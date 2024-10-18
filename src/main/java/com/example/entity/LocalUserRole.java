package com.example.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LocalUserRole extends AuditingEntity {//local_user_role
	@EmbeddedId
	LocalUserRoleId id;
	
	//N+1問題
	//left joinしつつも結合テーブルの列はselectせず
	//外部テーブルを個別にselectしてしまう
	//外部キーが複数あれば、外部キーの数×N+1
	//FetchType.LAZYで必要なときにだけ外部テーブルをselect
	//件数の多い一覧で、外部テーブルの属性参照は要注意
	
	//ManyToOneのデフォルトはFetchType.EAGER
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id", 
		insertable = false, updatable = false)
	@MapsId("userId")
	private LocalUser user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleName", referencedColumnName = "name", 
		insertable = false, updatable = false)
	@MapsId("roleName")
	private LocalRole role;
	
	public LocalUserRole(LocalUser user, LocalRole role) {
		this.id = new LocalUserRoleId(user.getId(), role.getName());
		this.user = user;
		this.role = role;
	}
	
	public LocalUserRole() {
		
	}
}
