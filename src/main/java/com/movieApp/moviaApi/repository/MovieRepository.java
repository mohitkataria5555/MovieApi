package com.movieApp.moviaApi.repository;

import com.movieApp.moviaApi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {


}
