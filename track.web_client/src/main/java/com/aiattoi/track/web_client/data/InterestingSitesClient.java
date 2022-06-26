package com.aiattoi.track.web_client.data;

import com.aiattoi.track.web_client.model.InterestingSiteDto;
import com.aiattoi.track.web_client.model.InterestingSitesWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class InterestingSitesClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient sitesWebClient;

    public InterestingSitesClient(@Value("http://localhost:8080") String backendUrl) {
        sitesWebClient = WebClient.create(backendUrl + "/interestingSites");
    }

    public Mono<InterestingSitesWebModel> create(InterestingSiteDto siteDto) {
        return sitesWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(siteDto)
                .retrieve()
                .bodyToMono(InterestingSitesWebModel.class);
    }

    public Flux<InterestingSitesWebModel> readAll() {
        return sitesWebClient.get()
                .retrieve()
                .bodyToFlux(InterestingSitesWebModel.class);
    }

    public Mono<InterestingSitesWebModel> readById(Integer id) {
        return sitesWebClient.get()
                .uri(ONE_URI, id).retrieve()
                .bodyToMono(InterestingSitesWebModel.class);
    }

    public Mono<InterestingSitesWebModel> update(InterestingSiteDto siteDto) {
        return sitesWebClient.put()
                .uri(ONE_URI, siteDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(siteDto)
                .retrieve()
                .bodyToMono(InterestingSitesWebModel.class);
    }

    public Mono<Void> deleteById(Integer id) {
        return sitesWebClient.delete()
                .uri(ONE_URI, id)
                .retrieve()
                .bodyToMono(Void.TYPE);
    }

    public Flux<InterestingSitesWebModel> listGreater(Integer altitude) {
        return sitesWebClient.get()
                .uri("/greater/{altitude}", altitude)
                .retrieve()
                .bodyToFlux(InterestingSitesWebModel.class);
    }
}
