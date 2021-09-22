package projectdefence.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectdefence.models.viewModels.BlogViewModel;
import projectdefence.security.rolesAuth.IsAdmin;
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

    @RequestMapping(value = "/deleteById/{id}",
            method = {RequestMethod.DELETE, RequestMethod.GET})
    @IsAdmin
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        blogService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
