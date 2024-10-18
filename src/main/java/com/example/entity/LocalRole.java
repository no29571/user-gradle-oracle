package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LocalRole extends AuditingEntity {//local_role
	@Id
	@Column(length = 10, nullable = false)
	private String name;
}
