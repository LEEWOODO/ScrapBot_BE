package com.scrapbot.service;

import java.util.List;
import java.util.Optional;

import com.scrapbot.entity.NewsCompany;
import com.scrapbot.entity.User;

public interface NewsCompanyService {
	/** * 신문사 정보 조회 * @param uid * @return */
	public Optional<NewsCompany> selectCompany(Long id);

	public List<NewsCompany> selectCompanyList();
}