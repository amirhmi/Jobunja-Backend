package Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Skill {
    private String name;
    private int point;
    private static List<String> valid_names = new ArrayList<>();

    public Skill() {
        this.name = "no-skill";
        this.point = 0;
    }

    public Skill(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public Skill(String name) throws InvalidSkillNameException {
        if (!valid_names.contains(name))
            throw new InvalidSkillNameException("skill is not valid");
        this.name = name;
        this.point = 0;
    }

    public static void setValidNames(List<String> valid)
    {
        valid_names = valid;
    }

    public String getName() { return this.name;}

    public int getPoint() { return this.point; }

    static class InvalidSkillNameException extends Exception
    {
        public InvalidSkillNameException(String msg) { super(msg); }
    }
}
