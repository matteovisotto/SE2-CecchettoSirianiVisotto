package it.dreamplatform.forum.entities;

import it.dreamplatform.forum.utils.StatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Post", schema = "dream_forum")
@NamedQuery(name = "Post.findByDiscussion", query = "SELECT p FROM Post p where p.discussion.discussionId = :discussionId")
public class Post implements Serializable {
    @Id
    @Column(columnDefinition = "integer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "status")
    private int statusId;

    @ManyToOne
    @JoinColumn(name = "creatorId")
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discussionId")
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