package com.aiattoi.track.api.dtos;

public class ManagerDto {
    public Integer id;
    public String name;

    public ManagerDto() {
    }

    public ManagerDto(String name) {
        this.name = name;
    }

    public ManagerDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
}
