
package com.example.binaryroles.dao.userendpoint;

import com.example.binaryroles.dao.endpoint.EndpointEntity;
import com.example.binaryroles.dao.user.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users_endpoints")
@Getter
@Setter
@EqualsAndHashCode
public class UserEndpointEntity {

    @EmbeddedId
    private UserEndpointId uid;

    @ManyToOne
    @MapsId("uidUser")
    @JoinColumn(name = "uid_user")
    private UserEntity user;

    @ManyToOne
    @MapsId("uidEndpoint")
    @JoinColumn(name = "uid_endpoint")
    private EndpointEntity endpoint;

    @Column(name = "permission")
    private boolean permission;

}
