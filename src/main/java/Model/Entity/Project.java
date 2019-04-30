package Model.Entity;

//import Model.Service.MiddlewareService;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import DataAccess.EndorsementDataMapper;
import DataAccess.UserSkillDataMapper;
import Model.Service.MiddlewareService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private List<Skill> skills = new ArrayList<>();
    private List<Bid> bids = new ArrayList<>();
    private int budget;
    private long deadline;
    private long creationDate;
    private User owner;
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

    public String getId() { return id; }
    public String getTitle() { return title;}
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public int getBudget() { return budget; }
    public long getDeadline() { return deadline; }
    public long getCreationDate() { return creationDate; }
    public List<Skill> getSkills() { return skills; }
    public List<Bid> getBids() {return bids;}
    public User getOwner() { return owner;}
    public User getWinner() { return winner;}

    public void setId(String id) { this.id = id;}
    public void setTitle(String title) { this.title = title;}
    public void setDescription(String description) { this.description = description;}
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl;}
    public void setBudget(int budget) { this.budget = budget;}
    public void setDeadline(long deadline) { this.deadline = deadline;}
    public void setCreationDate(long creationDate) { this.creationDate = creationDate;}
    public void setSkills(List<Skill> skills) { this.skills = skills;}
    public void setBids(List<Bid> bids) { this.bids = bids;}
    public void setOwner(User user) { this.owner = user;}
    public void setWinner(User user) { this.winner = user;}

    /*public boolean bidSuited(Bid bid)
    {
        if (bid.getBudget() > this.budget)
            return false;
        return userSuited(bid.getUser());
    }*/

    /*private boolean userSuited(User user)
    {
        for (Skill skill : this.skills) {
            if (!UserSkillDataMapper.exists(skill.getName(), user.getId()))
                return false;
            long skill_points = user.getSkillPoint(skill);
            if (skill_points < skill.getPoint())
                return false;
        }
        return true;
    }*/

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

    private boolean currentUserAlreadyBid() {
        for(Bid bid: bids) {
            if(MiddlewareService.getCurrentUser().getId().equals(bid.getUser().getId()))
                return true;
        }
        return false;
    }

    public static class NotSuitedBidException extends RuntimeException{}

    public static class ProjectJson
    {
        public String id;
        public String title;
        public String description;
        public String imageUrl;
        public List<SkillJson> skills = new ArrayList<>();
        public List<BidJson> bids = new ArrayList<>();
        public int budget;
        public long deadline;
        public String winnerId;
        public String winnerName;
        public boolean alreadyBid;
        public static class SkillJson
        {
            public String name;
            public int point;
        }
        public static class BidJson
        {
            public String userid;
            public int budget;
        }
    }

    public ProjectJson toProjectJson ()
    {
        ProjectJson ret = new ProjectJson();
        ret.id = this.id;
        ret.title = this.title;
        ret.description = this.description;
        ret.imageUrl = this.imageUrl;
        for (Skill skill : skills)
        {
            ProjectJson.SkillJson skillJson = new ProjectJson.SkillJson();
            skillJson.name = skill.getName();
            skillJson.point = skill.getPoint();
            ret.skills.add(skillJson);
        }
        for (Bid bid : bids)
        {
            ProjectJson.BidJson bidJson = new ProjectJson.BidJson();
            bidJson.userid = bid.getUser().getId();
            bidJson.budget = bid.getBudget();
            ret.bids.add(bidJson);
        }
        ret.alreadyBid = currentUserAlreadyBid();
        ret.budget = this.budget;
        ret.deadline = this.deadline;
        if (this.winner != null) {
            ret.winnerId = this.winner.getId();
            ret.winnerName = this.winner.getFullName();
        }
        else {
            ret.winnerId = "";
            ret.winnerName = "";
        }
        return ret;
    }
}
