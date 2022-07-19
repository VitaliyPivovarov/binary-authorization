package com.example.binaryroles.conroller.something.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SomethingCreateUpdateDto {

    @JsonProperty("name")
    private String name;
}
