import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Personaje {

	private String name;
	private String vision;
	private int rarity;
	private String nation;
	private String description;
	private String weapon;
	private List<Talento> skillTalents;
	private String id;

	private Map<String, List<AscensionItem>> ascension_materials;

	public Personaje() {
	}

	public String getName() {
		return name;
	}

	public String getVision() {
		return vision;
	}

	public int getRarity() {
		return rarity;
	}

	public String getNation() {
		return nation;
	}

	public String getDescription() {
		return description;
	}

	public String getWeapon() {
		return weapon;
	}

	public List<Talento> getSkillTalents() {
		return skillTalents;
	}

	public Map<String, List<AscensionItem>> getAscension_materials() {
		return ascension_materials;
	}

	public void setAscension_materials(Map<String, List<AscensionItem>> ascension_materials) {
		this.ascension_materials = ascension_materials;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
