package Controller;

import Model.Entity.Bid;
import Model.Entity.Project;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.*;
import Exception.CustomException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping
    public List<Project.ProjectJson> getProjects(@RequestAttribute int userId) {
        List<Project> response = MiddlewareService.getSuitedProjects(0, 0, false, "", userId);
        List<Project.ProjectJson> ret = new ArrayList<>();
        for (Project project : response)
            ret.add(project.toProjectJson(userId));
        return ret;
    }

    @GetMapping("/{id}")
    public Project.ProjectJson getProject(@PathVariable(value = "id") String id,  @RequestAttribute int userId) {
        Project project = MiddlewareService.getSpecificProject(id, userId);
        if(project == null) {
            throw new CustomException.ProjectNotFoundException();
        }
        return project.toProjectJson(userId);
    }

    @GetMapping("/page")
    public List<Project.ProjectJson> getProjectPagination(@RequestParam(value = "limit") int limit, @RequestAttribute int userId
            , @RequestParam(value = "page") int page, @RequestParam(value = "searchKey" , required = false) String searchKey) {
        List<Project> response = MiddlewareService.getSuitedProjects(page, limit, true, searchKey, userId);
        List<Project.ProjectJson> ret = new ArrayList<>();
        for (Project project : response)
            ret.add(project.toProjectJson(userId));
        return ret;
    }

    @PostMapping("/{id}/bid")
    public Bid.BidJson setBid(@PathVariable(value = "id") String id,
                              Integer bidAmount, @RequestAttribute int userId) {
        if(bidAmount == null) {
            throw new CustomException.BadBidAmountException();
        }
        Bid bid = MiddlewareService.setBid(id, bidAmount, userId);
        return bid.toBidJson();
    }
}
