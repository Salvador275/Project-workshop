package com.crusher.work.repositories;

import com.crusher.work.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findClientById(Long id);
}
