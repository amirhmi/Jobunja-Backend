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

    public long getSkillPoint(Skill jobSkill) throws SkillNotFoundException{
        for(Skill userSkill : skills) {
            if(userSkill.getName().equals(jobSkill.getName()))
                return userSkill.getPoint();
        }
        throw new SkillNotFoundException();
    }

    public void addSkill(Skill skill) throws SkillAlreadyExistsException{
        if (hasSkill(skill.getName()))
            throw new SkillAlreadyExistsException();
        skills.add(skill);
    }

    public boolean hasSkill(String skillName) {
        for(Skill skill : skills) {
            if (skill.getName().equals(skillName)) {
                return true;
            }
        }
        return false;
    }

    public void removeSkill(String skillName) throws SkillNotFoundException{
        for(Skill skill : skills)
            if(skill.getName().equals(skillName)) {
                skills.remove(skill);
                return;
            }
        throw new SkillNotFoundException();
    }

    public Skill endorseSkill(String skillName, User user) throws SkillNotFoundException, Skill.AlreadyEndorsedException
    {
        for (Skill skill : skills)
            if (skill.getName().equals(skillName)) {
                skill.endorse(user);
                return skill;
        }
        throw new SkillNotFoundException();
    }

    public List<Boolean> getIsEndorser(String userId)
    {
        List<Boolean> ret = new ArrayList<>();
        for (Skill skill : skills)
            ret.add(skill.isEndorsed(userId));
        return ret;
    }

    public static class SkillNotFoundException extends RuntimeException {}

    public static class SkillAlreadyExistsException extends RuntimeException {}

    public static class UserJson
    {
        public String id;
        public String first_name;
        public String last_name;
        public String job_title;
        public String profile_pic_url;
        public List<SkillJson> skills = new ArrayList<SkillJson>();
        public String bio;
        public static class SkillJson
        {
            public String skillname;
            public int point;
        }
    }

    public UserJson toUserJson()
    {
        UserJson ret = new UserJson();
        ret.id = this.id;
        ret.first_name = this.first_name;
        ret.last_name = this.last_name;
        ret.job_title = this.job_title;
        ret.profile_pic_url = this.profile_pic_url;
        for (Skill skill : skills)
        {
            UserJson.SkillJson skillJson = new UserJson.SkillJson();
            skillJson.skillname = skill.getName();
            skillJson.point = skill.getPoint();
            ret.skills.add(skillJson);
        }
        ret.bio = this.bio;
        return ret;
    }
}
