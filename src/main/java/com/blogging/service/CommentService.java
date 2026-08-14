package com.blogging.service;

import com.blogging.dto.CommentRequest;
import com.blogging.dto.CommentResponse;
import com.blogging.entity.Comment;
import com.blogging.entity.Post;
import com.blogging.entity.User;
import com.blogging.repository.CommentRepository;
import com.blogging.repository.PostRepository;
import com.blogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentResponse addComment(Long postId,
                                      CommentRequest request,
                                      String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(post);
        comment.setAuthor(author);

        Comment saved = commentRepository.save(comment);
        return mapToResponse(saved);
    }

    public Page<CommentResponse> getCommentsByPost(Long postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable)
                .map(this::mapToResponse);
    }

    public CommentResponse updateComment(Long commentId,
                                         CommentRequest request,
                                         String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You can only edit your own comments!");
        }

        comment.setContent(request.getContent());
        Comment updated = commentRepository.save(comment);
        return mapToResponse(updated);
    }

    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You can only delete your own comments!");
        }
        commentRepository.delete(comment);
    }

    private CommentResponse mapToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setAuthorUsername(comment.getAuthor().getUsername());
        response.setPostId(comment.getPost().getId());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());
        return response;
    }
}