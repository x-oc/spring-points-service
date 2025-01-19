package ru.vova.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationDTO {
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be empty")
    @Size(max = 15, message = "Username max size is 15 symbols")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
    @Size(max = 25, message = "Password max size is 25 symbols")
    private String password;
}
