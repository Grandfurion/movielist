package edu.yaros.movielist.controllers;

import com.google.common.collect.Lists;
import edu.yaros.movielist.models.Movie;
import edu.yaros.movielist.repositories.MovieRepository;
import edu.yaros.movielist.services.ExcelService;
import edu.yaros.movielist.services.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class DataController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ExcelService excelService;

    public void generalLayout(Model model){
        List<Movie> movieList = selectItems();

        model.addAttribute("movieList", movieList);
    }
    public List<Movie> selectItems() {
        return Lists.newArrayList(movieRepository.findAll());
    }

    @GetMapping("/")
    public String indexPage(Model model){
        generalLayout(model);
        return "index";
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public String addNewItem (@RequestParam("url") String url, Model model) {
        Parser parser = new Parser();
        Movie n = parser.parseSearch(url);
        movieRepository.save(n);

        generalLayout(model);

        return "index";
    }

    @PostMapping(path="/delete")
    public String deleteItem (@RequestParam("idToDelete") String idToDelete, Model model) {

        movieRepository.deleteById(Integer.parseInt(idToDelete));

        System.out.println(idToDelete);
        generalLayout(model);
        return "index";
    }

    @PostMapping("/excel")
    public ResponseEntity<byte[]> excelify(Model model){

        try {
            List<Movie> movieList = selectItems();
            byte[] excel = excelService.createExcelFile(movieList);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "movieList.xlsx");
            return ResponseEntity.ok().headers(headers).body(excel);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/test")
    public String test(Model model){

        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.metacritic.com/movie/abigail/").get();
            System.out.println(doc);

        } catch (IOException e) {
            e.printStackTrace();
        }

        generalLayout(model);
        return "index";
    }
}
