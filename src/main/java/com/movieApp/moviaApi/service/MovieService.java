package com.movieApp.moviaApi.service;

import com.movieApp.moviaApi.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file);
    MovieDto getMovie(Integer id);
    List<MovieDto> getAllMovies();
}
