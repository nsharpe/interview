import{MediaCommentControllerApi, Configuration} from 'media-comment-client'
import{getToken} from '../components/tokens/TokenManager'

export const mediaCommentControllerApi  = () => {
    const token = getToken()

    if(token== null){
        const config = new Configuration({
            basePath: "http://localhost:9140"
        });
        return new MediaCommentControllerApi(config);
    }

    const str: string = `${token}`;

    const config = new Configuration({
        accessToken: str,
        basePath: "http://localhost:9140"
    });

    return new MediaCommentControllerApi(config);
};