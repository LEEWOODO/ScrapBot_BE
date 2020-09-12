package com.scrapbot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString(exclude = {"user"})
@Entity
@Table(name = "keyword_table")
public class Keyword {
	@Id
	@GeneratedValue
	@Setter
	@Getter
	private Integer id;

	@Setter
	@Getter
	private String keyword;

	@ManyToOne
	@Setter
	@Getter
	private User user;
}
