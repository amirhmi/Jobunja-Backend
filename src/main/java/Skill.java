import java.util.ArrayList;
import java.util.List;

public class Skill {
    private String name;
    private int point;
    private static List<String> valid_names = new ArrayList<>();

    public Skill(String html, int i) {
        this.name = "no-skill";
        this.point = 0;
    }

    public Skill(String name) throws InvalidSkillNameException {
        if (!valid_names.contains(name))
            throw new InvalidSkillNameException("skill is not valid");
        this.name = name;
        this.point = point;
    }

    public static void setValidNames(List<String> valid)
    {
        System.out.println("setting valid names");
        valid_names = valid;
        for (String name : valid_names)
            System.out.println(name);
    }

    public String getName() { return this.name;}

    public int getPoint() { return this.point; }

    static class InvalidSkillNameException extends Exception
    {
        public InvalidSkillNameException(String msg) { super(msg); }
    }
}

