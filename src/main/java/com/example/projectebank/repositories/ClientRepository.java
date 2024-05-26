package com.example.projectebank.repositories;

import com.example.projectebank.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNameContains(String keyword);
}
