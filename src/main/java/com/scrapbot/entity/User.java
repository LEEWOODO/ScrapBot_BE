package com.scrapbot.entity;

import java.util.ArrayList;
import java.util.HashSet;
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

@ToString(exclude = { "newsCompanySet", "keywords" })
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

//	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//	@OrderColumn(name = "keywords_index")
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> keywords = new HashSet<String>();

	public void addKeyword(String keyword) {
		keywords.add(keyword);
	
	}

	public void removeBom(String keyword) {
		keywords.remove(keyword);
		
	}

	@JsonManagedReference
	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "news_set",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "newscompany_id"))
	private Set<NewsCompany> newsCompanySet= new HashSet<NewsCompany>();
	
	
	public void addNewCompany(NewsCompany company) {
		newsCompanySet.add(company);
		company.getUserSet().add(this);
	}
	public void removeCompany(NewsCompany company) {
		System.out.println(company.getId());
		System.out.println(newsCompanySet);
		newsCompanySet.remove(company);
		System.out.println(newsCompanySet);
		company.getUserSet().remove(this);
	}
	
//	
	@Column
	@Setter
	@Getter
	private String grade;

	public Set<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}

}
