package it.dreamplatform.forum.bean;

import java.util.List;

public class DiscussionContentBean extends DiscussionBean {
    List<PostBean> posts;

    public List<PostBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostBean> posts) {
        this.posts = posts;
    }
}
