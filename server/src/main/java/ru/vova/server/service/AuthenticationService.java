package ru.vova.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vova.server.dto.*;
import ru.vova.server.repository.UserRepository;
import ru.vova.server.entity.RefreshToken;
import ru.vova.server.entity.Role;
import ru.vova.server.entity.User;
import ru.vova.server.exception.RefreshTokenNotFoundException;
import ru.vova.server.exception.RefreshTokenTimeoutException;
import ru.vova.server.exception.UsernameExistsException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenService accessTokenService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public CredentialsDTO register(RegistrationDTO registrationDTO) throws UsernameExistsException {
        if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent())
            throw new UsernameExistsException("Пользователь " + registrationDTO.getUsername() + " уже существует");
        User user = User.builder()
                .username(registrationDTO.getUsername())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return generateCredentials(user);
    }

    public CredentialsDTO logIn(AuthenticationDTO authenticationDTO) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getUsername(), authenticationDTO.getPassword()));
        User user = userRepository.findByUsername(authenticationDTO.getUsername()).orElse(null);
        return generateCredentials(user);
    }

    public void logout(RefreshTokenDTO refreshTokenDTO) {
        refreshTokenService.removeToken(refreshTokenDTO.getToken());
    }

    public AccessTokenDTO getRefreshedToken(RefreshTokenDTO refreshTokenDTO) throws RefreshTokenTimeoutException, RefreshTokenNotFoundException {
        RefreshToken refreshToken = refreshTokenService.getRefreshToken(refreshTokenDTO.getToken());
        if (!refreshTokenService.isExpired(refreshToken)) {
            User user = refreshToken.getUser();
            String accessToken = accessTokenService.generateAccessToken(user);
            return new AccessTokenDTO(accessToken);
        }
        refreshTokenService.removeToken(refreshTokenDTO.getToken());
        throw new RefreshTokenTimeoutException("Токен " + refreshTokenDTO.getToken().toString() + " истёк");
    }

    private CredentialsDTO generateCredentials(User user) {
        String accessToken = accessTokenService.generateAccessToken(user);
        UUID refreshToken = refreshTokenService.generateRefreshToken(user);
        return CredentialsDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
