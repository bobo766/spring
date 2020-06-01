package ru.korshun.eda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.korshun.eda.entity.Ajax;

@Repository
public interface AjaxRepository
        extends JpaRepository<Ajax, Integer> {


}
