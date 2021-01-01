package com.scrapbot.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.scrapbot.entity.NewsArticle;
import com.scrapbot.entity.NewsCompany;

public interface NewsArticleService {

	/** * 기사 목록 조회 * @return */
	public List<NewsArticle> selectNewsArticleList();

	/** * 기사 조회 * @param id * @return */
	public Optional<NewsArticle> selectNewsArticle(Long id);

	/** * 기사 등록 * @param user */
	public void insertArticle(NewsArticle newsArticle);

	/** * 기사 정보 수정 * @param user */
	public void updateArticle(NewsArticle newsArticle);

	/** * 기사 삭제 * @param id */
	public void deleteArticle(Long id);
	
	/** * 신문사 별로 목록 조회 * @param newcompany */
	public List<NewsArticle> findByNewcompanyContaining(String newcompany);
	
	/** * 신문사 날짜별 목록 조회 * @param regdate */
	public List<NewsArticle> findByRegdateIs(String regdate);

	public List<NewsArticle> findByCompaniesAndDate(Set<NewsCompany> companies, String date);
	
	public List<NewsArticle> findByCompanies(Set<NewsCompany> companies);
}
