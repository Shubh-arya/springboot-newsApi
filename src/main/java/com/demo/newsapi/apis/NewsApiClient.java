package com.demo.newsapi.apis;

import com.demo.newsapi.dtos.NewsApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * News Api client services.
 *
 * @Author: Shubham Arya
 * @Version: 0.1
 */
@FeignClient(name = "news-api", url = "https://newsapi.org")
public interface NewsApiClient {
    @GetMapping("/v2/everything")
    NewsApiResponse searchArticles(@RequestParam("q") String query,
                                   @RequestParam("from") LocalDate fromDate,
                                   @RequestParam("sortBy") String sortBy,
                                   @RequestParam("pageSize") int pageSize,
                                   @RequestParam("page") int page,
                                   @RequestParam("apiKey") String apiKey
    );
}