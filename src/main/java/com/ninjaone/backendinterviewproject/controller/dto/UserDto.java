package com.ninjaone.backendinterviewproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Set<RoleDto> roles;

    public void setId(String id) {
        this.id = id;
    }
}
