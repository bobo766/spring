package ru.korshun.eda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.korshun.eda.entity.Alarms;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AlarmsRepository
        extends JpaRepository<Alarms, Integer> {

    @Transactional
    @Query(value = "SELECT location_alarm.lat, location_alarm.lon, users.name, users.phone " +
                    "FROM location_alarm " +
                    "LEFT JOIN users ON users.id = location_alarm.id_user " +
                    "WHERE id_gbr = ?1", nativeQuery = true)
    List<ForGbrOnly> getGbrAlarms(int id);


    interface ForGbrOnly {

        String getLat();
        String getLon();
        String getName();
        String getPhone();

    }

}
