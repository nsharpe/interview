# Apps

All java executables that are intended for production  are stored here.

Each application should have 

* It's own `public static void main(String[] args)`
* Be a spring boot application
* Use the [spring-pod](../spring-util/spring-pod/README.md) module to auto wire anything that is required for all application.

## Applications

- [**admin-endpoint**](admin-endpoint/README.md): Admin dashboard backend
- [**user-management**](user-management/README.md): User account management service
- [**media-management**](media-management/README.md): Media content management
- [**media-metric-endpoint**](media-metric-endpoint/README.md): Media usage metrics ingestion
- [**media-player-endpoint**](media-player-endpoint/README.md): Public media player API
- [**media-comment**](media-comment/README.md): User comments on media

## Module Convention

- webapp 
  - Any application that can be accessed via an http request to performa  business task
- sdk
  - A java sdk for a webapp
- typescript-sdk
  - A typescript sdk for a webapp