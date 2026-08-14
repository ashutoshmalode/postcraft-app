package com.blogging.service;

import com.blogging.dto.PostRequest;
import com.blogging.dto.PostResponse;
import com.blogging.entity.Post;
import com.blogging.entity.Tag;
import com.blogging.entity.User;
import com.blogging.repository.PostRepository;
import com.blogging.repository.TagRepository;
import com.blogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    public PostResponse createPost(PostRequest request, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setSummary(request.getSummary());
        post.setAuthor(author);
        post.setStatus(request.getStatus() != null &&
                request.getStatus().equalsIgnoreCase("PUBLISHED")
                ? Post.PostStatus.PUBLISHED : Post.PostStatus.DRAFT);

        if (request.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (String tagName : request.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        });
                tags.add(tag);
            }
            post.setTags(tags);
        }

        Post saved = postRepository.save(post);
        return mapToResponse(saved);
    }

    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findByStatus(Post.PostStatus.PUBLISHED, pageable)
                .map(this::mapToResponse);
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found!"));
        return mapToResponse(post);
    }

    public PostResponse updatePost(Long id, PostRequest request, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You can only edit your own posts!");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setSummary(request.getSummary());
        post.setStatus(request.getStatus() != null &&
                request.getStatus().equalsIgnoreCase("PUBLISHED")
                ? Post.PostStatus.PUBLISHED : Post.PostStatus.DRAFT);

        if (request.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (String tagName : request.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        });
                tags.add(tag);
            }
            post.setTags(tags);
        }

        Post updated = postRepository.save(post);
        return mapToResponse(updated);
    }

    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You can only delete your own posts!");
        }
        postRepository.delete(post);
    }

    public Page<PostResponse> searchPosts(String keyword, Pageable pageable) {
        return postRepository.searchPosts(keyword, pageable)
                .map(this::mapToResponse);
    }

    private PostResponse mapToResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setSummary(post.getSummary());
        response.setStatus(post.getStatus().name());
        response.setAuthorUsername(post.getAuthor().getUsername());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        response.setCommentCount(post.getComments() != null ? post.getComments().size() : 0);
        response.setTags(post.getTags() != null
                ? post.getTags().stream().map(Tag::getName).collect(Collectors.toList())
                : new ArrayList<>());
        return response;
    }
}