package com.aiattoi.track.api.controller;

import com.aiattoi.track.business.EntityStateException;
import com.aiattoi.track.business.InterestingSiteService;
import com.aiattoi.track.business.TrackService;
import com.aiattoi.track.domain.Track;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.aiattoi.track.domain.TrackColor.RED;
import static com.aiattoi.track.domain.TrackColor.WHITE;
import static com.aiattoi.track.domain.TrackType.CYCLING_ROUTE;
import static com.aiattoi.track.domain.TrackType.TOURIST_TRACK;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTests {

    @MockBean
    TrackService trackService;
    @MockBean
    InterestingSiteService siteService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testListAll() throws Exception {
        Track track1 = new Track(TOURIST_TRACK, RED);
        Track track2 = new Track(CYCLING_ROUTE, WHITE);

        List<Track> tracks = List.of(track1, track2);

        Mockito.when(trackService.listAll()).thenReturn(tracks);

        mockMvc.perform(get("/tracks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].type", Matchers.is("TOURIST_TRACK")))
                .andExpect(jsonPath("$[0].color", Matchers.is("RED")))
                .andExpect(jsonPath("$[1].type", Matchers.is("CYCLING_ROUTE")))
                .andExpect(jsonPath("$[1].color", Matchers.is("WHITE")));
    }

    @Test
    public void testNewTrackExisting() throws Exception {
        doThrow(new EntityStateException(new Track())).when(trackService).create(any(Track.class));

        mockMvc.perform(post("/tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"TOURIST_TRACK\", \"color\": \"RED\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testNewTrack() throws Exception {
        Track track = new Track(TOURIST_TRACK, RED);

        Mockito.when(trackService.readById(any(Integer.class))).thenReturn(Optional.of(track));
        Mockito.when(trackService.create(any(Track.class))).thenReturn(track);

        mockMvc.perform(post("/tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"TOURIST_TRACK\", \"color\": \"RED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)))
                .andExpect(jsonPath("$.type", Matchers.is("TOURIST_TRACK")))
                .andExpect(jsonPath("$.color", Matchers.is("RED")));

        ArgumentCaptor<Track> argumentCaptor = ArgumentCaptor.forClass(Track.class);
        verify(trackService, Mockito.times(1)).create(argumentCaptor.capture());
        Track trackProvidedToService = argumentCaptor.getValue();
        Assertions.assertEquals(0, trackProvidedToService.getId());
        Assertions.assertEquals(TOURIST_TRACK, trackProvidedToService.getType());
        Assertions.assertEquals(RED, trackProvidedToService.getColor());
    }

    @Test
    public void testListOne() throws Exception {
        Track track = new Track(TOURIST_TRACK, RED);

        Mockito.when(trackService.readById(0)).thenReturn(Optional.of(track));

        mockMvc.perform(get("/tracks/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)))
                .andExpect(jsonPath("$.type", Matchers.is("TOURIST_TRACK")))
                .andExpect(jsonPath("$.color", Matchers.is("RED")));

        Mockito.when(trackService.readById(not(eq(0)))).thenReturn(Optional.empty());

        mockMvc.perform(get("/tracks/1"))
                .andExpect(status().isNotFound());
    }

}
