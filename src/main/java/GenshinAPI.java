
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenshinAPI {

    private HttpClient client;
    private ObjectMapper mapper;
    private HashMap<URI, Personaje> pjCache;
    private HashMap<URI, Arma> wpCache;
    private ArrayList<String> listCache;

    public GenshinAPI() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
        pjCache = new HashMap<>();
		wpCache = new HashMap<>();
        listCache = new ArrayList<>();
    }

    // ===== 1. Obtener personaje =====
    public Personaje getPersonaje(String nombre) throws Exception {
        URI URLpj = URI.create("https://genshin.jmp.blue/characters/" + nombre);

        if (pjCache.containsKey(URLpj)) {
            return pjCache.get(URLpj);
        } else {
            HttpRequest request = HttpRequest.newBuilder().uri(URLpj).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Personaje no encontrado.");
            }
            Personaje pjEncontrado = mapper.readValue(response.body(), Personaje.class);
            pjCache.put(URLpj, pjEncontrado);

            return pjEncontrado;
        }
    }

    // ===== 2. Obtener arma =====
    public Arma getArma(String id) throws Exception {
        URI URLwp = URI.create("https://genshin.jmp.blue/weapons/" + id);

        if (wpCache.containsKey(URLwp)) {
            return wpCache.get(URLwp);
        } else {
            HttpRequest request = HttpRequest.newBuilder().uri(URLwp).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Arma no encontrada: " + id);
            }
            Arma wpEncontrada = mapper.readValue(response.body(), Arma.class);
            wpCache.put(URLwp, wpEncontrada);

            return wpEncontrada;
        }

    }

    // ===== 3. Lista de armas (solo ids) =====
    public List<String> getListaArmas() throws Exception {

        if (listCache.isEmpty()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://genshin.jmp.blue/weapons")).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            List<String> foundList = mapper.readValue(response.body(), new TypeReference<List<String>>() {});
            listCache.addAll(foundList);

            return foundList;
        } else {
			return listCache;
        }

        
    }
}
