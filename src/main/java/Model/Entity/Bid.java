package Model.Entity;

import DataAccess.ProjectDataMapper;
import DataAccess.UserDataMapper;

public class Bid {
    private String userId;
    private String projectId;
    private int budget;
    public Bid(String userId, int budget, String projectId)
    {
        this.userId = userId;
        this.budget = budget;
        this.projectId = projectId;
    }
    public User getUser () { return UserDataMapper.find(userId); }

    public Project getProject() { return ProjectDataMapper.find(projectId); }

    public int getBudget() { return budget; }

    public static class BidJson
    {
        public String user;
        public String project;
        public int budget;
    }

    public BidJson toBidJson ()
    {
        BidJson ret = new BidJson();
        ret.user = this.userId;
        ret.project = this.projectId;
        ret.budget = this.budget;
        return ret;
    }
}
