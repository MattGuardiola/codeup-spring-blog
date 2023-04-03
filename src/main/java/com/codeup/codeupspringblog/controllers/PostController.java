package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
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
    private final UserRepository userDao;

    private final EmailService emailService;

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
            User matt =userDao.findById(1L).get();
            post.setUser(matt);
            postDao.save(post);
            emailService.prepareAndSend(post, "A Post Has Been Created", post.getTitle() + ": " + post.getBody() );
        return "redirect:/posts";
    }


    @GetMapping("/{id}/edit")
    public String returnEditPage(@PathVariable("id") Long id, Model model) {
        Post post = postDao.findById(id).get();
        model.addAttribute("blogpost", post);
        return "posts/edit";
    }

    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute("blogPost") Post updatedPost) {
        Post post = postDao.findById(id).get();
        post.setTitle(updatedPost.getTitle());
        post.setBody(updatedPost.getBody());
        postDao.save(post);
        return "redirect:/posts";
    }

}
