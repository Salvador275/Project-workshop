package com.crusher.work.repositories;

import com.crusher.work.entities.Application;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
    Application findApplicationById(Long id);
}
