package com.scrapbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scrapbot.entity.NewsCompany;


@Repository
public interface NewsComapnyRepository extends CrudRepository<NewsCompany, Long>{

	
}

