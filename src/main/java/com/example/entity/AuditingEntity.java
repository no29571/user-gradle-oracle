package com.example.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditingEntity {
	@Version
	@Column(nullable = false)
	private Long ver;
	
	@CreatedBy
	@Column(nullable = false, updatable = false)
	private Integer createdBy;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate;
	
	@LastModifiedBy
	@Column(nullable = false)
	private Integer modifiedBy;
	
	@LastModifiedDate
	@Column(nullable = false)
    private LocalDateTime modifiedDate;
}
