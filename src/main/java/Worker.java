import java.util.List;

public class Worker extends User {
    private List<Skill> skills;

    public Worker(String username, List<Skill> skills) {
        super(username);
        this.skills = skills;
    }

    public Worker(String username, Skill... skills)
    {
        super(username);
        for (Skill skill : skills)
            this.skills.add(skill);
    }

    public long getSkillPoint(Skill jobSkill) {
        for(Skill userSkill : skills) {
            if(userSkill.getName().equals(jobSkill.getName()))
                return userSkill.getPoints();
        }
        return -1;
    }
}
