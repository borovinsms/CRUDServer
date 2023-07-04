package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository implements IPostRepository {

    protected static final long START_ID = 0L;

    private final ConcurrentMap<Long, Post> posts;

    private final AtomicLong counter;

    public PostRepository() {
        this.posts = new ConcurrentHashMap<>();
        this.counter = new AtomicLong(START_ID + 1);
    }

    @Override
    public List<Post> all() {
        return Collections.synchronizedList(new ArrayList<>(posts.values()));
    }

    @Override
    public Optional<Post> getById(long id) {
        return posts.containsKey(id) ? Optional.of(posts.get(id)) : Optional.empty();
    }

    @Override
    public Post save(Post post) {
        final var id = post.getId();
        if (id != START_ID && !posts.containsKey(id)) {
            throw new NotFoundException();
        }
        if (id == START_ID) {
            post.setId(counter.getAndIncrement());
        }
        posts.put(post.getId(), post);
        return posts.get(post.getId());
    }

    @Override
    public void removeById(long id) {
        posts.remove(id);
    }
}
