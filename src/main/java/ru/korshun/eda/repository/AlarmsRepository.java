package ru.korshun.eda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.korshun.eda.entity.Alarms;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AlarmsRepository
        extends JpaRepository<Alarms, Integer> {


    @Query(value = "SELECT id, name FROM users WHERE id_role=(SELECT id FROM roles WHERE role='GBR');", nativeQuery = true)
    List<Gbr> findAllGbr();


    @Query(value = "SELECT * FROM location_alarm WHERE date_time_done IS NULL AND id_gbr IS NULL;", nativeQuery = true)
    List<Alarms> getAllAlarms();


//    @Query(value = "INSERT INTO location_alarm_done (lat, lon, id_user, date_time, id_gbr, date_time_done, gps_enable, nw_enable) " +
//            "SELECT lat, lon, id_user, date_time, ?2, GETDATE(), gps_enable, nw_enable FROM location_alarm WHERE id_user=?1; " +
//            "DELETE FROM location_alarm WHERE id_user=?1", nativeQuery = true)
//    void finishAlarm(int idUser, int idgbr);

    @Transactional
    @Modifying
    @Query(value = "UPDATE location_alarm " +
            "SET date_time_done = GETDATE(), id_gbr=?2 " +
            "WHERE id_user=?1 AND id_gbr IS NULL AND date_time_done IS NULL;", nativeQuery = true)
    void finishAlarm(int idUser, int idgbr);


    interface Gbr {
        int getId();
        String getName();
    }

}
