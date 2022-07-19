package com.example.binaryroles.conroller.something;

import com.example.binaryroles.conroller.something.dto.SomethingCreateUpdateDto;
import com.example.binaryroles.conroller.something.dto.SomethingDto;
import com.example.binaryroles.service.auth.dto.PrincipalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.binaryroles.conroller.Endpoints.*;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
@Slf4j
public class SomethingController {

    @GetMapping(SOMETHING)
    @ResponseStatus(HttpStatus.OK)
    public List<SomethingDto> findSomething(@AuthenticationPrincipal PrincipalDto principalDto) {
        log.info("find something was called by {}", principalDto.getUsername());
        return List.of(new SomethingDto(1L, "something-name-1"), new SomethingDto(2L, "something-name-2"));
    }

    @PostMapping(SOMETHING)
    @ResponseStatus(HttpStatus.CREATED)
    public SomethingDto createSomething(@RequestBody SomethingCreateUpdateDto createDto,
                                        @AuthenticationPrincipal PrincipalDto principalDto) {
        log.info("create something {} was called by {}", createDto, principalDto.getUsername());
        return new SomethingDto(1L, createDto.getName());
    }

    @PutMapping(SOMETHING + _ID)
    @ResponseStatus(HttpStatus.OK)
    public SomethingDto updateSomething(@PathVariable(value = "id") Long id,
                                        @RequestBody SomethingCreateUpdateDto updateDto,
                                        @AuthenticationPrincipal PrincipalDto principalDto) {
        log.info("update something {} was called by {}", updateDto, principalDto.getUsername());
        return new SomethingDto(id, updateDto.getName());

    }
}