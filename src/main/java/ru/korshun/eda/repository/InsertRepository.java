package ru.korshun.eda.repository;

import org.springframework.stereotype.Repository;
import ru.korshun.eda.service.CustomUserDetailsService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class InsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    final CustomUserDetailsService mCustomUserDetailsService;

    public InsertRepository(CustomUserDetailsService mCustomUserDetailsService) {
        this.mCustomUserDetailsService = mCustomUserDetailsService;
    }

    @Transactional
    public boolean insertLocation(int id, double lat, double lon) {

        try {
            entityManager.createNativeQuery(
                    "INSERT INTO coordinates (lat, long, id_user, datetime) " +
                            "VALUES (?,?,?,GETDATE())")
                    .setParameter(1, lat)
                    .setParameter(2, lon)
                    .setParameter(3, id)
                    .executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
