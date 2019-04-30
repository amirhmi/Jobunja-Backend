package Model.Service;

import DataAccess.ProjectDataMapper;
import DataAccess.ProjectSkillDataMapper;
import Model.Entity.Project;
import Model.Entity.Skill;
import Model.Service.HttpClientGet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FetchProjects implements Runnable {
    private static HttpClientGet httpClientGet = new HttpClientGet();
    @Override
    public void run() {
        System.out.println("getting projects");
        try {
            String project_data_json = httpClientGet.HttpGetRequest(RequestType.PROJECT);
            List<Project> projects = JsonParser.parseProjectList(project_data_json);
            for (Project p : projects) {
                ProjectDataMapper.insert(p);
                for (Skill skill : p.getSkills()) {
                    ProjectSkillDataMapper.insert(p.getId(), skill.getName(), skill.getPoint());
                }
            }
            System.out.println("projects updated");
        }
        catch (IOException e)
        {
            System.out.println("IOException");
            return;
        }
        catch (SQLException e)
        {
            System.out.println("SQLException");
            System.out.println(e.getMessage());
        }
    }
}
