import React, { useState, useEffect, useRef } from 'react';
import {seriesControllerApi} from "../../api/media-management-client";
import {mediaPlayerClient} from "../../api/media-player-client";
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
    const gifRef = useRef<HTMLImageElement>(null);

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

    if(error!=null) return <h2>Error: {error}</h2>
    if (media == null) return <h2>No Media Found</h2>
    if (loading) return <h2>Loading...</h2>;


    const start = async() => {
        const eventId = crypto.randomUUID();

        setStartTime(Date.now)

        setButtonText("Stop");
        setIsPlaying(true);

        if (gifRef.current) {
            gifRef.current.src = bouncingBallGif;
        }

        mediaPlayerClient().start(media.id,{
            eventState: {
                mediaPosition: mediaPosition,
                timestamp: new Date().toISOString(),
                eventId: eventId
            },
            lastActionId: lastActionId
        });
        setLastActionId(eventId);
    }

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
                </td>

                </tbody>
            </table>
        </div>
    );
};

export default MediaPlayer;