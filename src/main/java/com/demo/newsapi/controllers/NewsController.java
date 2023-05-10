package com.demo.newsapi.controllers;

import com.demo.newsapi.dtos.NewsArticle;
import com.demo.newsapi.dtos.SearchInput;
import com.demo.newsapi.services.NewsService;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Api to handle the news articles.
 *
 * @Author: Shubham Arya
 * @Version: 0.1
 */
@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @PostMapping(path = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NewsArticle>> searchNewsArticles(@Valid @RequestBody SearchInput searchInput) {
        List<NewsArticle> NewsArticles = newsService.searchArticles(searchInput);
        return new ResponseEntity<>(NewsArticles, HttpStatus.OK);
    }
}
