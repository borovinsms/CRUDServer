package ru.netology.service;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.IPostRepository;

import java.util.List;

public class PostService implements IPostService {

    private static PostService instance;
    private final IPostRepository repository;

    private PostService(IPostRepository repository) {
        this.repository = repository;
    }

    public static PostService getInstance(IPostRepository repository) {
        if (instance == null) {
            instance = new PostService(repository);
        }
        return instance;
    }

    @Override
    public List<Post> all() {
        return repository.all();
    }

    @Override
    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Post save(Post post) throws NotFoundException {
        return repository.save(post);
    }

    @Override
    public void removeById(long id) {
        repository.removeById(id);
    }
}

