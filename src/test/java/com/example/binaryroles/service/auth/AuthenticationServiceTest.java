package com.example.binaryroles.service.auth;

import com.example.binaryroles.dao.endpoint.EndpointEntity;
import com.example.binaryroles.dao.user.UserEntity;
import com.example.binaryroles.dao.user.UserEntityRepository;
import com.example.binaryroles.dao.userendpoint.UserEndpointEntity;
import com.example.binaryroles.exception.ConflictException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Should return the user when the username is found")
    void loadUserByUsernameWhenUsernameIsFoundThenReturnTheUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("john");
        when(userEntityRepository.getByUsername(userEntity.getUsername()))
                .thenReturn(Optional.of(userEntity));

        UserDetails userDetails =
                authenticationService.loadUserByUsername(userEntity.getUsername());

        assertEquals(userEntity, userDetails);
    }

    @Test
    @DisplayName("Should throws an exception when the username is not found")
    void loadUserByUsernameWhenUsernameIsNotFoundThenThrowsException() {
        String username = "username";
        when(userEntityRepository.getByUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                ConflictException.class,
                () -> {
                    authenticationService.loadUserByUsername(username);
                });
    }

    @Test
    @DisplayName("Should return false when the endpointsIndex is empty")
    void checkPermissionWhenEndpointsIndexIsEmptyThenReturnFalse() {
        Integer binaryPermission = 1;
        Optional<Integer> endpointsIndex = Optional.empty();

        boolean result = authenticationService.checkPermission(binaryPermission, endpointsIndex);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when the endpointsIndex is not empty and the value is 1")
    void checkPermissionWhenEndpointsIndexIsNotEmptyAndValueIs1ThenReturnTrue() {
        Integer binaryPermission = Integer.parseInt("110", 2);
        Optional<Integer> endpointsIndex = Optional.of(1);

        boolean result = authenticationService.checkPermission(binaryPermission, endpointsIndex);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return 0 when the user has no permission")
    void buildBinaryPermissionWhenUserHasNoPermissionThenReturn0() {
        UserEntity userEntity = new UserEntity();

        UserEndpointEntity userEndpointEntity = new UserEndpointEntity();
        userEndpointEntity.setPermission(false);
        userEndpointEntity.setUser(userEntity);
        userEndpointEntity.setEndpoint(new EndpointEntity("1", "/api/v1/users", 0));

        Set<UserEndpointEntity> userEndpointEntities = new HashSet<>();
        userEndpointEntities.add(userEndpointEntity);

        userEntity.setUserEndpoints(userEndpointEntities);

        Map<String, Integer> allEndpoints = new HashMap<>();
        allEndpoints.put("/api/v1/users", 0);
        allEndpoints.put("/api/v1/users/{uid}", 1);
        allEndpoints.put("/api/v1/users/{uid}/endpoints", 2);

        Integer result = authenticationService.buildBinaryPermission(userEntity, allEndpoints);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should return 1 when the user has permission for one endpoint")
    void buildBinaryPermissionWhenUserHasOnePermissionThenReturn1() {
        UserEntity userEntity = new UserEntity();

        UserEndpointEntity userEndpointEntity = new UserEndpointEntity();
        userEndpointEntity.setPermission(true);
        userEndpointEntity.setUser(userEntity);
        userEndpointEntity.setEndpoint(new EndpointEntity("1", "/api/v1/users", 0));

        Set<UserEndpointEntity> userEndpointEntities = new HashSet<>();
        userEndpointEntities.add(userEndpointEntity);

        userEntity.setUserEndpoints(userEndpointEntities);

        Map<String, Integer> allEndpoints = new HashMap<>();
        allEndpoints.put("/api/v1/users", 0);
        allEndpoints.put("/api/v1/users/{uid}", 1);
        allEndpoints.put("/api/v1/users/{uid}/endpoints", 2);

        Integer result = authenticationService.buildBinaryPermission(userEntity, allEndpoints);

        assertEquals(4, result);
    }
}