package com.milosz.tai.app;

import com.milosz.tai.app.Entities.MoviePersonComment;
import com.milosz.tai.app.Entities.User;
import com.milosz.tai.app.Payload.CommentResponse;
import com.milosz.tai.app.Payload.UserInComment;

public class Utils {

    public static CommentResponse makeCommentResponse(MoviePersonComment item) {
        User user = item.getUser();
        UserInComment userInComment = new UserInComment(user.getId(), user.getNickname(), user.getImage());
        return new CommentResponse(userInComment, item.getContent(), item.getCommentedAt());
    }
}
