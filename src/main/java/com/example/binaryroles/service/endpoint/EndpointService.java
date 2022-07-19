package com.example.binaryroles.service.endpoint;

import com.example.binaryroles.dao.endpoint.EndpointEntity;
import com.example.binaryroles.dao.endpoint.EndpointEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EndpointService {

    private final EndpointEntityRepository endpointEntityRepository;

    /**
     * In-memory (REDIS, for example)
     */
    private Map<String, Integer> allEndpoints = new HashMap<>();

    @PostConstruct
    public void endpointsMapInit() {
        allEndpoints = endpointEntityRepository.findAll()
                .stream().collect(Collectors.toMap(EndpointEntity::getUri, EndpointEntity::getIndex));
    }

    public Optional<Integer> findIndexByUri(String uri) {
        return Optional.ofNullable(allEndpoints.get(uri));
    }

    public Map<String, Integer> getAllEndpoints() {
        return new HashMap<>(allEndpoints);
    }
}
