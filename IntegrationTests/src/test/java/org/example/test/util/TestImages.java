package org.example.test.util;

import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestImages {
    private TestImages(){}

    public static final ImageFromDockerfile PUBLIC_REST_ENDPOINT_IMAGE = create("public-rest-endpoint-test-image",
            "../PublicRestEndpoint/build/libs/public-rest-endpoint-app.jar");

    public static final ImageFromDockerfile MEDIA_MANAGEMENT_IMAGE = create("media-management-image",
            "../MediaManagement/build/libs/media-management-app.jar");

    private static ImageFromDockerfile create(String dockerImageName,String path){
       Path absolutePath = Paths.get(path).toAbsolutePath();
       System.out.println("Building Docker image using JAR: " + absolutePath);

       return new ImageFromDockerfile(dockerImageName, true) // false = don't delete immediately if you want to reuse
                .withFileFromPath("app.jar",
                        Paths.get(path))
                .withDockerfileFromBuilder(builder ->
                        builder
                                .from("eclipse-temurin:21-jre-alpine")
                                .copy("app.jar", "/app.jar")
                                .entryPoint("java", "-jar", "/app.jar")
                                .build()
                );
    }
}
