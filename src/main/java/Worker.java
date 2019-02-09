import java.util.List;

public class Worker extends User {
    public Worker(String username, List<Skill> skills) {
        super(username);
        this.skills = skills;
    }

    List<Skill> skills;
}
