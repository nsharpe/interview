import React, { useState, useEffect, useRef } from 'react';
import {seriesControllerApi} from "../../api/media-management-client";
import {mediaPlayerClient} from "../../api/media-player-client";
import {mediaControllerApi} from "../../api/media-metric-client";
import '../Table.css';
import { useParams } from 'react-router-dom';
import bouncingBallGif from '../../assets/bouncing_ball.gif';

export interface MediaItem {
    id: string;
}

const MediaPlayer: React.FC = () => {

    const [mediaPosition, setMediaPosition] = useState<number>(0);
    const [startTime, setStartTime] = useState<number>(Date.now);
    const [buttonText, setButtonText] = useState<string>("Start");
    const [lastActionId, setLastActionId] = useState<string|undefined>();
    const [media, setMedia] = useState<MediaItem>();
    const [isPlaying, setIsPlaying] = useState<boolean>(false);
    const { seriesId } = useParams();
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [totalPlays, setTotalPlays] = useState<number | undefined>(0);
    const [totalViewTimeSeconds, setTotalViewTimeSeconds] = useState<number | undefined>(0);
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

    useEffect(() => {
        const fetchPosts = async () => {
            setLoading(true);

            if(seriesId==null){
                setError("No series id Returned")
                return;
            }

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
                id: mediaId
            })

            setLoading(false)
        };

        fetchPosts();
    }, []);


    const start = async() => {
        const eventId = crypto.randomUUID();

        setStartTime(Date.now)

        setButtonText("Stop");
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

    useEffect(() => {
        if (media?.id) {
            start();
            fetchAndLogMetrics();
        }
    }, [media?.id]);

    if(error!=null) return <h2>Error: {error}</h2>
    if (media == null) return <h2>No Media Found</h2>
    if (loading) return <h2>Loading...</h2>;

    const stop = async() => {
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

    return (
        <div>
            <table>
                <thead>
                    <tr>
                        <th className="table-header">ID: {media.id}</th>
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
                            if(buttonText == "Start") {
                                start()
                            }else{
                                stop();
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
                            <p>Total Plays: {totalPlays}</p>
                            <p>Total View Time (seconds): {totalViewTimeSeconds}</p>
                        </div>
                    )}
                </td>

                </tbody>
            </table>
        </div>
    );
};

export default MediaPlayer;