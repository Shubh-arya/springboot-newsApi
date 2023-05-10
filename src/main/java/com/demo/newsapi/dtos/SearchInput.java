package com.demo.newsapi.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Valid
public class SearchInput {
    @NotNull
    private String query;
    @NotNull
    private String sortBy;
    @NotNull
    private LocalDate fromDate;
}
