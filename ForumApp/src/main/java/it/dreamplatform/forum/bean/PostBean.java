package it.dreamplatform.forum.bean;

import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.entities.User;

import java.util.Date;

public class PostBean {
    private Long postId;
    private String text;
    private Date timestamp;
    private int status;
    private User creator;
    private Discussion discussion;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }
}
