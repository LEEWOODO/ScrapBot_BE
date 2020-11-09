package com.scrapbot.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scrapbot.entity.NewsArticle;
import com.scrapbot.entity.NewsCompany;


@Repository
public interface NewsArticleRepository extends CrudRepository<NewsArticle, Long>{
	
	// 하이버네이트는 명명규칙이 중요한가 보다 findBy + 테이블컬럼명 + Containing 이렇게해야 기능작동됨.
	List<NewsArticle> findByNewcompanyContaining(String newcompany);

	List<NewsArticle> findByRegdateIs(String regdate);
	
	List<NewsArticle> findByNewscompanyIdInAndRegdate(List<String> newscompanyIds,String regdate);
	
	List<NewsArticle> findByNewscompanyIdIn(List<String> newscompanyIds);
	
	List<NewsArticle> findByRegdate(String regdate);
	
}

