package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Mail;
//
@Repository
public interface MailRepository extends JpaRepository<Mail, Integer> {

}
