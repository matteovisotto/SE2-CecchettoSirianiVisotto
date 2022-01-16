package org.dream.forum.entities;

import org.dream.forum.utils.StatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Post", schema = "dream_forum")
@NamedQuery(name = "Post.findByDiscussion", query = "SELECT p FROM Post p where p.discussion.discussionId = :discussionId")
public class Post implements Serializable {
    private Long postId;
    private String text;
    private Date timestamp;

    private int statusId;

    private User creator;

    private Discussion discussion;

    @Id
    @Column(columnDefinition = "integer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Temporal(TemporalType.TIMESTAMP)
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Column(name = "status")
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Transient
    public StatusEnum getStatus() {
        return StatusEnum.getStatus(this.statusId);
    }

    public void setStatus(StatusEnum status) {
        this.statusId = status.ordinal();
    }

    @ManyToOne
    @JoinColumn(name = "creatorId")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "discussionId")
    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }
}