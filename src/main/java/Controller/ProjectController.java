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
    public List<Project.ProjectJson> getProjects() {
        List<Project> response = MiddlewareService.getSuitedProjects(0, 0, false, "");
        List<Project.ProjectJson> ret = new ArrayList<>();
        for (Project project : response)
            ret.add(project.toProjectJson());
        return ret;
    }

    @GetMapping("/{id}")
    public Project.ProjectJson getProject(@PathVariable(value = "id") String id) {
        Project project = MiddlewareService.getSpecificProject(id);
        if(project == null) {
            throw new CustomException.ProjectNotFoundException();
        }
        return project.toProjectJson();
    }

    @GetMapping("/page")
    public List<Project.ProjectJson> getProjectPagination(@RequestParam(value = "limit") int limit
            , @RequestParam(value = "page") int page, @RequestParam(value = "searchKey" , required = false) String searchKey) {
        List<Project> response = MiddlewareService.getSuitedProjects(page, limit, true, searchKey);
        List<Project.ProjectJson> ret = new ArrayList<>();
        for (Project project : response)
            ret.add(project.toProjectJson());
        return ret;
    }

    @PostMapping("/{id}/bid")
    public Bid.BidJson setBid(@PathVariable(value = "id") String id,
                              Integer bidAmount) {
        if(bidAmount == null) {
            throw new CustomException.BadBidAmountException();
        }
        Bid bid = MiddlewareService.setBid(id, bidAmount);
        return bid.toBidJson();
    }
}
