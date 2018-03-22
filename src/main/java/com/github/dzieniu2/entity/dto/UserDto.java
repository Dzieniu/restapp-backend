package com.github.dzieniu2.entity.dto;

import com.github.dzieniu2.validation.ValidRole;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ToString
public class UserDto {

    @NotNull
    @Email
    @Size(min = 3,max = 20)
    private String email;

    @NotNull
    @Size(min = 3,max = 60)
    private String password;

    @NotNull
    @ValidRole
    private String role;
}
