package ru.korshun.eda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.korshun.eda.entity.User;

@Repository
public interface UserRepository
    extends JpaRepository<User, Integer> {

    User findByLogin(String login);
    User findById(long id);

}
