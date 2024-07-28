package com.movieApp.moviaApi.service;

import com.movieApp.moviaApi.dto.MovieDto;
import com.movieApp.moviaApi.entities.Movie;
import com.movieApp.moviaApi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final FileService fileService;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Value("${project.poster}")
    private String path;
    @Value("${base.url}")
    private String baseurl;

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // Upload the file
        String uploadedFileName = fileService.uploadFile(path, file);
        // Set the value of field poster as fileName
        movieDto.setPoster(uploadedFileName);
        // Map dto to Movie Object
        Movie movie = new Movie(
                movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        // Save the movie object
        Movie savedMovie = movieRepository.save(movie);

        // Generate the poster URL
        String posterUrl = baseurl + "/file/" + uploadedFileName;

        // Map movie object to dto and return it
        MovieDto movieDto1 = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );
        return movieDto1;
    }

    @Override
    public MovieDto getMovie(Integer id) {
        //check the data is in db and if exists fetch it
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        //generate posterUrl
        String posterUrl = baseurl + "/file/" + movie.getPoster();
        //map to movieDto object to return it

        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
    }

    @Override
    public List<MovieDto> getAllMovies() {
        //fetch all data from db
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<>();
        //iterate through the list and generate posterUrl for each movie obj
        for(Movie movie:movies){
            String posterUrl = baseurl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);

        }

        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // check if movie object exists with given movieId
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        String fileName = movie.getPoster();
        if(file != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path,file);
        }
        movieDto.setPoster(fileName);

        Movie movie1 = new Movie(
                movie.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        Movie updatedMovie = movieRepository.save(movie1);
        String posterUrl = baseurl + "/file/" + fileName;
        MovieDto movieDto1 = new MovieDto(
                movie1.getMovieId(),
                movie1.getTitle(),
                movie1.getDirector(),
                movie1.getStudio(),
                movie1.getMovieCast(),
                movie1.getReleaseYear(),
                movie1.getPoster(),
                posterUrl
        );
        return movieDto1;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        Integer id = movie.getMovieId();
        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));
        movieRepository.delete(movie);
        return "Movie deleted with id :" + id;
    }
}
