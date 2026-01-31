import{UserAdminControllerApi, Configuration} from 'media-player-admin-client';
import{getToken} from '../components/tokens/TokenManager'

export const userAdminClient  = () => {
    const token = getToken()

    if(token== null){
        const config = new Configuration({
            basePath: "http://localhost:9110/admin"
        });
        return new UserAdminControllerApi(config);
    }

    const str: string = `${token}`;

    const config = new Configuration({
        accessToken: str,
        basePath: "http://localhost:9110/admin"
    });

    return new UserAdminControllerApi(config);
};