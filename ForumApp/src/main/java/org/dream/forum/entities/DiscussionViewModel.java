package org.dream.forum.entities;

import org.dream.forum.services.PostService;
import org.dream.forum.services.TopicService;

import javax.ejb.EJB;
import java.io.Serializable;
import java.util.Date;

public class DiscussionViewModel implements Serializable {
    @EJB(name = "org.dream.forum.services/PostService")
    PostService postService;

    private Long discussionId;
    private String title;
    private Topic topic;
    private Date createdAt;
    private User creator;
    private String content;


    public DiscussionViewModel(Discussion discussion){
        this.discussionId = discussion.getDiscussionId();
        this.title = discussion.getTitle();
        this.topic = discussion.getTopic();
        this.createdAt = discussion.getTimestamp();
        Post post = postService.getByDiscussionId(discussionId).get(0); // Da sostituire
        this.creator = post.getCreator();
        this.content = post.getText();

    }

    public Long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(Long discussionId) {
        this.discussionId = discussionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
