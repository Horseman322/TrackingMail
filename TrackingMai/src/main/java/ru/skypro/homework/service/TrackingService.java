package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.repository.TrackingRepository;
//
@Service
@AllArgsConstructor
public class TrackingService {
    private final TrackingRepository trackingRepository;


}
