package Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Skill {
    private String name;
    private int point;
    private static List<String> valid_names = new ArrayList<>();
    private List<User> endorsedBy = new ArrayList<>();

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
            throw new InvalidSkillNameException();
        this.name = name;
        this.point = 0;
    }

    public void endorse(User endorser)
    {
        for (User user : endorsedBy)
            if (user.getId().equals(endorser.getId()))
                throw new AlreadyEndorsedException();
        endorsedBy.add(endorser);
        this.point += 1;
    }

    public static void setValidNames(List<String> valid)
    {
        valid_names = valid;
    }

    public String getName() { return this.name;}

    public int getPoint() { return this.point; }

    public static List<String> getValidNames() { return valid_names; }

    public static class InvalidSkillNameException extends RuntimeException { }

    public static class AlreadyEndorsedException extends RuntimeException { }
}

