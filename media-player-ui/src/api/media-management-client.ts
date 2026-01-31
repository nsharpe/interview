import {SeriesControllerApi,Configuration} from 'media-player-media-management-client'
import{getToken} from '../components/tokens/TokenManager'

export const seriesControllerApi  = () => {
    const token = getToken()

    if(token== null){
        const config = new Configuration({
            basePath: "http://localhost:9090"
        });
        return new SeriesControllerApi(config);
    }

    const str: string = `${token}`;

    const config = new Configuration({
        accessToken: str,
        basePath: "http://localhost:9090"
    });

    return new SeriesControllerApi(config);
};