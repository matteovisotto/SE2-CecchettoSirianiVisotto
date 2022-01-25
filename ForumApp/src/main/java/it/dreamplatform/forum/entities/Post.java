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

    private int status;

    @ManyToOne
    @JoinColumn(name = "creatorId")
    private User creator;

    @ManyToOne
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