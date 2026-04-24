import React, { useState, useEffect, useRef } from 'react';
import {seriesControllerApi} from "../../api/media-management-client";
import {mediaPlayerClient} from "../../api/media-player-client";
import {mediaControllerApi} from "../../api/media-metric-client";
import {mediaCommentControllerApi} from "../../api/media-comment-client";
import type { CommentGet } from 'media-comment-client';
import '../Table.css';
import { useParams } from 'react-router-dom';
import bouncingBallGif from '../../assets/bouncing_ball.gif';

export interface MediaItem {
    id: string;
    title: string | undefined;
}

const MediaPlayer: React.FC = () => {

    const [mediaPosition, setMediaPosition] = useState<number>(0);
    const [startTime, setStartTime] = useState<number>(Date.now);
    const [buttonText, setButtonText] = useState<string>("Start");
    const [lastActionId, setLastActionId] = useState<string|undefined>();
    const [media, setMedia] = useState<MediaItem>();
    const [isPlaying, setIsPlaying] = useState<boolean>(true);
    const { seriesId } = useParams();
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [totalPlays, setTotalPlays] = useState<number | undefined>(0);
    const [totalViewTimeSeconds, setTotalViewTimeSeconds] = useState<number | undefined>(0);
    const [commentText, setCommentText] = useState<string>('');
    const [comments, setComments] = useState<CommentGet[]>([]);
    const [fetchingComments, setFetchingComments] = useState<boolean>(false);
    const gifRef = useRef<HTMLImageElement>(null);

    const fetchAndLogMetrics = async () => {
        if (!media?.id) return;
        try {
            const { data: { totalPlayTimeMillis, totalPlays } } = await mediaControllerApi().getMediaViewTime(media.id);

            // If totalPlayTimeMillis is null or undefined, use 0
            const formattedTime = totalPlayTimeMillis ?? 0;

            setTotalPlays(totalPlays);
            setTotalViewTimeSeconds(formattedTime / 1000);
        } catch (error) {
            console.error("Error fetching metrics:", error);
        }
    };

    const fetchComments = async () => {
        if (!media?.id) return;

        try {
            setFetchingComments(true);
            const response = await mediaCommentControllerApi().getCommentsForMedia(media.id, 0, 10);
            if (response && response.data && Array.isArray(response.data)) {
                setComments(response.data);
            } else if (response && response.data && response.data.content) {
                setComments(response.data.content || []);
            } else {
                setComments([]);
            }
        } catch (error) {
            console.error("Error fetching comments:", error);
            setComments([]);
        } finally {
            setFetchingComments(false);
        }
    };

    useEffect(() => {
        const fetchPosts = async () => {
            setLoading(true);

            if(seriesId==null){
                setError("No series id Returned")
                return;
            }

            const seriesData = await seriesControllerApi().get2(seriesId)

            const data = await seriesControllerApi().getFirstEpisodeId(seriesId);

            if(data.data==null){
                setError("No Data Returned")
                return;
            }

            const mediaId = data.data.toString();
            if(mediaId == null){
                setError("No media Id found")
                return;
            }

            setMedia({
                id: mediaId,
                title: seriesData.data.title
            })

            setLoading(false)
        };

        fetchPosts();
    }, [seriesId]);

    // Fetch comments when media is loaded
    useEffect(() => {
        if (media) {
            fetchComments();
        }
    }, [media]);

    // Automatically start playing when media is loaded
    useEffect(() => {
        if (media && isPlaying) {
            const start = async () => {
                const eventId = crypto.randomUUID();

                setStartTime(Date.now());
                setButtonText("Pause");

                if (gifRef.current) {
                    gifRef.current.src = bouncingBallGif;
                }

                if(media?.id) {
                    mediaPlayerClient().start(media.id, {
                        eventState: {
                            mediaPosition: mediaPosition,
                            timestamp: new Date().toISOString(),
                            eventId: eventId
                        },
                        lastActionId: lastActionId
                    });
                    setLastActionId(eventId);
                }
            };

            start();
        }
    }, [media, isPlaying]);


    const start = async() => {
        const eventId = crypto.randomUUID();

        setStartTime(Date.now());

        setButtonText("Pause");
        setIsPlaying(true);

        if (gifRef.current) {
            gifRef.current.src = bouncingBallGif;
        }

        if(media?.id) {
            mediaPlayerClient().start(media.id, {
                eventState: {
                    mediaPosition: mediaPosition,
                    timestamp: new Date().toISOString(),
                    eventId: eventId
                },
                lastActionId: lastActionId
            });
            setLastActionId(eventId);
        }
    }

    // Remove the useEffect that calls start() when media changes since we're calling it from button click

    if(error!=null) return <h2>Error: {error}</h2>
    if (media == null) return <h2>No Media Found</h2>
    if (loading) return <h2>Loading...</h2>;

    const pause = async() => {
        const eventId = crypto.randomUUID();

        setButtonText("Start");
        setIsPlaying(false);

        const newMediaPosition = mediaPosition + (Date.now() - startTime)

        console.log("Start Time:"+startTime);
        console.log("newMediaPosition:"+newMediaPosition);

        console.log("mediaPosition.before:"+mediaPosition);
        setMediaPosition(newMediaPosition);
        console.log("mediaPosition:"+mediaPosition);

        if(lastActionId==null){
            setError("Last Action Id Was Not Set For Stop Command")
            return;
        }

        await mediaPlayerClient().stop({
            eventState: {
                mediaPosition: newMediaPosition,
                timestamp: new Date().toISOString(),
                eventId: eventId
            },
            lastActionId: lastActionId
        });
        setLastActionId(eventId);

        await fetchAndLogMetrics();
    }

    const postComment = async () => {
        if (!media?.id || !commentText.trim()) return;

        const cMediaPosition = isPlaying ? mediaPosition + (Date.now() - startTime) : mediaPosition;

        try {
            const commentRequest = {
                comment: commentText,
                mediaPositionMs: cMediaPosition
            };

            await mediaCommentControllerApi().postComment(media.id, commentRequest);

            setCommentText('');
            await fetchComments();
        } catch (error) {
            console.error("Error posting comment:", error);
            setError("Failed to post comment");
        }
    }

    return (
        <div>
            <table>
                <thead>
                    <tr>
                        <th className="table-header">{media.title}</th>
                    </tr>
                </thead>

                <tbody>
                <td style={{ textAlign: 'center' }}>
                    <div style={{
                        marginBottom: '10px',
                        height: '300px',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center'
                    }}>
                        {isPlaying && (
                            <img
                                ref={gifRef}
                                src={bouncingBallGif}
                                alt="Playing"
                                style={{ maxWidth: '100%', maxHeight: '300px' }}
                            />
                        )}
                    </div>
                    <button
                        onClick={() => {
                            if(buttonText === "Start") {
                                start()
                            }else{
                                pause();
                        }}}
                        style={{
                            marginBottom: '10px',
                            display: 'inline-flex',
                            alignItems: 'center',
                            justifyContent: 'center',

                            transform: 'scale(3)',
                            margin: '40px',
                        }}
                    >
                        {buttonText}
                    </button>
                    {totalPlays !== null && (
                        <div style={{ marginTop: '10px', textAlign: 'center' }}>
                            <p>Total Plays (number of times this page was loaded): {totalPlays}</p>
                            <p>Total View Time (seconds): {totalViewTimeSeconds}</p>
                        </div>
                    )}

                    {/* Comment Section */}
                    <div style={{ marginTop: '20px', padding: '10px', borderTop: '1px solid #ccc' }}>
                        <h3>Comments</h3>
                        <div style={{ marginBottom: '10px' }}>
                            <textarea
                                value={commentText}
                                onChange={(e) => setCommentText(e.target.value)}
                                placeholder="Write a comment..."
                                style={{ width: '100%', height: '60px', padding: '5px' }}
                            />
                            <button
                                onClick={postComment}
                                style={{ marginTop: '5px', padding: '5px 10px' }}
                            >
                                Post Comment
                            </button>
                        </div>

                        {fetchingComments ? (
                            <p>Loading comments...</p>
                        ) : comments.length > 0 ? (
                            <div>
                                {comments.map((comment, index) => (
                                    <div key={index} style={{
                                        border: '1px solid #eee',
                                        padding: '10px',
                                        marginBottom: '5px',
                                        borderRadius: '4px'
                                    }}>
                                        <p>{comment.comment}</p>
                                        <small>Position: {comment.mediaPositionMs}ms</small>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <p>No comments yet.</p>
                        )}
                    </div>
                </td>

                </tbody>
            </table>
        </div>
    );
};

export default MediaPlayer;