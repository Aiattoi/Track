package com.aiattoi.track.web_client.model;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
