package com.movieApp.moviaApi.service;

import com.movieApp.moviaApi.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMovie(Integer id);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer movieId, MovieDto movieDto,MultipartFile file) throws IOException;
    String deleteMovie(Integer movieId) throws IOException;
}
