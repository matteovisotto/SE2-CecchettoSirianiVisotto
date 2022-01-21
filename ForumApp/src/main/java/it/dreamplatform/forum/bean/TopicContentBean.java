package it.dreamplatform.forum.bean;

import it.dreamplatform.forum.bean.DiscussionBean;

import java.util.List;
import java.util.Set;

public class TopicContentBean extends TopicBean {

    private List<DiscussionBean> discussions;

    public List<DiscussionBean> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<DiscussionBean> discussions) {
        this.discussions = discussions;
    }
}
