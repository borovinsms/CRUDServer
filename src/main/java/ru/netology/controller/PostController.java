package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.IPostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";

    private static PostController instance;
    private final IPostService service;

    private PostController(IPostService service) {
        this.service = service;
    }

    public static PostController getInstance(IPostService service) {
        if (instance == null) {
            instance = new PostController(service);
        }
        return instance;
    }

    protected void sendJson(Object data, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void all(HttpServletResponse response) throws IOException {
        sendJson(service.all(), response);
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        try {
            sendJson(service.getById(id), response);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        try {
            final var post = new Gson().fromJson(body, Post.class);
            sendJson(service.save(post), response);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        service.removeById(id);
        response.getWriter().close();
    }
}
