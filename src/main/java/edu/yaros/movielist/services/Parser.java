package edu.yaros.movielist.services;

import edu.yaros.movielist.models.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Parser {

    public Movie parseSearch(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

            String title = doc.select(".c-productHero_title > h1").first().ownText();
            String director = doc.select(".c-crewList_link").first().ownText();
            String releaseYear = doc.select(".c-heroMetadata_item > span").first().ownText();

            Movie movie = new Movie();
            movie.setUrl(url);
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setReleaseYear(Integer.parseInt(releaseYear));

            return movie;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
