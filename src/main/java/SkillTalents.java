import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SkillTalents {
    String name;
    ArrayList<Upgrades> upgrades;
    String type;

    public SkillTalents() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Upgrades> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(ArrayList<Upgrades> upgrades) {
        this.upgrades = upgrades;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
