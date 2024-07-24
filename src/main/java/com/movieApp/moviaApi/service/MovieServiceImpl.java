package com.movieApp.moviaApi.service;

import com.movieApp.moviaApi.dto.MovieDto;
import com.movieApp.moviaApi.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }
    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) {
        return null;
    }

    @Override
    public MovieDto getMovie(Integer id) {
        return null;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return null;
    }
}
