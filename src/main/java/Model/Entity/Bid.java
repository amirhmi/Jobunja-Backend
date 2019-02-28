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
}
