import{MediaPerformanceControllerApi, Configuration} from 'media-metric-media-player-client'
import{getToken} from '../components/tokens/TokenManager'

export const mediaControllerApi  = () => {
    const token = getToken()

    if(token== null){
        const config = new Configuration({
            basePath: "http://localhost:9130"
        });
        return new MediaPerformanceControllerApi(config);
    }

    const str: string = `${token}`;

    const config = new Configuration({
        accessToken: str,
        basePath: "http://localhost:9130"
    });

    return new MediaPerformanceControllerApi(config);
};