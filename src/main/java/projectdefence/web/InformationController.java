package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import projectdefence.service.BlogService;

@Controller
@RequestMapping("/info")
public class InformationController {


    private final BlogService blogService;

    public InformationController(BlogService blogService) {

        this.blogService = blogService;
    }

    @GetMapping("/exercise")
    public String exercise() {

        return "exercise_info";
    }

    @GetMapping("/massage")
    public String massage() {

        return "massage_info";
    }


    @GetMapping("/physiotherapy")
    public String physiotherapy() {

        return "physiotherapy_info";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        if (!model.containsAttribute("blogViewModels")) {
            model.addAttribute("size",this.blogService.findAllBlogs().size());
            model.addAttribute("blogViewModels", this.blogService.findAllBlogs());
        }
        return "blog";
    }

}
