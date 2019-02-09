import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project {
    private String title;
    private List<Skill> skills;
    private int budget;
    private List<Bid> candidates = new ArrayList<>();

    public String getTitle()
    {
        return title;
    }

    public Project(String title, List<Skill> skills, int budget)
    {
        this.title = title;
        this.skills = skills;
        this.budget = budget;
    }

    public Project(String title, int budget, Skill... skills)
    {
        this.title = title;
        this.budget = budget;
        Collections.addAll(this.skills, skills);
    }

    public void addBid(Bid bid)
    {
        if (!bidSuited(bid))
            return;
        this.candidates.add(bid);
    }

    private boolean bidSuited(Bid bid)
    {
        if (bid.getBudget() > this.budget)
            return false;
        for (Skill skill : this.skills) {
            int skill_points = bid.getWorker().getSkillPoint(skill);
            if (skill_points < 0)
                return false;
            if (skill_points < skill.getPoints())
                return false;
        }
        return true;
    }

    public Bid evaluate()
    {
        Bid ret = null;
        int ret_score = 0;
        for (Bid this_bid : candidates) {
            int this_score = getBidScore(this_bid);
            if (ret == null || ret_score < this_score) {
                ret = this_bid;
                ret_score = this_score;
            }
        }
        return ret;
    }

    private int getBidScore(Bid bid)
    {
        int ret = bid.getBudget() - this.budget;
        for (Skill skill : this.skills)
        {
            int skill_diff = skill.getPoints() - bid.getWorker().getSkillPoint(skill);
            ret += 10000 * skill_diff * skill_diff;
        }
        return ret;
    }
}
