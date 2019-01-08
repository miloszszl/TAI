package com.milosz.tai.app.Entities;

import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.COMMENT_CONTENT_LENGTH;

@MappedSuperclass
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Lob
    @Size(max = COMMENT_CONTENT_LENGTH)
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentedAt = new Date();

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastUpdatedAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    private Date removeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Date commentedAt) {
        this.commentedAt = commentedAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
    }

}
