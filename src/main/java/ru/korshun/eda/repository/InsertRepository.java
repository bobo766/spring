package ru.korshun.eda.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class InsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public boolean insertLocation(int id, double lat, double lon, int isGpsEnable, int isNetworkEnable) {

        try {
            entityManager.createNativeQuery(
                    "INSERT INTO location_alarm (lat, lon, id_user, date_time, gps_enable, nw_enable) " +
                            "VALUES (?, ?, ?, GETDATE(), ?, ?)")
                    .setParameter(1, lat)
                    .setParameter(2, lon)
                    .setParameter(3, id)
                    .setParameter(4, isGpsEnable)
                    .setParameter(5, isNetworkEnable)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean finishAlarm(int idUser, int idgbr) {

        try {
            entityManager.createNativeQuery(
                    "INSERT INTO location_alarm_done (lat, lon, id_user, date_time, id_gbr, date_time_done, gps_enable, nw_enable) " +
                            "SELECT lat, lon, id_user, date_time, ?1, GETDATE(), gps_enable, nw_enable FROM location_alarm WHERE id_user=?2; " +
                            "DELETE FROM location_alarm WHERE id_user=?2; ")
                    .setParameter(1, idgbr)
                    .setParameter(2, idUser)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
