package it.dreamplatform.forum.entities;

import it.dreamplatform.forum.JPAConfigurator.ConfigurePostFilter;
import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Discussion", schema = "dream_forum")
@Customizer(ConfigurePostFilter.class)
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

    @OneToMany(mappedBy = "discussion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;


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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {        this.posts = posts;    }
}
