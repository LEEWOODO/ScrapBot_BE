package com.scrapbot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scrapbot.entity.NewsArticle;
import com.scrapbot.service.NewsArticleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@CrossOrigin(origins="http://localhost:3000")
@RestController
@Api(value = "NewsArticleController", description = "Article API")
@RequestMapping(value ="/api", method = RequestMethod.GET)
public class NewsArticleController {

	@Autowired
	private final NewsArticleService newsArticleService = null;
	
    @GetMapping("/articles")
    @ApiOperation(httpMethod = "GET"
    			,value = "articles 리스트전체 조회" 
    			,notes="articles 리스트 반환하는 API")
    public List<NewsArticle> getArticle() {
    	return newsArticleService.selectNewsArticleList();
    }
	
	@GetMapping("/articles/getById/{id}")
    @ApiOperation(httpMethod = "GET"
				,value = "articles 단일기사 id로 조회" 
				,notes="articles 하나의 기사 조회하는 api")
	public Map<String, Object> findById(@PathVariable("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Optional<NewsArticle> oUser = newsArticleService.selectNewsArticle(id);
		if(oUser.isPresent()) {
			response.put("result", "SUCCESS");
			response.put("user", oUser.get());
		} else {
			response.put("result", "FAIL");
			response.put("reason", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
		}

		return response;
	}
	
	@GetMapping("/articles/getByName/{newcompany}")
    @ApiOperation(httpMethod = "GET"
				,value = "articles 신문사별 리스트 조회" 
				,notes="articles 신문사별 리스트 조회 api")
	public List<NewsArticle> findOne(@PathVariable("newcompany") String newcompany){
		// string like 는 containing 을 이용하는것이 잘 되는듯. 개인적인 우도 생각
		return newsArticleService.findByNewcompanyContaining(newcompany);
	}

	@GetMapping("/articles/date/{regdate}")
    @ApiOperation(httpMethod = "GET"
				,value = "articles 날짜별 리스트 조회" 
				,notes="articles 날짜별 리스트 조회 api")
	public List<NewsArticle> findByRegdate(@PathVariable("regdate") String regdate){
		// string like 는 containing 을 이용하는것이 잘 되는듯. 개인적인 우도 생각
		return newsArticleService.findByRegdateIs(regdate);
	}
	
}