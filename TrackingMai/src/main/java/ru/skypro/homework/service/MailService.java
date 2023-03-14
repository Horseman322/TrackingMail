package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Mail;
import ru.skypro.homework.entity.Post;
import ru.skypro.homework.repository.MailRepository;
import ru.skypro.homework.repository.PostRepository;

import java.util.List;
//
@Service
@AllArgsConstructor
public class MailService {
    private final MailRepository mailRepository;
    private final PostRepository postRepository;

    public List<Mail> getAllMail() {
        return mailRepository.findAll();
    }

    public Mail getMail(Integer id) {
        return mailRepository.findById(id).orElse(null);
    }

    public Mail createMail(Mail mail) {
        Post post = postRepository.findByIndex(mail.getRecipientIndex());
        Mail mailCreate = mailRepository.findById(mail.getId()).orElse(null);
        if (mailCreate == null && post != null) {
            return mailRepository.save(mail);
        } else {
            return null;
        }
    }

    public Mail updateMail(Mail mail) {
        Post post = postRepository.findByIndex(mail.getRecipientIndex());
        Mail mailUpdate = mailRepository.findById(mail.getId()).orElse(null);
        if (mailUpdate != null && post != null) {
            return mailRepository.save(mail);
        } else {
            return null;
        }
    }

    public boolean deleteMail(Integer id) {
        Mail mail = mailRepository.findById(id).orElse(null);
        if (mail == null) {
            return false;
        } else {
            mailRepository.deleteById(id);
            return true;
        }
    }

}
