package com.hyperlocal.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hyperlocal.marketplace.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
