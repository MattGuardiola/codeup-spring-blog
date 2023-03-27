package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/posts")
public class PostController {
    private final PostRepository postDao;

        @GetMapping
        public String indexPage(Model model) {
            List<Post> posts = postDao.findAll();
            model.addAttribute("posts", posts);
            return "posts/index";
        }

    @GetMapping(path = "/{id}")
    public String individualPost(Model model, @PathVariable long id) {
            Post post = postDao.findById(id).get();
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/create")
    public String returnCreatePage(Model model) {
        model.addAttribute("blogpost", new Post());
        return "posts/create";
    }


    @PostMapping("/create")
    public String createAPost(@ModelAttribute Post post) {
            postDao.save(post);
        return "redirect:/posts";
    }


}
