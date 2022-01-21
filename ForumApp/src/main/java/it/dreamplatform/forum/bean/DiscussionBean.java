package it.dreamplatform.forum.bean;

import java.io.Serializable;
import java.util.Date;

public class DiscussionBean implements Serializable {
    private Long discussionId;
    private String title;
    private String text;
    private Date timestamp;
    private Long topicId;
    private PublicUserBean creator;
    private Integer number_replies;

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

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public PublicUserBean getCreator() {
        return creator;
    }

    public void setCreator(PublicUserBean creator) {
        this.creator = creator;
    }

    public Integer getNumberReplies() {
        return number_replies;
    }

    public void setNumberReplies(Integer number_replies) {
        this.number_replies = number_replies;
    }
}
