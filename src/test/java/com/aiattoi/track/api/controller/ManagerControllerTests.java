package com.aiattoi.track.api.controller;

import com.aiattoi.track.business.EntityStateException;
import com.aiattoi.track.business.ManagerService;
import com.aiattoi.track.business.NoEntityFoundException;
import com.aiattoi.track.domain.Manager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ManagerController.class)
public class ManagerControllerTests {

    @MockBean
    ManagerService managerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testListAll() throws Exception {
        Manager manager1 = new Manager("Pavla Nova");
        Manager manager2 = new Manager("Pavel Novotny");

        List<Manager> managers = List.of(manager1, manager2);

        Mockito.when(managerService.listAll()).thenReturn(managers);

        mockMvc.perform(get("/managers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Pavla Nova")))
                .andExpect(jsonPath("$[1].name", Matchers.is("Pavel Novotny")));
    }

    @Test
    public void testNewManagerExisting() throws Exception {
        doThrow(new EntityStateException(new Manager())).when(managerService).create(any(Manager.class));

        mockMvc.perform(post("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Fail Test\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testNewManager() throws Exception {
        Manager manager = new Manager("test");

        Mockito.when(managerService.readById(any(Integer.class))).thenReturn(Optional.of(manager));
        Mockito.when(managerService.create(any(Manager.class))).thenReturn(manager);

        mockMvc.perform(post("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)))
                .andExpect(jsonPath("$.name", Matchers.is("test")));

        ArgumentCaptor<Manager> argumentCaptor = ArgumentCaptor.forClass(Manager.class);
        verify(managerService, Mockito.times(1)).create(argumentCaptor.capture());
        Manager managerProvidedToService = argumentCaptor.getValue();
        Assertions.assertEquals(0, managerProvidedToService.getId());
        Assertions.assertEquals("test", managerProvidedToService.getName());
    }

    @Test
    public void testListOne() throws Exception {
        Manager manager = new Manager("test");

        Mockito.when(managerService.readById(0)).thenReturn(Optional.of(manager));

        mockMvc.perform(get("/managers/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)))
                .andExpect(jsonPath("$.name", Matchers.is("test")));

        Mockito.when(managerService.readById(not(eq(0)))).thenReturn(Optional.empty());

        mockMvc.perform(get("/managers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateManagerNotExisting() throws Exception {
        doThrow(new NoEntityFoundException(Manager.getEntityName())).when(managerService).updateWithTracks(any(Manager.class));

        mockMvc.perform(put("/managers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateManager() throws Exception {
        Manager manager = new Manager("test");

        Mockito.when(managerService.readById(not(eq(0)))).thenReturn(Optional.empty());
        Mockito.when(managerService.readById(0)).thenReturn(Optional.of(manager));

        mockMvc.perform(put("/managers/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(0)))
                .andExpect(jsonPath("$.name", Matchers.is("test")));

        ArgumentCaptor<Manager> argumentCaptor = ArgumentCaptor.forClass(Manager.class);
        verify(managerService, Mockito.times(1)).updateWithTracks(argumentCaptor.capture());
        Manager managerProvidedToService = argumentCaptor.getValue();
        Assertions.assertEquals(0, managerProvidedToService.getId());
        Assertions.assertEquals("test", managerProvidedToService.getName());
    }
    @Test
    public void testDeleteManager() throws Exception {
        Manager manager = new Manager("test");

        Mockito.when(managerService.readById(not(eq(0)))).thenReturn(Optional.empty());
        Mockito.when(managerService.readById(0)).thenReturn(Optional.of(manager));

        mockMvc.perform(delete("/managers/1"))
                .andExpect(status().isNotFound());

        verify(managerService, never()).deleteByIdWithTracks(any());


        mockMvc.perform(delete("/managers/0"))
                .andExpect(status().isOk());

        verify(managerService, times(1)).deleteByIdWithTracks(0);
    }
}
