import java.util.List;

public class User {
    private String username;
    private List<Skill> skills;
    private String job_title;
    private String profile_pic_url;
    private String bio;

    public String getUsername() { return username; }

    public User (String username, List<Skill> skills) {
        this.username = username;
        this.skills = skills;
    }

    public User(String username, Skill... skills)
    {
        this.username = username;
        for (Skill skill : skills)
            this.skills.add(skill);
    }

    public long getSkillPoint(Skill jobSkill) {
        for(Skill userSkill : skills) {
            if(userSkill.getName().equals(jobSkill.getName()))
                return userSkill.getPoint();
        }
        return -1;
    }
}
