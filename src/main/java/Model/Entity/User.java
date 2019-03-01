package Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String first_name;
    private String last_name;
    private String job_title;
    private String profile_pic_url;
    private List<Skill> skills = new ArrayList<Skill>();
    private String bio;

    public String getId() { return id; }
    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name;}
    public String getFullName() { return first_name + " " + last_name; }
    public String getJobTitle() {return job_title;}
    public String getProfilePicUrl() { return profile_pic_url;}
    public String getBio() { return bio;}
    public List<Skill> getSkills() { return skills; }

    public User (String id, String first_name, String last_name, List<Skill> skills, String job_title,
                 String profile_pic_url, String bio) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.skills = skills;
        this.job_title  = job_title;
        this.profile_pic_url = profile_pic_url;
        this.bio = bio;
    }

    public User(String id, String first_name, String last_name, String job_title, String profile_pic_url,
                String bio, Skill... skills)
    {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        for (Skill skill : skills)
            this.skills.add(skill);
        this.job_title = job_title;
        this.profile_pic_url = profile_pic_url;
        this.bio = bio;
    }

    public long getSkillPoint(Skill jobSkill) {
        for(Skill userSkill : skills) {
            if(userSkill.getName().equals(jobSkill.getName()))
                return userSkill.getPoint();
        }
        return -1;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public boolean hasSkill(String skillName) {
        for(Skill skill : skills)
            if(skill.equals(skillName))
                return true;
        return false;
    }

    public boolean removeSkill(String skillName) {
        for(Skill skill : skills)
            if(skill.getName().equals(skillName)) {
                skills.remove(skill);
                return true;
            }
        return false;
    }

    public boolean endorseSkill(String skillName)
    {
        for (Skill skill : skills)
            if (skill.getName().equals(skillName)) {
                skill.addPoint();
                return true;
            }
        return false;
    }
}
