package ru.korshun.eda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.korshun.eda.entity.Role;
import ru.korshun.eda.entity.User;

import java.util.List;

@Repository
public interface BossRepository
    extends JpaRepository<User, Integer> {

    Boolean existsByPhone(String phone);

    @Query(value = "SELECT users.id, users.name, users.phone, roles.role FROM users " +
                    "LEFT JOIN roles ON roles.id = users.id_role " +
                    "ORDER BY users.id_role ASC, users.name ASC;",
            nativeQuery = true)
    List<Users> findAllUsers();

    @Query(value = "SELECT * FROM roles WHERE role NOT LIKE '" + Role.ADMIN + "' " +
            "AND role NOT LIKE '" + Role.OPERATOR + "';",
            nativeQuery = true)
    List<Roles> findRoles();


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (password, name, phone, id_role) " +
            "SELECT ?1, ?2, ?3, id FROM roles WHERE role=?4", nativeQuery = true)
    void addUser(String password, String name, String phone, String role);


    interface Roles {
        int getId();
        String getRole();
    }

    interface Users {
        int getId();
        String getName();
        String getPhone();
        String getRole();
    }

}
