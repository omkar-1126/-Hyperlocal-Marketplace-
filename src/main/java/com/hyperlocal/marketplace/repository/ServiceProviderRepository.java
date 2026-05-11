package com.hyperlocal.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hyperlocal.marketplace.model.ServiceProvider;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    List<ServiceProvider> findByServiceType(String serviceType);
}
