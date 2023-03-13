package ru.skypro.homework.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.service.TrackingService;
//
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/tracking")
public class TrackingController {

    private final Logger logger = LoggerFactory.getLogger(TrackingController.class);
    private final TrackingService trackService;


}
