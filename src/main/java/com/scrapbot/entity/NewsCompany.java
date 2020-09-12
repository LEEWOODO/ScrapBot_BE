package com.scrapbot.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "news_company")
public class NewsCompany {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String companyName;

	@Override
	public String toString() {
		return "NewsCompany [id=" + id + ", companyName=" + companyName + ", companyIdOnNaver=" + companyIdOnNaver
				+ "]";
	}


	@Column
	private String companyIdOnNaver;


	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "newsCompanySet")
	Set<User> userSet;
	

}
