package com.example.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class LocalUserRoleId {
	private Integer userId;
	private String roleName;
	
	public LocalUserRoleId(Integer userId, String roleName) {
		this.userId = userId;
		this.roleName = roleName;
	}
	
	public LocalUserRoleId() {
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		LocalUserRoleId other = (LocalUserRoleId) obj;
		
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		
		if (roleName == null) {
			if (other.roleName != null) {
				return false;
			}
		} else if (!roleName.equals(other.roleName)) {
			return false;
		}
		
		return true;
	}
}
