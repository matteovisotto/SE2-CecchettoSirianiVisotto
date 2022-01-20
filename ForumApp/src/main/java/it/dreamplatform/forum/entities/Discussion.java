package it.dreamplatform.forum.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Discussion", schema = "dream_forum")

public class Discussion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "integer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discussionId;
    private String title;
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "topicId")
    private Topic topic;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discussion", cascade = CascadeType.ALL)
    private Set<Post> posts;


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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    //public void setPosts(Set<Post> posts) {        this.posts = posts;    }
}
