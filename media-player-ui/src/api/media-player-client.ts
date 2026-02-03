import {MediaEventControllerApi,Configuration} from 'media-player-media-player-client'
import{getToken} from '../components/tokens/TokenManager'

export const mediaPlayerClient  = () => {
    const token = getToken()

    if(token== null){
        const config = new Configuration({
            basePath: "http://localhost:9100"
        });
        return new MediaEventControllerApi(config);
    }

    const str: string = `${token}`;

    const config = new Configuration({
        accessToken: str,
        basePath: "http://localhost:9100"
    });

    return new MediaEventControllerApi(config);
};