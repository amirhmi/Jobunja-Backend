import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private List<Skill> skills;
    private List<Bid> bids = new ArrayList<>();
    private int budget;
    private long deadline;
    private User winner;

    public Project() { }

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

    public String getTitle()
    {
        return title;
    }

    public String getId() { return id; }

    public String getDescription() { return description; }

    public String getImageUrl() { return imageUrl; }

    public int getBudget() { return budget; }

    public long getDeadline() { return deadline; }

    public List<Skill> getSkills() { return skills; }

    public void addBid(Bid bid)
    {
        if (!bidSuited(bid))
            return;
        this.bids.add(bid);
    }

    private boolean bidSuited(Bid bid)
    {
        if (bid.getBudget() > this.budget)
            return false;
        for (Skill skill : this.skills) {
            long skill_points = bid.getUser().getSkillPoint(skill);
            if (skill_points < 0)
                return false;
            if (skill_points < skill.getPoint())
                return false;
        }
        return true;
    }

    public Bid evaluate()
    {
        Bid ret = null;
        long ret_score = 0;
        for (Bid this_bid : bids) {
            long this_score = getBidScore(this_bid);
            if (ret == null || ret_score < this_score) {
                ret = this_bid;
                ret_score = this_score;
            }
        }
        return ret;
    }

    private long getBidScore(Bid bid)
    {
        long ret = this.budget - bid.getBudget();
        for (Skill skill : this.skills)
        {
            long skill_diff = skill.getPoint() - bid.getUser().getSkillPoint(skill);
            ret += 10000 * skill_diff * skill_diff;
        }
        return ret;
    }
}
