package com.demo.newsapi.services;

import com.demo.newsapi.apis.NewsApiClient;
import com.demo.newsapi.dtos.NewsApiResponse;
import com.demo.newsapi.dtos.NewsArticle;
import com.demo.newsapi.dtos.SearchInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service calls the external news service and return the result.
 *
 * @Author: Shubham Arya
 * @Version: 0.1
 */
@Service
public class NewsService {

    @Autowired
    private NewsApiClient newsApiClient;

    @Value("${api-key}")
    String apiKey;

    public List<NewsArticle> searchArticles(SearchInput input) {
        NewsApiResponse response = newsApiClient.searchArticles(input.getQuery(), input.getFromDate(), input.getSortBy(), 5, 1, apiKey);
        return response.getArticles();
    }
}
