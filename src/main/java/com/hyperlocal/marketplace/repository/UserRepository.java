package com.hyperlocal.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hyperlocal.marketplace.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}