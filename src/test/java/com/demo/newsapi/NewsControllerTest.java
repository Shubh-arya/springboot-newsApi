package com.demo.newsapi;

import com.demo.newsapi.controllers.NewsController;
import com.demo.newsapi.dtos.NewsArticle;
import com.demo.newsapi.dtos.SearchInput;
import com.demo.newsapi.dtos.Source;
import com.demo.newsapi.exceptions.ErrorResponse;
import com.demo.newsapi.services.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsController.class)
public class NewsControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    NewsService newsService;

    @Test
    void shouldRespondWithErrorWhenRequestBodyIsEmpty() throws Exception {
        ErrorResponse expected = new ErrorResponse();
        expected.setMessage("Invalid request body");
        Map<String, String> fields = new HashMap<>();
        fields.put("query", "must not be null");
        fields.put("fromDate", "must not be null");
        fields.put("sortBy", "must not be null");
        expected.setFields(fields);

        MvcResult mvcResult = mvc.perform(post("/api/news/search").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest()).andReturn();
        assertEquals("test", new ObjectMapper().writeValueAsString(expected), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldRespondWithErrorWhenQueryFieldIsMissing() throws Exception {
        ErrorResponse expected = new ErrorResponse();
        expected.setMessage("Invalid request body");
        Map<String, String> fields = new HashMap<>();
        fields.put("query", "must not be null");
        expected.setFields(fields);

        MvcResult mvcResult = mvc.perform(post("/api/news/search").contentType(MediaType.APPLICATION_JSON).content("{\"fromDate\":\"2023-04-10\",\"sortBy\":\"author\"}")).andExpect(status().isBadRequest()).andReturn();
        assertEquals("test", new ObjectMapper().writeValueAsString(expected), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldRespondWithErrorWhenFromDateIsMissing() throws Exception {
        ErrorResponse expected = new ErrorResponse();
        expected.setMessage("Invalid request body");
        Map<String, String> fields = new HashMap<>();
        fields.put("fromDate", "must not be null");
        expected.setFields(fields);

        MvcResult mvcResult = mvc.perform(post("/api/news/search").contentType(MediaType.APPLICATION_JSON).content("{\"query\":\"money transfer\",\"sortBy\":\"author\"}")).andExpect(status().isBadRequest()).andReturn();
        assertEquals("test", new ObjectMapper().writeValueAsString(expected), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldRespondWithErrorWhenSortByFieldIsMissing() throws Exception {
        ErrorResponse expected = new ErrorResponse();
        expected.setMessage("Invalid request body");
        Map<String, String> fields = new HashMap<>();
        fields.put("sortBy", "must not be null");
        expected.setFields(fields);

        MvcResult mvcResult = mvc.perform(post("/api/news/search").contentType(MediaType.APPLICATION_JSON).content("{\"query\":\"money transfer\",\"fromDate\":\"2023-04-10\"}")).andExpect(status().isBadRequest()).andReturn();
        assertEquals("test", new ObjectMapper().writeValueAsString(expected), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldRespondWithErrorWhenFromDateValueIsInvalid() throws Exception {
        ErrorResponse expected = new ErrorResponse();
        expected.setMessage("Invalid type");
        Map<String, String> fields = new HashMap<>();
        fields.put("fromDate", "Invalid value");
        expected.setFields(fields);

        MvcResult mvcResult = mvc.perform(post("/api/news/search").contentType(MediaType.APPLICATION_JSON).content("{\"query\":\"money transfer\",\"fromDate\":\"2023-04-10A\",\"sortBy\":\"author\"}")).andExpect(status().isBadRequest()).andReturn();
        assertEquals("test", new ObjectMapper().writeValueAsString(expected), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetNews() throws Exception {
        // Mocked response data
        Source source = new Source(null, "test");
        List<NewsArticle> mockedNewsList = new ArrayList<>();
        mockedNewsList.add(new NewsArticle(source, "Lawrence Bonk", "Money transfer firm Wise shares drop as volumes disappoint", "Money transfer firm Wise shares drop as volumes disappoint", "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiZGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL2J1c2luZXNzL21vbmV5LXRyYW5zZmVyLWZpcm0td2lzZS1zaGFyZXMtZHJvcC12b2x1bWVzLWRpc2FwcG9pbnQtMjAyMy0wNC0xOC_SAQA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1", null, "2023-04-18T08:44:00Z", "Reuters"));
        mockedNewsList.add(new NewsArticle(source, "Lawrence Bonk1", "Money transfer firm Wise shares drop as volumes disappoint", "Money transfer firm Wise shares drop as volumes disappoint", "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiZGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL2J1c2luZXNzL21vbmV5LXRyYW5zZmVyLWZpcm0td2lzZS1zaGFyZXMtZHJvcC12b2x1bWVzLWRpc2FwcG9pbnQtMjAyMy0wNC0xOC_SAQA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1", null, "2023-04-18T08:44:00Z", "Reuters"));
        mockedNewsList.add(new NewsArticle(source, "Lawrence Bonk2", "Money transfer firm Wise shares drop as volumes disappoint", "Money transfer firm Wise shares drop as volumes disappoint", "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiZGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL2J1c2luZXNzL21vbmV5LXRyYW5zZmVyLWZpcm0td2lzZS1zaGFyZXMtZHJvcC12b2x1bWVzLWRpc2FwcG9pbnQtMjAyMy0wNC0xOC_SAQA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1", null, "2023-04-18T08:44:00Z", "Reuters"));
        mockedNewsList.add(new NewsArticle(source, "Lawrence Bonk3", "Money transfer firm Wise shares drop as volumes disappoint", "Money transfer firm Wise shares drop as volumes disappoint", "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiZGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL2J1c2luZXNzL21vbmV5LXRyYW5zZmVyLWZpcm0td2lzZS1zaGFyZXMtZHJvcC12b2x1bWVzLWRpc2FwcG9pbnQtMjAyMy0wNC0xOC_SAQA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1", null, "2023-04-18T08:44:00Z", "Reuters"));
        mockedNewsList.add(new NewsArticle(source, "Lawrence Bonk4", "Money transfer firm Wise shares drop as volumes disappoint", "Money transfer firm Wise shares drop as volumes disappoint", "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiZGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL2J1c2luZXNzL21vbmV5LXRyYW5zZmVyLWZpcm0td2lzZS1zaGFyZXMtZHJvcC12b2x1bWVzLWRpc2FwcG9pbnQtMjAyMy0wNC0xOC_SAQA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1", null, "2023-04-18T08:44:00Z", "Reuters"));

        // Mock the behavior of the newsService
        when(newsService.searchArticles(new SearchInput())).thenReturn(mockedNewsList);
        mvc.perform(post("/api/news/search").contentType(MediaType.APPLICATION_JSON).content("{\"query\":\"money transfer\",\"fromDate\":\"2023-04-10\",\"sortBy\":\"author\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(mockedNewsList.size())).andExpect(jsonPath("$[0].title").value(mockedNewsList.get(0).getTitle())).andExpect(jsonPath("$[0].description").value(mockedNewsList.get(0).getDescription())).andExpect(jsonPath("$[0].author").value(mockedNewsList.get(0).getAuthor()))
                // Add assertions for other fields as needed
                .andExpect(jsonPath("$[1].title").value(mockedNewsList.get(1).getTitle())).andExpect(jsonPath("$[1].description").value(mockedNewsList.get(1).getDescription())).andExpect(jsonPath("$[1].author").value(mockedNewsList.get(1).getAuthor()));

    }
}

