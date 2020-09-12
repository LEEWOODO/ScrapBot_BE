package com.scrapbot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.scrapbot.entity.User;
import com.scrapbot.repository.UserRepository;
import com.scrapbot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

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

}
