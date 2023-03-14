package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Tracking;

import java.util.List;

//
@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Integer> {

    List<Tracking> findByMailId(Integer id);

}
