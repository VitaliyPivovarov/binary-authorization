package com.example.binaryroles.dao.endpoint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "endpoints")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid2")
    @NotBlank
    private String uid;

    @Column(name = "uri")
    @Length(max = 2048)
    @NotBlank
    private String uri;

    @Column(name = "index")
    private int index;

}
