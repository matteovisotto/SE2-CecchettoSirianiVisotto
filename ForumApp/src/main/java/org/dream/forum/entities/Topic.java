package org.dream.forum.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name="Topic", schema="dream_forum")
@NamedQuery(name="Topic.findAll", query = "SELECT t FROM Topic t")
public class Topic implements Serializable {
    private Long topicId;
    private String title;
    private Date timestamp;

    @Id
    @Column(columnDefinition="integer")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @Temporal(TemporalType.TIMESTAMP)
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
