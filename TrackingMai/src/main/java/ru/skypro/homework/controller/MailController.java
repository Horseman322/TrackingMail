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
import ru.skypro.homework.entity.Mail;
import ru.skypro.homework.service.MailService;

import java.util.List;

//
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final Logger logger = LoggerFactory.getLogger(MailController.class);
    private final MailService mailService;

    @Operation(summary = "Поиск всех почтовых отправлений",
            description = "нажмите на кнопку",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные почтовые отправления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Mail.class))
                            )
                    )
            }
    )
    @GetMapping
    public List<Mail> getAllMail() {
        return mailService.getAllMail();
    }


    @Operation(summary = "Поиск почтового отправления по его уникальному ID",
            description = "введите ID почтового отправления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найдено почтовое отправление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Mail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "ничего не найдено"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getMail(@PathVariable Integer id) {
        logger.info("MailController. method getMail. Mail id = " + id);
        Mail mail = mailService.getMail(id);
        if (mail == null) {
            return ResponseEntity.status(404).body("Mail item with this identifier was not found");
        } else {
            return ResponseEntity.status(200).body(mail);
        }
    }


    @Operation(summary = "Добавление почтового отправления с его уникальным ID",
            description = "введите ID (можно и не вводить, система сама сгенерирует его), тип (цифрами от 0 до 3), индекс получателя, адрес и имя получателя почтового отправления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "почтовое отправление успешно сохранено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Mail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "почтовое отправление с таким ID уже существует"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> createMail(@RequestBody Mail mail) {
        logger.info("MailController. method createMail. Mail = " + mail);
        if (mailService.createMail(mail) == null) {
            return ResponseEntity.status(409).body("Perhaps a mail list with this ID already exists or there is no post office with this index");
        } else {
            return ResponseEntity.status(200).body(mail);
        }
    }


    @Operation(summary = "Изменение почтового отправления по его уникальному ID",
            description = "введите ID, тип (можно цифрами от 0 до 3), индекс получателя, адрес и имя получателя почтового отправления. почтовое отправление с таким ID должен существовать",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "почтовое отправление успешно изменено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Mail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "почтовое отправление с таким ID не существует"
                    )
            }
    )
    @PatchMapping
    public ResponseEntity<?> updateMail(@RequestBody Mail mail) {
        logger.info("MailController. method updateMail. Mail = " + mail);
        if (mailService.updateMail(mail) == null) {
            return ResponseEntity.status(404).body("Mail item with this identifier was not found");
        } else {
            return ResponseEntity.status(200).body(mail);
        }
    }


    @Operation(summary = "Удаление почтового отправления по его уникальному ID",
            description = "введите ID почтового отправления для удаления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "почтовое отправление успешно удалено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Mail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "почтовое отправление с таким ID не существует"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMail(@PathVariable Integer id) {
        logger.info("MailController. method deleteMail. Id = " + id);
        if (mailService.deleteMail(id)) {
            return ResponseEntity.status(200).body("Mail item deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Mail item with this identifier was not found");
        }
    }


}
