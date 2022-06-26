package com.aiattoi.track.integration;

import com.aiattoi.track.api.controller.ManagerController;
import com.aiattoi.track.api.dtos.ManagerDto;
import com.aiattoi.track.dao.ManagerJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Objects;

@ExtendWith(SpringExtension.class)

@SpringBootTest
public class ManagerIntegrationTests {
    @Autowired
    ManagerController managerController;
    @Autowired
    ManagerJpaRepository repository;

    final String managerName = "test";

    @Test
    public void testCreateRead() {
        ManagerDto managerDto = new ManagerDto(managerName);

        ManagerDto managerResult = managerController.newManager(managerDto);

        Collection<ManagerDto> managers = managerController.listAll();
        boolean found = false;
        for (ManagerDto m : managers)
            if (Objects.equals(m.name, managerDto.name))
                found = true;
        Assertions.assertTrue(found);

        repository.deleteById(managerResult.getId());
    }
}
