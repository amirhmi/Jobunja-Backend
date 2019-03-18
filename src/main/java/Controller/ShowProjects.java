package Controller;

import Model.Entity.Project;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowProjects {
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<Project> showProjects() {
        return MiddlewareService.getSuitedProjects();
    }
}
