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
                            "VALUES (?,?,?,GETDATE(), ?, ?)")
                    .setParameter(1, lat)
                    .setParameter(2, lon)
                    .setParameter(3, id)
                    .setParameter(4, isGpsEnable)
                    .setParameter(5, isNetworkEnable)
                    .executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
