package it.dreamplatform.forum.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Topic", schema = "dream_forum")
@NamedQuery(name = "Topic.findAll", query = "SELECT t FROM Topic t")
public class Topic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "integer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Topic", cascade = CascadeType.ALL)
    private Set<Discussion> discussions;


    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Discussion> getDiscussions() {
        return discussions;
    }

    public void addDiscussion(Discussion discussion) {
        getDiscussions().add(discussion);
        discussion.setTopic(this);
    }

    public void removeDiscussion(Discussion discussion){
        getDiscussions().remove(discussion);
    }
}
