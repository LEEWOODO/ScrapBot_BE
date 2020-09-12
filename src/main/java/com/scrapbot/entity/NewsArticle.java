package com.scrapbot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "articletable")
public class NewsArticle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "newcompany")
	private String newcompany;

	@Column(name = "title")
	private String title;

	@Column(name = "topornot")
	private boolean topornot;

	@Column(name = "pagenumber")
	private String pagenumber;

	@Column(name = "urlpath")
	private String url;

//	@Column
//	private String text;

	@Column
	private String regdate;


}
