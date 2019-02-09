import java.util.List;

public class Project {
    private String title;
    private List<Skill> skills;
    private int budget;
    private List<Worker> candidates;

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
        for (Skill skill : skills)
            this.skills.add(skill);
    }

    public void addWorker(Worker candidate, int budget)
    {
        if (budget > this.budget)
            return;
        this.candidates.add(candidate);
    }

    private boolean workerSuited(Worker worker, int budget)
    {
        if (budget > this.budget)
            return false;
        for (int i = 0; i < this.skills.size(); i++)
            ;//TODO
        return true;
    }

    public void evaluate()
    {
        for (int i = 0; i < candidates; i++)
            //;
    }
}
