package ru.vova.server.service;

import org.springframework.stereotype.Service;
import ru.vova.server.dto.PointRequestDTO;
import ru.vova.server.dto.PointResponseDTO;

import java.util.Date;

@Service
public class AreaCheckService {
    public PointResponseDTO checkPoint(PointRequestDTO request) {
        PointResponseDTO response = PointResponseDTO.builder()
                .x(request.getX())
                .y(request.getY())
                .r(request.getR())
                .reqTime(new Date())
                .build();

        long startTime = System.nanoTime() / 1000;
        response.setHit(isHit(request.getX(), request.getY(), request.getR()));
        long endTime = System.nanoTime() / 1000;
        response.setProcTime(endTime - startTime);
        return response;
    }

    private boolean isHit(double x, double y, double r) {
        return x >= 0 && y >= 0 && x * x + y * y <= r * r ||
                x <= 0 && y <= 0 && -x <= r / 2 && -y <= r ||
                x >= 0 && y <= 0 && y >= x - r;
    }
}
