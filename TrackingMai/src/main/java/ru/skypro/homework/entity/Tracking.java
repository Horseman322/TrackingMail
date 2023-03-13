package ru.skypro.homework.entity;

import lombok.Data;
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

    Integer mailingId;
    Integer PostIndex;

    LocalDateTime dateTime;
    String status;

}
