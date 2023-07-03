package ru.netology.service;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;

public interface IPostService {

    List<Post> all();

    Post getById(long id) throws NotFoundException;

    Post save(Post post);

    void removeById(long id);
}
