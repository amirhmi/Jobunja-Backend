package Model.Service;

import DataAccess.ProjectDataMapper;
import DataAccess.ProjectSkillDataMapper;
import Model.Entity.Bid;
import Model.Entity.Project;
import Model.Entity.Skill;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class UpdateWinners implements Runnable {
    private static HttpClientGet httpClientGet = new HttpClientGet();
    @Override
    public void run() {
        System.out.println("updating winners");
        List<Project> projects = MiddlewareService.getRecentlyEndedProjects();
        for (Project p : projects) {
            Bid winnerBid = p.evaluate();
            if (winnerBid != null) {
                ProjectDataMapper.setWinner(p.getId(), winnerBid.getUserId());
            }
        }
    }
}
