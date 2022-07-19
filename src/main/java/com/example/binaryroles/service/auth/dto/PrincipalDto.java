package com.example.binaryroles.service.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrincipalDto {
    private String userUid;
    private String username;
    private Integer binaryPermission;

    public PrincipalDto(String userUid, String username, Integer binaryPermission) {
        this.userUid = userUid;
        this.username = username;
        this.binaryPermission = binaryPermission;
    }
}
