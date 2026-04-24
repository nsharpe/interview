package org.amoeba.example.app.media.comment.controller.model;

import org.amoeba.example.comment.repository.CommentPostgres;
import org.amoeba.example.postgrsql.PostgresTimeStamp;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommentGetTest {


    @Test
    void testOf(){
        UUID recordId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID publicId = UUID.randomUUID();
        OffsetDateTime created = OffsetDateTime.of(2026,2,3,11,15,17,1, ZoneOffset.UTC);
        OffsetDateTime updated = OffsetDateTime.of(2026,2,4,11,15,17,1, ZoneOffset.UTC);
        PostgresTimeStamp timeStamp = PostgresTimeStamp.builder()
                .creationTimestamp(created)
                .lastUpdatedDate(updated)
                .build();

        CommentPostgres postCommentRequest = CommentPostgres.builder()
                .mediaPositionMs(100L)
                .id(1L)
                .comment("a Comment")
                .recordType("rType")
                .recordId(recordId)
                .publicId(publicId)
                .timeStamp(timeStamp)
                .userId(userId)
                .build();

        CommentGet commentGet = CommentGet.of(postCommentRequest);

        assertNotNull(commentGet);
        assertEquals(publicId, commentGet.getId());
        assertEquals("a Comment", commentGet.getComment());
        assertEquals("rType", commentGet.getRecordType());
        assertEquals( recordId, commentGet.getRecordId());
        assertEquals(userId, commentGet.getUserId());
        assertEquals(created, commentGet.getCreated());

    }
}