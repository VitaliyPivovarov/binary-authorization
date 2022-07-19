package com.example.binaryroles.conroller.something.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SomethingDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}
