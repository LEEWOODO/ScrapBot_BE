package com.scrapbot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scrapbot.config.ApiResponseMessage;
import com.scrapbot.entity.NewsArticle;
import com.scrapbot.entity.NewsCompany;
import com.scrapbot.entity.User;
import com.scrapbot.service.NewsArticleService;
import com.scrapbot.service.NewsCompanyService;
import com.scrapbot.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Api(value = "UserController", description = "사용자 관련 API")
@RequestMapping(value = "/api", method = RequestMethod.GET)
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private final UserService userService = null;

	@Autowired
	private final NewsCompanyService newsCompanyService = null;
	
	@Autowired
	private final NewsArticleService articleService = null;

	// 예제
	@GetMapping("/user")
	@ApiOperation(httpMethod = "GET", value = "사용자 목록 조회", notes = "사용자 목록을 조회하는 API")
	public List<User> getUserList() {
		logger.info("your message");
		return userService.selectUserList();
	}

	@GetMapping("/user/{id}")
	@ApiOperation(httpMethod = "GET", value = "사용자 정보 조회", notes = "사용자의 정보를 조회하는 API. User entity 클래스의 id값을 기준으로 데이터를 가져온다.")
	public Optional<User> getUser(@PathVariable("id") Long id) {
		return userService.selectUser(id);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "사용자 정보 등록", notes = "사용자 정보를 저장하는 API. User entity 클래스로 데이터를 저장한다.")
	public ResponseEntity<ApiResponseMessage> insertUser(User user) {
		ApiResponseMessage message = new ApiResponseMessage("Success", "등록되었습니다.", "", "");
		ResponseEntity<ApiResponseMessage> response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
		try {
			userService.insertUser(user);
		} catch (Exception ex) {
			message = new ApiResponseMessage("Failed", "사용자 등록에 실패하였습니다.", "ERROR00001",
					"Fail to registration for user information.");
			response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보를 수정하는 API. User entity 클래스로 데이터를 수정한다.이때엔 정보를 등록할 때와는 다르게 id 값을 함깨 보내줘야한다.")
	public ResponseEntity<ApiResponseMessage> updateUser(User user) {
		ApiResponseMessage message = new ApiResponseMessage("Success", "수정되었습니다.", "", "");
		ResponseEntity<ApiResponseMessage> response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
		try {
			userService.updateUser(user);
		} catch (Exception ex) {
			message = new ApiResponseMessage("Failed", "사용자 정보 수정에 실패하였습니다.", "ERROR00002",
					"Fail to update for user information.");
			response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "사용자 정보 삭제", notes = "사용자 정보를 삭제하는 API. User entity 클래스의 id 값으로 데이터를 삭제한다.")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("id") Long id) {
		ApiResponseMessage message = new ApiResponseMessage("Success", "삭제되었습니다.", "", "");
		ResponseEntity<ApiResponseMessage> response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
		try {
			userService.deleteUser(id);
		} catch (Exception ex) {
			message = new ApiResponseMessage("Failed", "사용자 정보 삭제에 실패하였습니다.", "ERROR00003",
					"Fail to remove for user information.");
			response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}

	@GetMapping("/user/info/{email}")
	@ApiOperation(httpMethod = "GET", value = "email 별 유저 조회", notes = "email 별 유저 조회api")
	public List<User> findByRegdate(@PathVariable("email") String email, @RequestParam(value = "year") String year) {
		// string like 는 containing 을 이용하는것이 잘 되는듯. 개인적인 우도 생각
		return userService.findByEmail(email);
	}

	@RequestMapping(value = "/user/keyword/add", method = RequestMethod.PUT)
	@ApiOperation(httpMethod = "PUT", value = "add keyword to specific user", notes = "an add api that a keyword to a user")
	public ResponseEntity<ApiResponseMessage> addKeyword(@RequestParam(value = "id") Long id,
			@RequestParam(value = "keyWord") String keyWord) {
		ApiResponseMessage message = new ApiResponseMessage("Success", "키워드가 추가되었습니.", "", "");
		ResponseEntity<ApiResponseMessage> response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
		logger.info("your message");
		try {
			logger.info("your message");
			userService.addKeyword(id, keyWord);
		} catch (Exception ex) {
			message = new ApiResponseMessage("Failed", "사용자 키워드 등에 실패하였습니다.", "ERROR00003",
					"Fail to remove for user information.");
			response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/user/keyword/delete", method = RequestMethod.PUT)
	@ApiOperation(httpMethod = "PUT", value = "delete keyword to specific user", notes = "an delete api that a keyword to a user")
	public ResponseEntity<ApiResponseMessage> deleteKeyword(@RequestParam(value = "id") Long id,
			@RequestParam(value = "keyWord") String keyWord) {
		ApiResponseMessage message = new ApiResponseMessage("Success", "키워드가 삭제되었습니.", "", "");
		ResponseEntity<ApiResponseMessage> response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
		logger.info("your message");
		try {
			logger.info("your message");
			userService.removeKeyword(id, keyWord);
		} catch (Exception ex) {
			message = new ApiResponseMessage("Failed", "사용자 키워드 삭에 실패하였습니다.", "ERROR00003",
					"Fail to remove for user information.");
			response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	// REST API 만들기
	// 유저에게 신문사 정보를 추가하는 REST API.
	@RequestMapping(value = "/user/news-comany/add", method = RequestMethod.PUT)
	@ApiOperation(httpMethod = "PUT", value = "add companyList User", notes = "an REST API adding comany object to user")
	public ResponseEntity<ApiResponseMessage> addNewsCompany(@RequestParam(value = "userid") Long userid,
			@RequestParam(value = "companyid") Long companyid) {
		ApiResponseMessage message = new ApiResponseMessage("Success", "뉴스정보가 추가되었습니다.", "", "");
		// 응답시 돌려줄 객체 생성 - 초기값은 성공으로 세팅
		// 에러발생시 실패 메서지로 교체
		ResponseEntity<ApiResponseMessage> response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
		logger.info("response obj created success");

		try {
			userService.addNewsCompany(userid, newsCompanyService.selectCompany(companyid).get());
		} catch (Exception e) {
			message = new ApiResponseMessage("Failed", "뉴스정보 추가에 실패하였습니다.", "ERROR00003",
					"Fail to remove for user information.");
			response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	// REST API 만들기
	// 유저에게 신문사 정보를 추가하는 REST API.
	@RequestMapping(value = "/user/news-comany/sub", method = RequestMethod.PUT)
	@ApiOperation(httpMethod = "PUT", value = "add companyList User", notes = "an REST API adding comany object to user")
	public ResponseEntity<ApiResponseMessage> subNewsCompany(@RequestParam(value = "userid") Long userid,
			@RequestParam(value = "companyid") Long companyid) {
		ApiResponseMessage message = new ApiResponseMessage("Success", "뉴스정보가 삭제되었습니다.", "", "");
		// 응답시 돌려줄 객체 생성 - 초기값은 성공으로 세팅
		// 에러발생시 실패 메서지로 교체
		ResponseEntity<ApiResponseMessage> response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
		logger.info("response obj created success");

		try {
			userService.subNewsCompany(userid, newsCompanyService.selectCompany(companyid).get());
		} catch (Exception e) {
			message = new ApiResponseMessage("Failed", "뉴스정보 삭제에 실패하였습니다.", "ERROR00003",
					"Fail to remove for user information.");
			response = new ResponseEntity<ApiResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@GetMapping("/user/contents/{id}/{date}")
	@ApiOperation(httpMethod = "GET", value = "사용자에게 스크랩 결과를 보여줌", notes = "스크랩 결과를 보여줌 API. User entity 클래스의 id값을 기준으로 데이터를 가져온다.")
	public List<NewsArticle> getUserContents(@PathVariable("id") Long id,@PathVariable("date") String date) {
		User user = userService.selectUser(50001l).get();
		List<NewsArticle> articles = articleService.findByCompaniesAndDate(user.getNewsCompanySet(),date);
		articles=userService.filterArticlesByKeywords(articles,user.getKeywords());
		return articles;
	}
	
}
