package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// Stub
public class PostRepository implements IPostRepository {

    protected static final Long startId = 0L;

    private static PostRepository instance;

    private final ConcurrentMap<Long, Post> posts;

    private Long counter;

    private PostRepository() {
        this.posts = new ConcurrentHashMap<>();
        this.counter = startId + 1;
    }

    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }
        return instance;
    }

    @Override
    public List<Post> all() {
        return List.copyOf(posts.values());
    }

    @Override
    public Optional<Post> getById(long id) {
        return posts.values().stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }

    @Override
    public Post save(Post post) {
        final var id = post.getId();
        if (id != startId && !posts.containsKey(id)) {
            throw new NotFoundException();
        }
        if (id == startId) {
            post.setId(counter);
            counter++;
        }
        posts.put(post.getId(), post);
        return posts.get(post.getId());
    }

    @Override
    public void removeById(long id) {
        posts.remove(id);
    }
}
