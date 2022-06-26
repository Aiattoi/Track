package com.aiattoi.track.api.controller;

import com.aiattoi.track.api.dtos.ManagerDto;
import com.aiattoi.track.business.ManagerService;
import com.aiattoi.track.business.NoEntityFoundException;
import com.aiattoi.track.domain.Manager;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.aiattoi.track.api.converter.ManagerConverter.*;

@RestController
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/managers")
    public Collection<ManagerDto> listAll() {
        return toDtos(managerService.listAll());
    }

    @PostMapping("/managers")
    public ManagerDto newManager(@RequestBody ManagerDto newManager)
            throws NoEntityFoundException {

        Manager manager = managerService.create(fromDto(newManager));

        Manager man = this.managerService.readById(manager.getId()).orElseThrow(
                () -> new NoEntityFoundException(Manager.getEntityName()));

        return toDto(man);
    }

    @GetMapping("/managers/{id}")
    public ManagerDto listOne(@PathVariable Integer id)
            throws NoEntityFoundException {

        return toDto(managerService.readById(id).orElseThrow(
                () -> new NoEntityFoundException(Manager.getEntityName())));
    }

    @PutMapping("/managers/{id}")
    ManagerDto updateManager(@RequestBody ManagerDto managerDto, @PathVariable Integer id) {
        managerDto.id = id;
        Manager manager = fromDto(managerDto);

        managerService.updateWithTracks(manager);

        return toDto(manager);
    }

    @DeleteMapping("/managers/{id}")
    public void deleteManager(@PathVariable Integer id)
            throws NoEntityFoundException {

        managerService.readById(id).orElseThrow(
                () -> new NoEntityFoundException(Manager.getEntityName()));

        managerService.deleteByIdWithTracks(id);
    }
}
