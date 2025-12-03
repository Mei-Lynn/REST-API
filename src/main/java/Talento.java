import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Talento {

	private String name;
	private String description;

	public Talento() {
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
