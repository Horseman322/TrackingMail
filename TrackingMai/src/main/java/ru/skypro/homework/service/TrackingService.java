package ru.skypro.homework.service;

import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Mail;
import ru.skypro.homework.entity.Post;
import ru.skypro.homework.entity.Tracking;
import ru.skypro.homework.model.Status;
import ru.skypro.homework.model.TrackDto;
import ru.skypro.homework.repository.MailRepository;
import ru.skypro.homework.repository.PostRepository;
import ru.skypro.homework.repository.TrackingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.skypro.homework.model.Status.*;

//
@Service
@AllArgsConstructor
public class TrackingService {
    private final TrackingRepository trackingRepository;
    private final PostRepository postRepository;
    private final MailRepository mailRepository;

    public String registeredMail(TrackDto trackDto) {
        Status status = REGISTERED;
        return generateTextAndSaveEntity(status, trackDto);
    }

    public String arriveMail(TrackDto trackDto) {
        Status status = ARRIVE;
        return generateTextAndSaveEntity(status, trackDto);
    }

    public String departedMail(TrackDto trackDto) {
        Status status = DEPARTED;
        return generateTextAndSaveEntity(status, trackDto);
    }

    public String receivedMail(TrackDto trackDto) {
        Status status = Status.RECEIVED;
        return generateTextAndSaveEntity(status, trackDto);
    }

    public List<String> trackingHistoryMail(Integer id) {
        List<Tracking> trackingHistory = trackingRepository.findByMailId(id);
        return trackingHistory.stream().map(this::getString).collect(Collectors.toList());
    }

    private String generateTextAndSaveEntity(Status status, TrackDto trackDto) {
        if (checkForAvailability(trackDto)) {
            if (checkLogic(status, trackDto)) {
                Tracking tracking = insert(trackDto);
                tracking.setStatus(status);
                trackingRepository.save(tracking);
                return getString(tracking);
            } else {
                return "violation of the logic of movement";
            }
        } else {
            return "data entry error";
        }
    }


    private String getString(Tracking tracking) {
        switch (tracking.getStatus()) {
            case REGISTERED:
                return "Mail " + tracking.getMailId() + " registered with the post office " + tracking.getPostIndex() + " . Data and time: " + tracking.getDateTime();
            case ARRIVE:
                return "Mail " + tracking.getMailId() + " arrive at the post office " + tracking.getPostIndex() + " . Data and time: " + tracking.getDateTime();
            case DEPARTED:
                return "Mail " + tracking.getMailId() + " left the post office " + tracking.getPostIndex() + " . Data and time: " + tracking.getDateTime();
            case RECEIVED:
                return "Mail " + tracking.getMailId() + " issued to the recipient at the post office " + tracking.getPostIndex() + " . Data and time: " + tracking.getDateTime();
            default:
                return null;
        }
    }


    private Tracking insert(TrackDto trackDto) {
        Tracking tracking = new Tracking();
        tracking.setMailId(trackDto.getMail());
        tracking.setPostIndex(trackDto.getPost());
        tracking.setDateTime(LocalDateTime.now());
        return tracking;
    }


    private boolean checkForAvailability(TrackDto trackDto) {
        Post post = postRepository.findByIndex(trackDto.getPost());
        Mail mail = mailRepository.findById(trackDto.getMail()).orElse(null);
        return post != null && mail != null;
    }


    private boolean checkLogic(Status status, TrackDto trackDto) {
        List<Tracking> history = trackingRepository.findByMailId(trackDto.getMail());
        if (history.isEmpty()) {
            if (status.equals(REGISTERED)) {
                return true;
            }
        } else {
            Tracking tracking = history.get(history.size() - 1);
            Mail mail = mailRepository.findById(tracking.getMailId()).orElse(null);
            if (tracking.getStatus().equals(REGISTERED) && status.equals(DEPARTED) && tracking.getPostIndex().equals(trackDto.getPost())) {
                return true;
            }
            if (tracking.getStatus().equals(DEPARTED) && status.equals(ARRIVE) && !tracking.getPostIndex().equals(trackDto.getPost())) {
                return true;
            }
            if (tracking.getStatus().equals(ARRIVE) && status.equals(DEPARTED) && tracking.getPostIndex().equals(trackDto.getPost())) {
                return true;
            }
            if (tracking.getStatus().equals(ARRIVE) && status.equals(Status.RECEIVED) && tracking.getPostIndex().equals(trackDto.getPost()) && trackDto.getPost().equals(mail.getRecipientIndex())) {
                return true;
            }
        }
        return false;
    }


}
