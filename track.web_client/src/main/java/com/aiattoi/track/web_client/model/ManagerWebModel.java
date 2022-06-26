package com.aiattoi.track.web_client.model;

public class ManagerWebModel extends ManagerDto {

    public ManagerWebModel() {}

    public ManagerWebModel(ManagerDto managerDto) {
        super(managerDto.id, managerDto.name);
    }
}
