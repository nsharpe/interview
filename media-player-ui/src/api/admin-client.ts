import{UserAdminControllerApi} from 'media-player-admin-client';

export const adminClient = new UserAdminControllerApi(
    {
        basePath: 'http://localhost:9110',
        isJsonMime: (mime: string) =>
            mime.toLowerCase().includes('application/json') ||
            mime.toLowerCase().includes('text/json'),
    }
);