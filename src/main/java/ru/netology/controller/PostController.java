package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    protected void sendJson(Object data, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void all(HttpServletResponse response) throws IOException {
        sendJson(service.all(), response);
    }

    public void getById(long id, HttpServletResponse response) throws IOException, NotFoundException {
        sendJson(service.getById(id), response);
    }

    public void save(Reader body, HttpServletResponse response) throws IOException, NotFoundException {
        final var post = new Gson().fromJson(body, Post.class);
        sendJson(service.save(post), response);
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        service.removeById(id);
        response.getWriter().close();
    }
}
