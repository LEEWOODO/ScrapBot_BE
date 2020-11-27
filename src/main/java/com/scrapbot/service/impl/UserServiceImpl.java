package com.scrapbot.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.scrapbot.entity.NewsArticle;
import com.scrapbot.entity.NewsCompany;
import com.scrapbot.entity.User;
import com.scrapbot.repository.NewsComapnyRepository;
import com.scrapbot.repository.UserRepository;
import com.scrapbot.service.NewsCompanyService;
import com.scrapbot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<User> selectUserList() {
		return ImmutableList.copyOf(userRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Optional<User> selectUser(Long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void insertUser(User user) {
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void addKeyword(Long id, String keyWord) {
		logger.info("your message3");
		User user = userRepository.findById(id).get();
		logger.info(user.getEmail());
		user.addKeyword(keyWord);
		userRepository.save(user);
		logger.info("your message4");
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void removeKeyword(Long id, String keyWord) {
		logger.info("your message3");
		User user = userRepository.findById(id).get();
		logger.info(user.getEmail());
		user.removeBom(keyWord);
		userRepository.save(user);
		logger.info("your message4");

	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void addNewsCompany(Long userid, NewsCompany company) {
		logger.info("add newComapny obj to user by  ");
		User user = userRepository.findById(userid).get();
		// 문제 발생
		// autowired로 다른 리퍼지토리를 생성할 수 없다. -> service 안에서는 다른클래스의 repository를 사용 할 수 없으며
		// 대신 다른 서비스 객체를 생성하여 사용한다.
		user.addNewCompany(company);
		logger.info("CompanyName : " + company.getCompanyName());

		userRepository.save(user);
		logger.info("update successes");
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void subNewsCompany(Long userid, NewsCompany company) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userid).get();
		// 문제 발생
		// autowired로 다른 리퍼지토리를 생성할 수 없다. -> service 안에서는 다른클래스의 repository를 사용 할 수 없으며
		// 대신 다른 서비스 객체를 생성하여 사용한다.

		logger.info("CompanyName : ");
		user.removeCompany(company);
		logger.info("CompanyName : " + company.getCompanyName() + "삭제 함");

		userRepository.save(user);
		logger.info("update successes");
	}

	@Override
	public Set<NewsArticle> filterArticlesByKeywords(List<NewsArticle> articles, Set<String> keywords) {
		// TODO Auto-generated method stub

		return keywords.stream()
				.flatMap(keyword -> articles.stream().filter(article -> article.getText().contains(keyword)))
				.collect(Collectors.toSet());
		
	}

}
