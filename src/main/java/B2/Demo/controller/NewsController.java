package B2.Demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import B2.Demo.model.NewsResponse;
import B2.Demo.service.NewsService;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String index(Model model) {
        String temaPorDefecto = "tecnologia";
        NewsResponse noticias = newsService.buscarPorTema(temaPorDefecto);

        if (noticias != null && noticias.getArticles() != null && !noticias.getArticles().isEmpty()) {
            model.addAttribute("noticias", noticias.getArticles());
            model.addAttribute("tema", temaPorDefecto);
        } else {
            model.addAttribute("noticias", List.of());
            model.addAttribute("tema", temaPorDefecto);
        }

        return "index";
    }

    @GetMapping("/buscar")
    public String buscarNoticias(@RequestParam("q") String query, Model model) {
        NewsResponse noticias = newsService.buscarPorTema(query);

        if (noticias == null || noticias.getArticles() == null || noticias.getArticles().isEmpty()) {
            model.addAttribute("noticias", List.of());
            model.addAttribute("tema", query);
            return "index";
        }

        model.addAttribute("noticias", noticias.getArticles());
        model.addAttribute("tema", query);
        return "index";
    }

}
