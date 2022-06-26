package com.aiattoi.track.web_client.data;

import com.aiattoi.track.web_client.model.InterestingSitesWebModel;
import com.aiattoi.track.web_client.model.ManagerWebModel;
import com.aiattoi.track.web_client.model.TrackDto;
import com.aiattoi.track.web_client.model.TrackWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TrackClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient trackWebClient;

    public TrackClient(@Value("http://localhost:8080") String backendUrl) {
        this.trackWebClient = WebClient.create(backendUrl + "/tracks");
    }

    public Mono<TrackWebModel> create(TrackDto trackDto) {
        return trackWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(trackDto)
                .retrieve()
                .bodyToMono(TrackWebModel.class);
    }

    public Flux<TrackWebModel> readAll() {
        return trackWebClient.get()
                .retrieve()
                .bodyToFlux(TrackWebModel.class);
    }

    public Mono<ManagerWebModel> readManager(Integer id) {
        return trackWebClient.get()
                .uri(ONE_URI + "/manager", id)
                .retrieve()
                .bodyToMono(ManagerWebModel.class);
    }

    public Mono<TrackWebModel> changeManager(Integer trackId, Integer managerId) {
        return trackWebClient.put()
                .uri("/{trackId}/manager/{managerId}", trackId, managerId)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TrackWebModel.class);
    }

    public Flux<InterestingSitesWebModel> readSites(Integer id) {
        return trackWebClient.get()
                .uri(ONE_URI + "/interestingSites", id)
                .retrieve()
                .bodyToFlux(InterestingSitesWebModel.class);
    }

    public Mono<TrackWebModel> addSite(Integer trackId, Integer siteId) {
        return trackWebClient.put()
                .uri("/{trackId}/interestingSites/{isId}", trackId, siteId)
                .retrieve()
                .bodyToMono(TrackWebModel.class);
    }

    public Mono<TrackWebModel> deleteSite(Integer trackId, Integer siteId) {
        return trackWebClient.delete().uri("/{trackId}/interestingSites/{isId}", trackId, siteId)
                .retrieve()
                .bodyToMono(TrackWebModel.class);
    }

    public Mono<TrackWebModel> readById(Integer id) {
        return trackWebClient.get()
                .uri(ONE_URI, id).retrieve()
                .bodyToMono(TrackWebModel.class);
    }

    public Mono<TrackWebModel> update(TrackDto trackDto) {
        return trackWebClient.put()
                .uri(ONE_URI, trackDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(trackDto)
                .retrieve()
                .bodyToMono(TrackWebModel.class);
    }

    public Mono<Void> deleteById(Integer id) {
        return trackWebClient.delete()
                .uri(ONE_URI, id)
                .retrieve()
                .bodyToMono(Void.TYPE);
    }
}
