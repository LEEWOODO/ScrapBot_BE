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
	private Long id;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyIdOnNaver == null) ? 0 : companyIdOnNaver.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsCompany other = (NewsCompany) obj;
		if (companyIdOnNaver == null) {
			if (other.companyIdOnNaver != null)
				return false;
		} else if (!companyIdOnNaver.equals(other.companyIdOnNaver))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
