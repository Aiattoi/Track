package com.aiattoi.track.dao;

import com.aiattoi.track.domain.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ManagerRepositoryTests {
    @Autowired
    ManagerJpaRepository managerJpaRepository;

    @Test
    public void testCreateReadDelete() {
        // add manager and ensure, that it has been done
        Manager manager = new Manager("Jindrich Stary");

        managerJpaRepository.save(manager);

        List<Manager> managers = managerJpaRepository.findAll();
        boolean found = false;
        Integer id = 0;
        for (Manager m : managers)
            if (m.getName() == manager.getName()) {
                found = true;
                id = m.getId();
            }
        Assertions.assertTrue(found);

        // delete manager and ensure, that it has been done
        managerJpaRepository.deleteById(id);

        managers = managerJpaRepository.findAll();
        found = false;
        for (Manager m : managers)
            if (m.getName() == manager.getName()) {
                found = true;
            }
        Assertions.assertFalse(found);
    }

}

