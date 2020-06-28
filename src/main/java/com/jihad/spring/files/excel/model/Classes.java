package com.jihad.spring.files.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="classes")
public class Classes extends BaseEntity<Long> implements Serializable{
	
	private static final long serialVersionUID = 1010010001;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "class_code" , nullable = false)
	private String classCode;
	
	@Column(name = "class_name" , nullable = false)
	private String className;
	
	/*@Column(name = "book_price")
	private Double bookPrice;*/
	
	@ManyToOne
	@JoinColumn(name = "school_id", nullable = false)
	private School school;
	
	@Transient
	private Long schoolId;
}
