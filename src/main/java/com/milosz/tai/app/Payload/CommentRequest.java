package com.milosz.tai.app.Payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.COMMENT_CONTENT_LENGTH;

public class CommentRequest {

    @NotBlank
    @Size(max=COMMENT_CONTENT_LENGTH)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
