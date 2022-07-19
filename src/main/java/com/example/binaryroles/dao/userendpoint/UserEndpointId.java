
package com.example.binaryroles.dao.userendpoint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class UserEndpointId implements Serializable {

    @Column(name = "uid_user")
    @NotBlank
    private String uidUser;

    @Column(name = "uid_endpoint")
    @NotBlank
    private String uidEndpoint;

}
