package com.scrapbot.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString(exclude = "newsCompanySet")
@Entity
@Table(name = "user_table")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter
	@Getter
	private Long id;

	@Column
	@Setter
	@Getter
	private String email;

	@Getter
	@Setter
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> keywords = new ArrayList<String>();

	@JsonManagedReference
	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable( name = "news_set", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "newscompany_id"))
	private Set<NewsCompany> newsCompanySet;
//	
	@Column
	@Setter
	@Getter
	private String grade;

}
