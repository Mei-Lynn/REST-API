import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class GenshinAPI {

	private HttpClient client;
	private ObjectMapper mapper;

	public GenshinAPI() {
		client = HttpClient.newHttpClient();
		mapper = new ObjectMapper();
	}

	// ===== 1. Obtener personaje =====
	public Personaje getPersonaje(String nombre) throws Exception {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://genshin.jmp.blue/characters/" + nombre))
				.GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Personaje no encontrado.");
		}

		return mapper.readValue(response.body(), Personaje.class);
	}

	// ===== 2. Obtener arma =====
	public Arma getArma(String id) throws Exception {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://genshin.jmp.blue/weapons/" + id)).GET()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Arma no encontrada: " + id);
		}

		return mapper.readValue(response.body(), Arma.class);
	}

	// ===== 3. Lista de armas (solo ids) =====
	public List<String> getListaArmas() throws Exception {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://genshin.jmp.blue/weapons")).GET()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return mapper.readValue(response.body(), new TypeReference<List<String>>() {
		});
	}
}
