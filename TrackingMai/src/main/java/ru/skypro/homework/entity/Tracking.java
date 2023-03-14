package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.model.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
//
@Data
@Entity
@Table(name = "tracking")
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @JoinColumn(name = "mail_id")
    Integer mailId;

    @JoinColumn(name = "post_index")
    Integer postIndex;

    LocalDateTime dateTime;
    Status status;

}
