package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Mail;
import ru.skypro.homework.repository.MailRepository;
import java.util.List;
//
@Service
@AllArgsConstructor
public class MailService {
    private final MailRepository mailRepository;

    public List<Mail> getAllMail() {
        return mailRepository.findAll();
    }

    public Mail getMail(Integer id) {
        return mailRepository.findById(id).orElse(null);
    }

    public Mail createMail(Mail mail) {
        return mailRepository.save(mail);
    }

    public Mail updateMail(Mail mail) {
        return mailRepository.save(mail);
    }

    public boolean deleteMail(Integer id) {
        Mail mail = mailRepository.findById(id).orElse(null);
        return mail != null;
    }

}
