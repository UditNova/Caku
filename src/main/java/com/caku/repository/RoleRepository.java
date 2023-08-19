package com.caku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caku.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}
