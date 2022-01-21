package it.dreamplatform.forum.bean;

import it.dreamplatform.forum.entities.Discussion;

import java.io.Serializable;
import java.util.Date;

public class PostBean implements Serializable {
    private Long postId;
    private String text;
    private Date timestamp;
    private int status;
    private PublicUserBean creator;
    //private Discussion discussion;

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

    public PublicUserBean getCreator() {
        return creator;
    }

    public void setCreator(PublicUserBean creator) {
        this.creator = creator;
    }

    /*public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }*/
}
