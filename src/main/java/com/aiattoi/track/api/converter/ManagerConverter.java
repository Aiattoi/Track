package com.aiattoi.track.api.converter;

import com.aiattoi.track.api.dtos.ManagerDto;
import com.aiattoi.track.domain.Manager;

import java.util.ArrayList;
import java.util.Collection;

public class ManagerConverter {
    public static Manager fromDto(ManagerDto managerDto) {
        Manager manager = new Manager(managerDto.name);
        if (managerDto.id == null)
            manager.setId(0);
        else
            manager.setId(managerDto.id);
        return manager;
    }

    public static ManagerDto toDto(Manager manager) {
        return new ManagerDto(manager.getId(), manager.getName());
    }

    public static Collection<Manager> fromDtos(Collection<ManagerDto> managerDtos) {
        Collection<Manager> managers = new ArrayList<>();
        managerDtos.forEach((t) -> managers.add(fromDto(t)));
        return managers;
    }

    public static Collection<ManagerDto> toDtos(Collection<Manager> managers) {
        Collection<ManagerDto> managerDtos = new ArrayList<>();
        managers.forEach((t) -> managerDtos.add(toDto(t)));
        return managerDtos;
    }
}
