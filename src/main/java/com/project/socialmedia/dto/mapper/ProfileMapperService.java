package com.project.socialmedia.dto.mapper;

import com.project.socialmedia.dto.ProfileDTO;
import com.project.socialmedia.dto.PostDTO;
import com.project.socialmedia.entity.User;
import com.project.socialmedia.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileMapperService {

    public ProfileDTO toProfileDTO(User user, List<PostDTO> postDTOs) {
        return new ProfileDTO(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getUsername(),
                user.getSummary(),
                user.getFollowing().stream().map(User::getId).collect(Collectors.toList()),
                user.getFollowers().stream().map(User::getId).collect(Collectors.toList()),
                postDTOs,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public List<PostDTO> getPostsForUser(Set<Post> posts) {
        return posts.stream().map(this::toPostDTO).collect(Collectors.toList());
    }

    private PostDTO toPostDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getUserId(),
                post.getText(),
                post.getLikes(),
                post.getReposts(),
                post.getNumberComments(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
