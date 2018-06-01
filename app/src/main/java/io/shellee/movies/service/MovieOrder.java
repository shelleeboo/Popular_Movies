package io.shellee.movies.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MovieOrder {
    popular("popular"),
    top_rated("top_rated");

    @Getter
    private String value;
}
