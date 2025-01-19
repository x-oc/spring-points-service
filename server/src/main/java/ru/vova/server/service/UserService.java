package ru.vova.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vova.server.dto.*;
import ru.vova.server.repository.PointRepository;
import ru.vova.server.entity.Point;
import ru.vova.server.entity.User;

import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final PointRepository pointRepository;
    private final AreaCheckService areaCheckService;

    public List<PointResponseDTO> getAllPoints(User user) {
        return pointRepository.findByUserId(user.getId())
                .stream()
                .map(PointResponseDTO::new)
                .toList();
    }

    public PointResponseDTO addPoint(PointRequestDTO point, User user) {
        PointResponseDTO response = areaCheckService.checkPoint(point);
        Point pointEntity = Point.builder()
                .x(response.getX())
                .y(response.getY())
                .r(response.getR())
                .hit(response.isHit())
                .reqTime(response.getReqTime())
                .procTime(response.getProcTime())
                .user(user)
                .build();
        this.pointRepository.save(pointEntity);
        return response;
    }

    public void removePoints(User user) {
        pointRepository.deleteByUserId(user.getId());
    }

    public UserProfileDataDTO getUserProfileData(User user) {
        return UserProfileDataDTO
                .builder()
                .username(user.getUsername())
                .build();
    }
}
