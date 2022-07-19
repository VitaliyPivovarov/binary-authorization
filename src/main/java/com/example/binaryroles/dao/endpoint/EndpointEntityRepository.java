package com.example.binaryroles.dao.endpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointEntityRepository extends JpaRepository<EndpointEntity, String> {

}
