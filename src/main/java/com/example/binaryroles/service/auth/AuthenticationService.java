package com.example.binaryroles.service.auth;

import com.example.binaryroles.dao.user.UserEntity;
import com.example.binaryroles.dao.user.UserEntityRepository;
import com.example.binaryroles.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserEntity loadUserByUsername(String username) {
        return userEntityRepository.getByUsername(username)
                .orElseThrow(() -> new ConflictException(String.format("Username: %s not found!", username)));
    }

    public boolean checkPermission(Integer binaryPermission, Optional<Integer> endpointsIndex) {
        if (endpointsIndex.isEmpty()) return false;

        String binaryString = Integer.toBinaryString(binaryPermission);
        char value = binaryString.charAt(endpointsIndex.get());
        return value == '1';
    }

    public Integer buildBinaryPermission(final UserEntity userEntity, Map<String, Integer> allEndpoints) {
        StringBuilder stringBuilder = new StringBuilder(StringUtils.repeat("0", allEndpoints.size()));

        userEntity.getUserEndpoints()
                .forEach(userEndpoint -> {
                    Integer index = allEndpoints.get(userEndpoint.getEndpoint().getUri());
                    if (index != null && userEndpoint.isPermission()) {
                        stringBuilder.setCharAt(index, '1');
                    }
                });
        return Integer.parseInt(stringBuilder.toString(), 2);
    }

}
