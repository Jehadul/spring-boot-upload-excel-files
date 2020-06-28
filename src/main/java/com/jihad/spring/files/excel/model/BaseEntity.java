package com.jihad.spring.files.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract public class BaseEntity<T> {

	@Column(name = "CREATOR")
	private T creator;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "MODIFIER")
	private T modifier;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "ACTIVE_STATUS")
	private Integer activeStatus = 1;

	@PrePersist
	private void preCreateFn() {
		this.createdDate = new Date();
		this.modifiedDate = new Date();
	}

	@PreUpdate
	private void preUpdateFn() {
		this.modifiedDate = new Date();
	}

}
