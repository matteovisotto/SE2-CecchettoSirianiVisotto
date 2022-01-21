package it.dreamplatform.forum.bean;

import java.util.List;

public class TopicContentBean extends TopicBean {

    private List<DiscussionBean> discussions;

    public List<DiscussionBean> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<DiscussionBean> discussions) {
        this.discussions = discussions;
    }
}
