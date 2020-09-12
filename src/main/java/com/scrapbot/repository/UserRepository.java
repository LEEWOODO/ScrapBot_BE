package com.scrapbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scrapbot.entity.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	// kakaoid
	//List<User> findByKakaoid(String kakaoid);
}

