package com.scrapbot.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.scrapbot.entity.NewsCompany;
import com.scrapbot.repository.NewsComapnyRepository;
import com.scrapbot.repository.UserRepository;
import com.scrapbot.service.NewsCompanyService;

@Service
public class NewsCompanyServiceImpl implements NewsCompanyService {
	@Autowired
	NewsComapnyRepository comapnyRepository;
	@Autowired
	UserRepository repository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Optional<NewsCompany> selectCompany(Long id) {
		logger.info(comapnyRepository.findById(id).get().getCompanyName());
		return comapnyRepository.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public List<NewsCompany> selectCompanyList() {
		return ImmutableList.copyOf(comapnyRepository.findAll());
	}
}
