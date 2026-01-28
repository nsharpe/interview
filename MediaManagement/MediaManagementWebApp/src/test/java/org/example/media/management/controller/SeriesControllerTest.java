package org.example.media.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.model.AuthenticationInfo;
import org.example.security.SecurityConfiguration;
import org.example.security.TokenRepo;
import org.example.series.SeriesModel;
import org.example.series.SeriesService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeriesController.class)
@Import(SecurityConfiguration.class)
class SeriesControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final UUID id = UUID.randomUUID();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SeriesService seriesService;

    @MockitoBean
    private TokenRepo tokenRepo;

    @MockitoBean
    RedisConnectionFactory redisConnectionFactory;

    @Test
    void shouldReturnExpectedResponse() throws Exception {

        when(tokenRepo.infoForToken("123")).thenReturn(mock(AuthenticationInfo.class));
        when(seriesService.createSeries(any())).thenReturn(baseSeriesModel().build());

        mockMvc.perform(post("/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer 123")
                        .content(OBJECT_MAPPER.writeValueAsString(getBasePost()))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))// Verify HTTP status
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value("A title"))
                .andExpect(jsonPath("$.description").value("Information about the story"));
    }

    private SeriesModel.SeriesModelBuilder<?,?> baseSeriesModel(){
        return SeriesModel.builder()
                .id(id)
                .locale(Locale.ENGLISH)
                .title("A title")
                .description("Information about the story");
    }

    private static Map<String,Object> getBasePost(){
        HashMap<String,Object> body = new HashMap<>();

        body.put("title","A title");
        body.put("description","Information about the story");
        body.put("locale","en");

        return body;
    }
}