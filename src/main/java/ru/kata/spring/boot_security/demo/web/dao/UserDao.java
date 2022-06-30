package ru.kata.spring.boot_security.demo.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.web.models.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByName(String username);

}
