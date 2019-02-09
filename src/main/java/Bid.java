public class Bid {
    private Worker worker;
    private int budget;
    public Bid(Worker worker, int budget)
    {
        this.worker = worker;
        this.budget = budget;
    }
    public Worker getWorker()
    {
        return worker;
    }
    public int getBudget()
    {
        return budget;
    }
}
