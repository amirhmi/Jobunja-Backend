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

    public String getFirstName() { return first_name; }
    public String getId() { return id; }
    public String getLastName() { return last_name;}
    public String getJobTitle() {return job_title;}
    public String getProfilePicUrl() { return profile_pic_url;}
    public String getBio() { return bio;}

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
}
