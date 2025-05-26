package B2.Demo.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import B2.Demo.model.NewsResponse;

@Service
public class NewsService {

    private final String API_KEY = "APIKEY";
    // reemplaza con tu API key
    private final String URL = "https://newsapi.org/v2/everything?q=tecnología&from=2025-05-21&sortBy=publishedAt&language=es&apiKey="
            + API_KEY;

    public String getNewsJson() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        return response.getBody(); // por ahora solo devuelve el JSON en crudo
    }

    public NewsResponse buscarPorTema(String tema) {
        String encoded = URLEncoder.encode(tema, StandardCharsets.UTF_8);
        String urlFinal = "https://newsapi.org/v2/everything?q=" + encoded +
                "&from=2025-05-20&sortBy=publishedAt&language=es&apiKey=" + API_KEY;
        try {
            RestTemplate restTemplate = new RestTemplate();
            String json = restTemplate.getForObject(urlFinal, String.class);

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            return mapper.readValue(json, NewsResponse.class);
        } catch (Exception e) {
            System.out.println("Error en búsqueda por tema: " + e.getMessage());
            return null;
        }
    }

}
