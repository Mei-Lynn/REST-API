
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Character {
    String name;
    String vision;
    int rarity;
    ArrayList<SkillTalents> skillTalents;
}
