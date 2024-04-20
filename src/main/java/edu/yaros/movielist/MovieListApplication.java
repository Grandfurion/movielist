package edu.yaros.movielist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan({"edu.yaros.movielist"})
@EntityScan("edu.yaros.movielist")
@RestController
public class MovieListApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieListApplication.class, args);
	}


}
