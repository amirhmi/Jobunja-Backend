package Model.Entity;

public class Bid {
    private User user;
    private Project project;
    private int budget;
    public Bid(User user, int budget, Project project)
    {
        this.user = user;
        this.budget = budget;
        this.project = project;
    }
    public User getUser () { return user; }

    public Project getProject() { return project; }

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
        ret.user = this.user.getId();
        ret.project = this.project.getId();
        ret.budget = this.budget;
        return ret;
    }
}
