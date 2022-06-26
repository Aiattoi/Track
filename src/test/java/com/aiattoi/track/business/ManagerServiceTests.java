package com.aiattoi.track.business;

import com.aiattoi.track.dao.ManagerJpaRepository;
import com.aiattoi.track.domain.Manager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTests
{
    @InjectMocks
    ManagerService service;

    @Mock
    ManagerJpaRepository repository;

    @Test
    public void testCreateManager()
    {
        Manager manager = new Manager("Jan Novak");
        service.create(manager);
        verify(repository, times(1)).save(manager);
    }

    @Test
    public void testListAllManagers()
    {
        List<Manager> list = new ArrayList<>();
        Manager manager = new Manager("Pavel Novotny");
        Manager manager1 = new Manager("Pavla Nova");
        Manager manager2 = new Manager("Petr Novak");

        list.add(manager);
        list.add(manager1);
        list.add(manager2);

        when(repository.findAll()).thenReturn(list);

        Collection<Manager> managerCollection = service.listAll();
        assertEquals(3, managerCollection.size());
        verify(repository, times(1)).findAll();
    }


}
