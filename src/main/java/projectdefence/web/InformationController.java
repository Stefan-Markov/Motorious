package projectdefence.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class InformationController {

    @GetMapping("exercise")
    public String exercise(){

        return "exercise_info";
    }

    @GetMapping("massage")
    public String massage(){

        return "massage_info";
    }


    @GetMapping("physiotherapy")
    public String physiotherapy(){

        return "physiotherapy_info";
    }

}
