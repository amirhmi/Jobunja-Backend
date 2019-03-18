package Controller;

import Model.Entity.Bid;
import Model.Entity.Project;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import Exception.CustomException;

@RestController
public class SetBid {

    @RequestMapping(value = "/setbid/{projectid}", method = RequestMethod.POST)
    public Bid setBid(@PathVariable(value = "projectid") String projectId,
                      HttpServletRequest request,
                      @RequestParam(value="bidAmount", required=false) String bidAmountParam) {
        int bidAmount;
        try {
            bidAmount = Integer.parseInt(bidAmountParam);

        } catch (NumberFormatException e) {
            throw new CustomException.BadBidAmountException();
        }
        Bid bid = MiddlewareService.setBid(projectId, bidAmount);
        return bid;
    }
}
