package projectdefence.web;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(BlogNamespace.BLOG_URI)
public interface BlogNamespace {
    String BLOG_URI = "/blog";
}
