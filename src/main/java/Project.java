import java.util.List;

public class Project {
    String title;
    List<Skill> skills;
    public Project(String title, List<Skill> skills)
    {
        this.title = title;
        this.skills = skills;
    }
    public Project(String title, Skill... skills)
    {
        this.title = title;
        for (Skill skill : skills)
            this.skills.add(skill);
    }
}
