package com.example.binaryroles.service.endpoint;

import com.example.binaryroles.dao.endpoint.EndpointEntity;
import com.example.binaryroles.dao.endpoint.EndpointEntityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EndpointServiceTest {

    @Mock
    private EndpointEntityRepository endpointEntityRepository;

    @InjectMocks
    private EndpointService endpointService;

    @Test
    @DisplayName("Should initialize allEndpoints map with all endpoints from database")
    void endpointsMapInitShouldInitializeAllEndpointsMapWithAllEndpointsFromDatabase() {
        EndpointEntity endpointEntity1 =
                new EndpointEntity("1", "http://localhost:8080/endpoint1", 1);
        EndpointEntity endpointEntity2 =
                new EndpointEntity("2", "http://localhost:8080/endpoint2", 2);
        List<EndpointEntity> endpointEntities = Arrays.asList(endpointEntity1, endpointEntity2);
        when(endpointEntityRepository.findAll()).thenReturn(endpointEntities);

        endpointService.endpointsMapInit();

        assertEquals(2, endpointService.getAllEndpoints().size());
        assertEquals(1, endpointService.getAllEndpoints().get("http://localhost:8080/endpoint1"));
        assertEquals(2, endpointService.getAllEndpoints().get("http://localhost:8080/endpoint2"));
    }
}