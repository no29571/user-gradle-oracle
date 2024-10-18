package com.example.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LocalUser extends AuditingEntity {//local_user
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@Column(length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(nullable = false)
	private boolean enabled;
	
	//FetchType.LAZY：getter呼び出し時に要@Transactional
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<LocalUserRole> userRole;
}
