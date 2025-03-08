package com.movieapp.dto;


import com.movieapp.entity.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

    @NotNull(message = "email can't be null")
    private String email;

    @NotNull(message = "password can't be null")
    private String password;

    @NotNull(message = "user name can't be null")
    private String userName;


}
