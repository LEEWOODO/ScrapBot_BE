package com.scrapbot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scrapbot.config.ApiResponseMessage;
import com.scrapbot.entity.User;
import com.scrapbot.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Api(value = "UserController", description = "사용자 관련 API")
@RequestMapping(value = "/api", method = RequestMethod.GET)
public class UserController {

	@Autowired
	private final UserService userService = null;

	// 예제
	@GetMapping("/user")
	@ApiOperation(httpMethod = "GET", value = "사용자 목록 조회", notes = "사용자 목록을 조회하는 API")
	public List<User> getUserList() {
		
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

}
