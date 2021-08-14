package projectdefence.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectdefence.models.viewModels.BlogViewModel;
import projectdefence.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogRestController {

    private final BlogService blogService;

    public BlogRestController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/info")
    public ResponseEntity<List<BlogViewModel>> findAll() {
        return ResponseEntity
                .ok()
                .body(blogService.findAll());
    }


}
