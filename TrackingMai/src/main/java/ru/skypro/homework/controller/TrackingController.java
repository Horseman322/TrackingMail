package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.entity.Tracking;
import ru.skypro.homework.model.TrackDto;
import ru.skypro.homework.service.TrackingService;


import java.util.List;

//
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/tracking")
public class TrackingController {

    private final Logger logger = LoggerFactory.getLogger(TrackingController.class);
    private final TrackingService trackingService;

    @Operation(summary = "Регистрация почтового отправления. Необходимо указать ID отправления и индекс почтового отделения",
            description = "введите ID почтового отправления и индекс почтового отделения",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "передвижение почтового отправления успешно сохранено. возвращает только текстовое сообщение от сервиса независимо от результата",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Tracking.class)
                            )
                    )
            }
    )
    @PostMapping("/registered")
    public String registeredMail(@RequestBody TrackDto trackDto) {
        logger.info("TrackingController. method registeredMail. TrackDto = " + trackDto);
        return trackingService.registeredMail(trackDto);
    }


    @Operation(summary = "Прибытие почтового отправления. Необходимо указать ID отправления и индекс почтового отделения",
            description = "введите ID почтового отправления и индекс почтового отделения",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "передвижение почтового отправления успешно сохранено. возвращает только текстовое сообщение от сервиса независимо от результата",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Tracking.class)
                            )
                    )
            }
    )
    @PostMapping("/arrive")
    public String arriveMail(@RequestBody TrackDto trackDto) {
        logger.info("TrackingController. method arriveMail. TrackDto = " + trackDto);
        return trackingService.arriveMail(trackDto);
    }


    @Operation(summary = "Отправление почтового отправления. Необходимо указать ID отправления и индекс почтового отделения",
            description = "введите ID почтового отправления и индекс почтового отделения",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "передвижение почтового отправления успешно сохранено. возвращает только текстовое сообщение от сервиса независимо от результата",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Tracking.class)
                            )
                    )
            }
    )
    @PostMapping("/departed")
    public String departedMail(@RequestBody TrackDto trackDto) {
        logger.info("TrackingController. method departedMail. TrackDto = " + trackDto);
        return trackingService.departedMail(trackDto);
    }


    @Operation(summary = "Отметка о получении почтового отправления. Необходимо указать ID отправления и индекс почтового отделения. Если индекс получателя не совпадёт с текущим отделением придёт ошибка",
            description = "введите ID почтового отправления и индекс почтового отделения",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "передвижение почтового отправления успешно сохранено. возвращает только текстовое сообщение от сервиса независимо от результата",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Tracking.class)
                            )
                    )
            }
    )
    @PostMapping("/received")
    public String receivedMail(@RequestBody TrackDto trackDto) {
        logger.info("TrackingController. method receivedMail. TrackDto = " + trackDto);
        return trackingService.receivedMail(trackDto);
    }


    @Operation(summary = "Отслеживает всю историю передвижений почтового отправления. Необходимо указать ID отправления",
            description = "введите ID почтового отправления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "история передвижения почтового отправления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Tracking.class))
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> trackingHistoryMailing(@PathVariable Integer id) {
        logger.info("TrackingController. method trackingHistoryMailing. Mail ID = " + id);
        List<String> history = trackingService.trackingHistoryMail(id);
        if (history.isEmpty()) {
            return ResponseEntity.status(404).body("Mail item with this identifier was not found");
        } else {
            return ResponseEntity.status(200).body(history);
        }
    }


}
