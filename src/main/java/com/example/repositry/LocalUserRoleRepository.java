package com.example.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.LocalUserRole;
import com.example.entity.LocalUserRoleId;

public interface LocalUserRoleRepository extends JpaRepository<LocalUserRole, LocalUserRoleId> {
	List<LocalUserRole> findAllByOrderByUserIdAscRoleNameAsc();
	
	List<LocalUserRole> findByUserId(Integer userId);
	
	List<LocalUserRole> findByRoleName(String roleName);
	
	boolean existsByRoleName(String roleName);
		
	List<LocalUserRole> deleteByUserId(Integer userId);
}
