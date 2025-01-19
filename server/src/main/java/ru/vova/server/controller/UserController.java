package ru.vova.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vova.server.dto.*;
import ru.vova.server.entity.User;
import ru.vova.server.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-points")
    public ResponseEntity<List<PointResponseDTO>> getPoints(@AuthenticationPrincipal User user) {
        List<PointResponseDTO> points = userService.getAllPoints(user);
        log.info("Точки для пользователя {} загружены успешно", user.getUsername());
        return ResponseEntity.ok(points);
    }

    @PostMapping("/check-point")
    public ResponseEntity<?> checkpoint(@Validated @RequestBody PointRequestDTO point, @AuthenticationPrincipal User user) {
        PointResponseDTO response = userService.addPoint(point, user);
        log.info("Успешно проверена точка для пользователя {}", user.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("get-user-profile-data")
    public ResponseEntity<?> getUserProfileData(@AuthenticationPrincipal User user) {
        UserProfileDataDTO userProfileDataDTO = userService.getUserProfileData(user);
        log.info("Successfully got user profile data for user with username {}", user.getUsername());
        return ResponseEntity.ok(userProfileDataDTO);
    }

    @PostMapping("/remove-points")
    public ResponseEntity<?> removePoints(@AuthenticationPrincipal User user) {
        userService.removePoints(user);
        log.info("Successfully removed points for user with username {}", user.getUsername());
        return ResponseEntity.ok().build();
    }

}
