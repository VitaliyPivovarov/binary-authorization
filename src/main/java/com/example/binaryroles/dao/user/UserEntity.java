package com.example.binaryroles.dao.user;

import com.example.binaryroles.dao.userendpoint.UserEndpointEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = "password")
public class UserEntity implements UserDetails {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid2")
    @NotBlank
    private String uid;

    @Column(name = "username")
    @NotBlank
    @Length(max = 40)
    private String username;

    @Column(name = "password")
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserEndpointEntity> userEndpoints = new HashSet<>();

    @Transient
    private boolean enabled;
    @Transient
    private boolean accountNonExpired;
    @Transient
    private boolean accountNonLocked;
    @Transient
    private boolean credentialsNonExpired;
    @Transient
    private List<GrantedAuthority> authorities;
}
