package ru.korshun.eda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.korshun.eda.entity.Alarms;

import java.util.List;

@Repository
public interface AlarmsRepository
        extends JpaRepository<Alarms, Integer> {


    @Query(value = "SELECT id, name FROM users WHERE id_role=3;", nativeQuery = true)
    List<Gbr> findAllGbr();


//    @Query(value = "SELECT users.name, users.phone, location_alarm.lat, location_alarm.lon, location_alarm.gps_enable, " +
//                    "location_alarm.nw_enable " +
//                    "FROM location_alarm " +
//                    "LEFT JOIN users ON users.id = location_alarm.id_user AND users.enable = 1;", nativeQuery = true)
//    List<ForGbrOnly> getAllAlarms();
//    @Transactional
//    @Query(value = "SELECT location_alarm.lat, location_alarm.lon, users.name, users.phone " +
//                    "FROM location_alarm " +
//                    "LEFT JOIN users ON users.id = location_alarm.id_user " +
//                    "WHERE id_gbr = ?1", nativeQuery = true)
//    List<ForGbrOnly> getGbrAlarms(int id);
//
//

    interface Gbr {

        int getId();
        String getName();

    }

}
