import {
    UserGeneratorControllerApi,
    SeriesGeneratorControllerApi,
    Configuration
} from 'media-player-qa-client';
import {getToken} from "../components/tokens/TokenManager";

export const userGeneratorControllerApi  = () => {
    const token = getToken()

    if(token== null){
        const config = new Configuration({
            basePath: "http://localhost:9120"
        });
        return new UserGeneratorControllerApi(config);
    }

    const str: string = `${token}`;

    const config = new Configuration({
        accessToken: str,
        basePath: "http://localhost:9120"
    });

    return new UserGeneratorControllerApi(config);
};

export const seriesGeneratorControllerApi  = () => {
    const token = getToken()

    if(token== null){
        const config = new Configuration({
            basePath: "http://localhost:9120"
        });
        return new SeriesGeneratorControllerApi(config);
    }

    const str: string = `${token}`;

    const config = new Configuration({
        accessToken: str,
        basePath: "http://localhost:9120"
    });

    return new SeriesGeneratorControllerApi(config);
};