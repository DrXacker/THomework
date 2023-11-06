package com.example.demo.repository.hibernate;

import com.example.demo.model.db.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RolesJpaRepository extends JpaRepository<Role, Long> {
    Set<Role> findByName(String roleName);
}
