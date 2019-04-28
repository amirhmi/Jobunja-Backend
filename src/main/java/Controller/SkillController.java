//package Controller;
//
//import Model.Entity.Skill;
//import Model.Entity.User;
//import Model.Service.MiddlewareService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/skills")
//public class SkillController {
//    @GetMapping
//    public List<String> getAddableSkills()
//    {
//        return MiddlewareService.CanBeAddedSkills();
//    }
//
//    @PutMapping
//    public User.UserJson addSkill(@RequestParam String skillName) {
//        MiddlewareService.addSkillForLoginUser(skillName);
//        return MiddlewareService.getCurrentUser().toUserJson();
//    }
//
//    @DeleteMapping
//    public User.UserJson removeSkill(@RequestParam String skillName) {
//        MiddlewareService.RemoveSkillForLoginUser(skillName);
//        return MiddlewareService.getCurrentUser().toUserJson();
//    }
//}
