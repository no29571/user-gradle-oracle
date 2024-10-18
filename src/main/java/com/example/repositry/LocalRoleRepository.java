package com.example.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.LocalRole;

public interface LocalRoleRepository extends JpaRepository<LocalRole, String> {

}
