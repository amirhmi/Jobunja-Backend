package Model.Entity;

import DataAccess.SkillDataMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Skill {
    private String name;
    private int point;
    private List<Integer> endorsersId = new ArrayList<>();

    public Skill() {
        this.name = "no-skill";
        this.point = 0;
    }

    public Skill(String name, int point) throws InvalidSkillNameException, SQLException {
        if (!SkillDataMapper.find(name))
            throw new InvalidSkillNameException();
        this.name = name;
        this.point = point;
    }

    public Skill(String name) throws InvalidSkillNameException, SQLException {
        if (!SkillDataMapper.find(name))
            throw new InvalidSkillNameException();
        this.name = name;
        this.point = 0;
    }

    public void setName(String name) { this.name = name;}
    public void setPoint(int point) { this.point = point;}
    public void setEndorsedBy(List<Integer> endorsersId) { this.endorsersId = endorsersId;}

    public Boolean isEndorsed(int endorserId)
    {
        for (int userId: endorsersId)
            if (userId == endorserId)
                return true;
        return false;
    }

    public void endorse(User endorser) throws AlreadyEndorsedException
    {
        for (int userId : endorsersId)
            if (userId == endorser.getId())
                throw new AlreadyEndorsedException();
        endorsersId.add(endorser.getId());
        this.point += 1;
    }

    public String getName() { return this.name;}
    public int getPoint() { return this.point;}
    public List<Integer> getEndorsersId() { return this.endorsersId;}

    public static class InvalidSkillNameException extends RuntimeException { }

    public static class AlreadyEndorsedException extends RuntimeException { }

    public class SkillJson
    {
        public String name;
        public int point;
        public List<String> endorsers = new ArrayList<>();
    }

    public SkillJson toSkillJson ()
    {
        SkillJson ret = new SkillJson();
        ret.name = this.name;
        ret.point = this.point;
        for (int userId : endorsersId)
            ret.endorsers.add(Integer.toString(userId));
        return ret;
    }
}
