package com.aiattoi.track.web_client.data;

import com.aiattoi.track.web_client.model.ManagerDto;
import com.aiattoi.track.web_client.model.ManagerWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ManagerClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient managerWebClient;

    public ManagerClient(@Value("http://localhost:8080") String backendUrl) {
        managerWebClient = WebClient.create(backendUrl + "/managers");
    }

    public Mono<ManagerWebModel> create(ManagerDto manager) {
        return managerWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(manager)
                .retrieve()
                .bodyToMono(ManagerWebModel.class);
    }

    public Flux<ManagerWebModel> readAll() {
        return managerWebClient.get()
                .retrieve()
                .bodyToFlux(ManagerWebModel.class);
    }

    public Mono<ManagerWebModel> readById(Integer id) {
        return managerWebClient.get()
                .uri(ONE_URI, id).retrieve()
                .bodyToMono(ManagerWebModel.class);
    }

    public Mono<ManagerWebModel> update(ManagerDto managerDto) {
        return managerWebClient.put()
                .uri(ONE_URI, managerDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(managerDto)
                .retrieve()
                .bodyToMono(ManagerWebModel.class);
    }

    public Mono<Void> deleteById(Integer id) {
        return managerWebClient.delete()
                .uri(ONE_URI, id)
                .retrieve()
                .bodyToMono(Void.TYPE);
    }
}
