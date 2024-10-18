package com.example.repositry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.entity.LocalUser;

import jakarta.persistence.LockModeType;

public interface LocalUserRepository extends JpaRepository<LocalUser, Integer> {
	//ログインと重複チェックで使用
	Optional<LocalUser> findByEmail(String email);
	//boolean existsByEmail(String email);
	
	//一覧の初期表示で使用
	List<LocalUser> findAllByOrderByEmailAsc();
	
	//searchフォームで使用
	List<LocalUser> findByEmailContainingOrderByEmailAsc(String keyword);
	
	//同時更新回避で使用（FOR UPDATE）
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<LocalUser> findOneForUpdateByEmail(String email);
}
