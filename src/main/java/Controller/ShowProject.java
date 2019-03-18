package Controller;

import Exception.CustomException;
import Model.Entity.Project;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowProject{
    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public Project showProject(@PathVariable(value = "id") String id) {
        if (id == null || id.isEmpty()) {
            throw new CustomException.BadRequestException();
        }

        Project project = MiddlewareService.getSpecificProject(id);
        if(project == null) {
            throw new CustomException.ProjectNotFoundException();
        }
        return project;
    }
}
