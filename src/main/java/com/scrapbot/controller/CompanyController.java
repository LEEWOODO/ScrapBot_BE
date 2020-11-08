package com.scrapbot.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scrapbot.config.ApiResponseMessage;
import com.scrapbot.entity.NewsCompany;
import com.scrapbot.entity.User;
import com.scrapbot.repository.UserRepository;
import com.scrapbot.service.NewsCompanyService;
import com.scrapbot.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Api(value = "CompanyController", description = "신문회사 관련 API")
@RequestMapping(value = "/api", method = RequestMethod.GET)
public class CompanyController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private final NewsCompanyService newsCompanyService = null;
	// 예제
	@GetMapping("/company-list")
	@ApiOperation(httpMethod = "GET", value = "신문사 목록 조회", notes = "신문사 목록을 조회하는 API")
	public List<NewsCompany> getCompanyList() {
		return newsCompanyService.selectCompanyList();
	}
}
