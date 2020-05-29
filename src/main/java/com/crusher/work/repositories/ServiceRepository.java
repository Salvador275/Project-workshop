package com.crusher.work.repositories;

import com.crusher.work.entities.Service;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<Service, Long> {
    Service findServiceById(Long id);
}
