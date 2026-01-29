import{UserControllerApi} from 'media-player-public-rest-client'

export const userControllerApi = new UserControllerApi(
    {
        basePath: 'http://localhost:9080',
        isJsonMime: (mime: string) =>
            mime.toLowerCase().includes('application/json') ||
            mime.toLowerCase().includes('text/json'),
    }
);