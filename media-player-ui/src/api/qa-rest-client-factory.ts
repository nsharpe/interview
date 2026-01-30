import axios from 'axios';
import {
    UserGeneratorControllerApi,
    Configuration
} from 'media-player-qa-client';

export const userGeneratorControllerApi  = (token?: string | null) => {

    if(token== null){
        const config = new Configuration({
            basePath: "http://localhost:9120"
        });
    }

    const str: string = `${token}`;

    const config = new Configuration({
        accessToken: str,
        basePath: "http://localhost:9120"
    });

    return new UserGeneratorControllerApi(config);
};