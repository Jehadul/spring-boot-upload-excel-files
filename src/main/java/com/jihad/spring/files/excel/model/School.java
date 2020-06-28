package com.jihad.spring.files.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "school")
public class School extends BaseEntity<Long> implements Serializable {
	
	private static final long serialVersionUID = 1010010001;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "school_code", nullable = false)
	private String schoolCode;

	@Column(name = "school_name", nullable = false)
	private String schoolName;
	
	@Column(name = "gender", nullable = false)
	private String gender;
	
	@Column(name = "boarding", nullable = false)
	private String boarding;

}
