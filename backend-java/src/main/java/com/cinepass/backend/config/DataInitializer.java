package com.cinepass.backend.config;

import com.cinepass.backend.entity.Movie;
import com.cinepass.backend.entity.Showtime;
import com.cinepass.backend.repository.MovieRepository;
import com.cinepass.backend.repository.ShowtimeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ShowtimeRepository showtimeRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        // Clear old movie data to refresh with local posters if they start with http
        // or if there are fewer than 10 movies (to ensure completeness)
        long count = movieRepository.count();
        if (count > 0) {
            boolean hasHttpPosters = movieRepository.findAll().get(0).getPoster().startsWith("http");
            if (hasHttpPosters || count < 10) {
                showtimeRepository.deleteAll();
                movieRepository.deleteAll();
                count = 0;
            }
        }

        if (count >= 10) {
            return; // Already seeded with enough movies
        }

        // Seed Movies
        List<Movie> moviesToSeed = new ArrayList<>();

        moviesToSeed.add(createMovie("Dune: Part Two", "/posters/dune2.jpg", 
            "https://image.tmdb.org/t/p/original/xOMo8BRK7PfcJv9JCnx7s5hj0PX.jpg", "PG-13", "2h 46m", 
            Arrays.asList("Sci-Fi", "Adventure"), "2024-03-01", 8.8, 
            "Paul Atreides unites with Chani and the Fremen while on a warpath of revenge...",
            Arrays.asList(createCast("Timothée Chalamet", "Paul Atreides"), createCast("Zendaya", "Chani")),
            "https://www.youtube.com/embed/Way9Dexny3w"));

        moviesToSeed.add(createMovie("Oppenheimer", "/posters/oppenheimer.jpg",
            "https://image.tmdb.org/t/p/original/fm6KqXpk3M2HVveHwvkHIeHYDI6.jpg", "R", "3h 00m",
            Arrays.asList("Biography", "Drama"), "2023-07-21", 8.4,
            "The story of J. Robert Oppenheimer and his role in the development of the atomic bomb.",
            Arrays.asList(createCast("Cillian Murphy", "J. Robert Oppenheimer")),
            "https://www.youtube.com/embed/uYPbbksJxIg"));

        moviesToSeed.add(createMovie("Spider-Man: Across the Spider-Verse", "/posters/spiderman.jpg",
            "https://image.tmdb.org/t/p/original/oXpeooxWz9Y5VSt2E3p06B8qA7p.jpg", "PG", "2h 20m",
            Arrays.asList("Animation", "Action", "Adventure"), "2023-06-02", 8.6,
            "Miles Morales catapults across the Multiverse, where he encounters a team of Spider-People charged with protecting its very existence.",
            Arrays.asList(createCast("Shameik Moore", "Miles Morales"), createCast("Hailee Steinfeld", "Gwen Stacy")),
            "https://www.youtube.com/embed/shW9i6k8cB0"));

        moviesToSeed.add(createMovie("Barbie", "/posters/barbie.jpg",
            "https://image.tmdb.org/t/p/original/ctM8G2O4oF01rNZq98NeoWunNbs.jpg", "PG-13", "1h 54m",
            Arrays.asList("Comedy", "Fantasy"), "2023-07-21", 6.9,
            "Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land.",
            Arrays.asList(createCast("Margot Robbie", "Barbie"), createCast("Ryan Gosling", "Ken")),
            "https://www.youtube.com/embed/pBk4NYhWNMM"));

        moviesToSeed.add(createMovie("John Wick: Chapter 4", "/posters/johnwick4.jpg",
            "https://image.tmdb.org/t/p/original/7I6VUdPj6oQwc60u6K6bpZ6qb9A.jpg", "R", "2h 49m",
            Arrays.asList("Action", "Thriller"), "2023-03-24", 7.7,
            "John Wick uncovers a path to defeating The High Table.",
            Arrays.asList(createCast("Keanu Reeves", "John Wick")),
            "https://www.youtube.com/embed/qEVUrkHuie8"));

        moviesToSeed.add(createMovie("The Super Mario Bros. Movie", "/posters/mario.jpg",
            "https://image.tmdb.org/t/p/original/9n2t0Lp7R619RDESTu3p31Qp1uY.jpg", "PG", "1h 32m",
            Arrays.asList("Animation", "Adventure", "Comedy"), "2023-04-05", 7.0,
            "A plumber named Mario travels through an underground labyrinth with his brother, Luigi, trying to save a captured princess.",
            Arrays.asList(createCast("Chris Pratt", "Mario"), createCast("Charlie Day", "Luigi")),
            "https://www.youtube.com/embed/TnGl01FkMMo"));

        moviesToSeed.add(createMovie("Guardians of the Galaxy Vol. 3", "/posters/guardians3.jpg",
            "https://image.tmdb.org/t/p/original/5YZbUmjbMa3ClvS7Z9ETv3Zp9Q.jpg", "PG-13", "2h 30m",
            Arrays.asList("Action", "Adventure", "Sci-Fi"), "2023-05-05", 7.9,
            "Still reeling from the loss of Gamora, Peter Quill rallies his team around him to defend the universe and one of their own.",
            Arrays.asList(createCast("Chris Pratt", "Peter Quill"), createCast("Zoe Saldana", "Gamora")),
            "https://www.youtube.com/embed/u3V5KDHRQvk"));

        moviesToSeed.add(createMovie("Avatar: The Way of Water", "/posters/avatar2.jpg",
            "https://image.tmdb.org/t/p/original/8rpDptTjX9aoHbhUlsccp0s7ZpI.jpg", "PG-13", "3h 12m",
            Arrays.asList("Action", "Adventure", "Sci-Fi"), "2022-12-16", 7.6,
            "Jake Sully lives with his newfound family formed on the extrasolar moon Pandora.",
            Arrays.asList(createCast("Sam Worthington", "Jake Sully")),
            "https://www.youtube.com/embed/d9MyW72ELq0"));

        moviesToSeed.add(createMovie("Top Gun: Maverick", "/posters/topgun.jpg",
            "https://image.tmdb.org/t/p/original/AaV1Y2UIZha2S6MWICZcyQ1oYdb.jpg", "PG-13", "2h 10m",
            Arrays.asList("Action", "Drama"), "2022-05-27", 8.3,
            "After thirty years, Maverick is still pushing the envelope as a top naval aviator.",
            Arrays.asList(createCast("Tom Cruise", "Maverick")),
            "https://www.youtube.com/embed/giXcoGa8nzo"));

        moviesToSeed.add(createMovie("The Batman", "/posters/batman.jpg",
            "https://image.tmdb.org/t/p/original/t56870tSrqhDs8S5u9pZ6Pk8O7u.jpg", "PG-13", "2h 56m",
            Arrays.asList("Action", "Crime", "Drama"), "2022-03-04", 7.8,
            "When a sadistic serial killer begins murdering key political figures in Gotham, Batman is forced to investigate the city's hidden corruption.",
            Arrays.asList(createCast("Robert Pattinson", "Bruce Wayne")),
            "https://www.youtube.com/embed/mqqft2x_Aa4"));

        for (Movie movie : moviesToSeed) {
            if (!movieRepository.existsByTitle(movie.getTitle())) {
                Movie savedMovie = movieRepository.save(movie);
                createShowtime(savedMovie, "10:30 AM", "Hall A", 15.00);
                createShowtime(savedMovie, "02:00 PM", "Hall B", 12.00);
                createShowtime(savedMovie, "07:30 PM", "IMAX", 22.00);
            }
        }
    }


    private Movie createMovie(String title, String poster, String backdrop, String rating, String duration, 
                              List<String> genres, String releaseDate, Double imdbRating, String description, 
                              List<Map<String, String>> cast, String trailer) throws Exception {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setPoster(poster);
        movie.setBackdrop(backdrop);
        movie.setRating(rating);
        movie.setDuration(duration);
        movie.setGenre(objectMapper.writeValueAsString(genres));
        movie.setReleaseDate(releaseDate);
        movie.setImdbRating(imdbRating);
        movie.setDescription(description);
        movie.setCast(objectMapper.writeValueAsString(cast));
        movie.setTrailer(trailer);
        return movie;
    }

    private Map<String, String> createCast(String name, String role) {
        Map<String, String> castMember = new HashMap<>();
        castMember.put("name", name);
        castMember.put("role", role);
        return castMember;
    }

    private void createShowtime(Movie movie, String time, String hall, Double price) {
        Showtime st = new Showtime();
        st.setMovie(movie);
        st.setTime(time);
        st.setHall(hall);
        st.setPrice(price);
        showtimeRepository.save(st);
    }
}
