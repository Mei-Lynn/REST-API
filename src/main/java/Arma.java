import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Arma {

	private String name;
	private String type;
	private int rarity;
	private String subStat;
	private String passiveName;
	private String passiveDesc;

	public Arma() {
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getRarity() {
		return rarity;
	}

	public String getSubStat() {
		return subStat;
	}

	public String getPassiveName() {
		return passiveName;
	}

	public String getPassiveDesc() {
		return passiveDesc;
	}

	@Override
	public String toString() {
		return name + " (" + rarity + "★)";
	}
}
