package ru.korshun.eda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.korshun.eda.entity.User;

@Repository
public interface UserRepository
    extends JpaRepository<User, Integer> {

    User findByPhone(String phone);
    User findById(int id);
    Boolean existsByPhone(String phone);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (password, name, phone, id_role) " +
            "SELECT ?1, ?2, ?3, id FROM roles WHERE role=?4", nativeQuery = true)
    void addUser(String password, String name, String phone, String role);

}
