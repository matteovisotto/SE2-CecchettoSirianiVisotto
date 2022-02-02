package it.dreamplatform.forum.integration;

import it.dreamplatform.forum.EntityManagerProvider;
import it.dreamplatform.forum.bean.PostBean;
import it.dreamplatform.forum.bean.PublicUserBean;
import it.dreamplatform.forum.bean.UserBean;
import it.dreamplatform.forum.controller.DiscussionController;
import it.dreamplatform.forum.controller.NotificationController;
import it.dreamplatform.forum.controller.PostController;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.entities.Post;
import it.dreamplatform.forum.entities.Topic;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.mapper.DiscussionMapper;
import it.dreamplatform.forum.mapper.PostMapper;
import it.dreamplatform.forum.mapper.UserMapper;
import it.dreamplatform.forum.services.DiscussionService;
import it.dreamplatform.forum.services.PostService;
import it.dreamplatform.forum.services.TopicService;
import it.dreamplatform.forum.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PostIntegrationTest {

    private DiscussionService discussionService;
    private TopicService topicService;
    private UserService userService;
    private PostService postService;

    private PostController postController;

    private Discussion discussion;
    private Post post;
    private User policyMaker;

    @Rule
    public EntityManagerProvider provider = EntityManagerProvider.withUnit("forum-test");

    @Before
    public void setUp() {
        discussionService = new DiscussionService(this.provider.em());
        topicService = new TopicService(this.provider.em());
        userService = new UserService(this.provider.em());
        postService = new PostService(this.provider.em());
        UserMapper userMapper = new UserMapper();
        PostMapper postMapper = new PostMapper(userMapper, discussionService);
        NotificationController notificationController = new NotificationController(discussionService, postService);

        postController = new PostController(postService, postMapper, userService, userMapper, notificationController);

        policyMaker = new User();
        policyMaker.setName("name");
        policyMaker.setSurname("surname");
        policyMaker.setAreaOfResidence("area");
        policyMaker.setDateOfBirth(new java.util.Date());
        policyMaker.setMail("mail");
        policyMaker.setPolicyMakerID("policy");

        this.provider.em().persist(policyMaker);

        //this.provider.begin();

        Topic topic = topicService.getTopicById(27L);

        discussion = new Discussion();
        discussion.setText("Discussion Text 1");
        discussion.setTimestamp(new Date());
        discussion.setTitle("First Discussion");
        discussion.setTopic(topic);
        discussion.setPosts(new ArrayList<>());

        this.provider.em().persist(discussion);

        post = new Post();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(policyMaker);
        post.setDiscussion(discussion);

        this.provider.em().persist(post);
    }

    @After
    public void close() {
        this.provider.em().remove(policyMaker);
    }

    @Test
    public void insertNewPostPolicyMakerTest() throws Exception {
        /*Post post1 = new Post();
        post1.setStatus(1);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(policyMaker);
        post1.setDiscussion(discussion);*/

        this.provider.begin();

        PublicUserBean publicUserBean = new PublicUserBean();
        publicUserBean.setName("name");
        publicUserBean.setSurname("surname");
        publicUserBean.setAreaOfResidence("area");
        publicUserBean.setPolicyMaker(true);

        UserBean userBean = new UserBean();
        userBean.setName("name");
        userBean.setSurname("surname");
        userBean.setAreaOfResidence("area");
        userBean.setDateOfBirth(new java.util.Date());
        userBean.setMail("mail");
        userBean.setPolicyMakerID("policy");
        userBean.setUserId(userService.getUserByMail("mail").getUserId());

        PostBean post1 = new PostBean();
        post1.setStatus(1);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(publicUserBean);
        post1.setDiscussionId(discussionService.getDiscussionsByTopicId(27L).get(0).getDiscussionId());

        postController.publishPost(post1, userBean);

        //Long postId = postService.savePost(post1);

        assertEquals("Post Text 1", postService.getPostsByDiscussionId(discussionService.getDiscussionsByTopicId(27L).get(0).getDiscussionId()).get(1).getText());

        this.provider.rollback();
    }

    @Test
    public void insertNewPostUserTest() throws Exception {
        this.provider.begin();

        User user1 = new User();
        user1.setName("name1");
        user1.setSurname("surname1");
        user1.setAreaOfResidence("area1");
        user1.setDateOfBirth(new java.util.Date());
        user1.setMail("mail1");

        userService.createUser(user1);

        PublicUserBean publicUserBean = new PublicUserBean();
        publicUserBean.setName("name1");
        publicUserBean.setSurname("surname1");
        publicUserBean.setAreaOfResidence("area1");
        publicUserBean.setPolicyMaker(false);

        UserBean userBean = new UserBean();
        userBean.setName("name1");
        userBean.setSurname("surname1");
        userBean.setAreaOfResidence("area1");
        userBean.setDateOfBirth(new java.util.Date());
        userBean.setMail("mail1");
        userBean.setUserId(userService.getUserByMail("mail1").getUserId());

        PostBean post1 = new PostBean();
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(publicUserBean);
        post1.setDiscussionId(discussionService.getDiscussionsByTopicId(27L).get(0).getDiscussionId());

        postController.publishPost(post1, userBean);

        /*Post post1 = new Post();
        post1.setStatus(0);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(user1);
        post1.setDiscussion(discussion);

        Long postId = postService.savePost(post1);*/

        Post post = postService.getPostsByDiscussionId(discussionService.getDiscussionsByTopicId(27L).get(0).getDiscussionId()).get(1);

        assertEquals(post.getPostId(), postService.getPendingPosts().get(0).getPostId());

        assertEquals(1, postService.getPostsByCreator(user1.getUserId()).size());

        this.provider.rollback();
    }

    @Test
    public void deleteAPostTest() throws Exception {
        this.provider.begin();

        PublicUserBean publicUserBean = new PublicUserBean();
        publicUserBean.setName("name");
        publicUserBean.setSurname("surname");
        publicUserBean.setAreaOfResidence("area");
        publicUserBean.setPolicyMaker(true);

        UserBean userBean = new UserBean();
        userBean.setName("name");
        userBean.setSurname("surname");
        userBean.setAreaOfResidence("area");
        userBean.setDateOfBirth(new java.util.Date());
        userBean.setMail("mail");
        userBean.setPolicyMakerID("policy");
        userBean.setUserId(userService.getUserByMail("mail").getUserId());

        PostBean post1 = new PostBean();
        post1.setStatus(1);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(publicUserBean);
        post1.setDiscussionId(discussionService.getDiscussionsByTopicId(27L).get(0).getDiscussionId());

        postController.publishPost(post1, userBean);

        Post retrievedPost = postService.getPostsByDiscussionId(discussionService.getDiscussionsByTopicId(27L).get(0).getDiscussionId()).get(1);

        assertEquals("Post Text 1", retrievedPost.getText());

        postController.deletePost(retrievedPost.getPostId());
        //postService.deletePost(postRetrieved);

        assertEquals(1, postService.getPostsByDiscussionId(discussionService.getDiscussionByPolicyMaker(userService.getUserByMail("mail").getPolicyMakerID()).get(0).getDiscussionId()).size());

        this.provider.rollback();
    }

    @Test
    public void updatePostTest() {
        Post post1 = new Post();
        post1.setStatus(1);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(policyMaker);
        post1.setDiscussion(discussion);

        this.provider.begin();
        Long postId = postService.savePost(post1);

        assertEquals(postId, postService.getPostById(postId).getPostId());

        post1.setText("New Text");

        postService.savePost(post1);

        assertNotEquals("Post Text 1", postService.getPostById(postId).getText());
        assertEquals("New Text", postService.getPostById(postId).getText());

        this.provider.rollback();
    }

    @Test
    public void approvePendingPost() throws Exception {
        this.provider.begin();

        User user1 = new User();
        user1.setName("name1");
        user1.setSurname("surname1");
        user1.setAreaOfResidence("area1");
        user1.setDateOfBirth(new java.util.Date());
        user1.setMail("mail1");

        userService.createUser(user1);

        Post post1 = new Post();
        post1.setStatus(0);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(user1);
        post1.setDiscussion(discussion);

        Long postId = postService.savePost(post1);

        Post retrievedPost = postService.getPostById(postId);

        assertEquals(retrievedPost.getPostId(), postService.getPendingPosts().get(0).getPostId());

        postController.approvePendingPost(retrievedPost.getPostId());

        assertEquals(0, postService.getPendingPosts().size());

        assertEquals(1, postService.getPostById(postId).getStatus());

        this.provider.rollback();
    }

    @Test
    public void declinePendingPost() throws Exception {
        this.provider.begin();

        User user1 = new User();
        user1.setName("name1");
        user1.setSurname("surname1");
        user1.setAreaOfResidence("area1");
        user1.setDateOfBirth(new java.util.Date());
        user1.setMail("mail1");

        userService.createUser(user1);

        Post post1 = new Post();
        post1.setStatus(0);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(user1);
        post1.setDiscussion(discussion);

        Long postId = postService.savePost(post1);

        Post retrievedPost = postService.getPostById(postId);

        assertEquals(retrievedPost.getPostId(), postService.getPendingPosts().get(0).getPostId());

        postController.declinePendingPost(retrievedPost.getPostId());

        assertEquals(0, postService.getPendingPosts().size());

        this.provider.rollback();
    }

    @Test
    public void tryToAccessNotExistingPostTest() {
        this.provider.begin();
        assertThrows(Exception.class, () -> postController.getPostById(0L));
        this.provider.rollback();
    }

    @Test
    public void tryToApproveNotExistingPostTest() {
        this.provider.begin();
        assertThrows(Exception.class, () -> postController.approvePendingPost(0L));
        this.provider.rollback();
    }

    @Test
    public void tryToApproveAlreadyApprovedPostTest() {
        Post post1 = new Post();
        post1.setStatus(1);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(policyMaker);
        post1.setDiscussion(discussion);

        this.provider.begin();

        Long postId = postService.savePost(post1);

        assertThrows(Exception.class, () -> postController.approvePendingPost(postId));

        this.provider.rollback();
    }

    @Test
    public void tryToDeclineNotExistingPostTest() {
        this.provider.begin();
        assertThrows(Exception.class, () -> postController.declinePendingPost(0L));
        this.provider.rollback();
    }

    @Test
    public void tryToDeclineAlreadyApprovedPostTest() {
        Post post1 = new Post();
        post1.setStatus(1);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(policyMaker);
        post1.setDiscussion(discussion);

        this.provider.begin();

        Long postId = postService.savePost(post1);

        assertThrows(Exception.class, () -> postController.declinePendingPost(postId));

        this.provider.rollback();
    }

    @Test
    public void notValidIdToDeletePostTest() {
        this.provider.begin();
        assertThrows(Exception.class, () -> postController.deletePost(null));
        this.provider.rollback();
    }

    @Test
    public void tryToDeleteNotExistingPostTest() {
        this.provider.begin();
        assertThrows(Exception.class, () -> postController.deletePost(null));
        this.provider.rollback();
    }

    @Test
    public void tryToPublishPostWithNoValidUserTest() {
        PublicUserBean publicUserBean = new PublicUserBean();
        publicUserBean.setName("nameUserPublic");
        publicUserBean.setSurname("surnameUserPublic");
        publicUserBean.setAreaOfResidence("areaUserPublic");
        publicUserBean.setPolicyMaker(true);
        publicUserBean.setUserId(0L);

        UserBean userBean = new UserBean();
        userBean.setName("nameUser");
        userBean.setSurname("surnameUser");
        userBean.setAreaOfResidence("areaUser");
        userBean.setPolicyMakerID("ThisPolicyMakerId");
        userBean.setMail("mailUser");
        userBean.setDateOfBirth(new Date());
        userBean.setUserId(0L);

        PostBean post = new PostBean();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(publicUserBean);

        this.provider.begin();
        assertThrows(Exception.class, () -> postController.publishPost(post, userBean));
        this.provider.rollback();
    }

    @Test
    public void tryToPublishPostWithNoTextTest() {
        PublicUserBean publicUserBean = new PublicUserBean();
        publicUserBean.setName("nameUserPublic");
        publicUserBean.setSurname("surnameUserPublic");
        publicUserBean.setAreaOfResidence("areaUserPublic");
        publicUserBean.setPolicyMaker(true);
        publicUserBean.setUserId(0L);

        UserBean userBean = new UserBean();
        userBean.setName("nameUser");
        userBean.setSurname("surnameUser");
        userBean.setAreaOfResidence("areaUser");
        userBean.setPolicyMakerID("ThisPolicyMakerId");
        userBean.setMail("mailUser");
        userBean.setDateOfBirth(new Date());
        userBean.setUserId(0L);

        PostBean post = new PostBean();
        post.setStatus(1);
        post.setText(null);
        post.setTimestamp(new Date());
        post.setCreator(publicUserBean);

        User user = new User();
        user.setName("nameUserDB");
        user.setSurname("surnameUserDB");
        user.setAreaOfResidence("areaUserDB");
        user.setPolicyMakerID("ThisPolicyMakerId");
        user.setMail("mailUserDB");
        user.setDateOfBirth(new Date());

        this.provider.begin();

        userService.createUser(user);

        assertThrows(Exception.class, () -> postController.publishPost(post, userBean));

        this.provider.rollback();
    }

    @Test
    public void modifyNotExistingPostTest() {
        PublicUserBean publicUserBean = new PublicUserBean();
        publicUserBean.setName("nameUserPublic");
        publicUserBean.setSurname("surnameUserPublic");
        publicUserBean.setAreaOfResidence("areaUserPublic");
        publicUserBean.setPolicyMaker(true);
        publicUserBean.setUserId(0L);

        UserBean userBean = new UserBean();
        userBean.setName("nameUser");
        userBean.setSurname("surnameUser");
        userBean.setAreaOfResidence("areaUser");
        userBean.setPolicyMakerID("ThisPolicyMakerId");
        userBean.setMail("mailUser");
        userBean.setDateOfBirth(new Date());
        userBean.setUserId(0L);

        PostBean post = new PostBean();
        post.setStatus(1);
        post.setTimestamp(new Date());
        post.setCreator(publicUserBean);

        this.provider.begin();
        assertThrows(Exception.class, () -> postController.modifyPost(post, userBean));
        this.provider.rollback();
    }

    @Test
    public void userTryToModifyPostOfAnotherUserTest() {
        User user1 = new User();
        user1.setName("nameUserDB");
        user1.setSurname("surnameUserDB");
        user1.setAreaOfResidence("areaUserDB");
        user1.setMail("mailUserDB");
        user1.setDateOfBirth(new Date());

        User user2 = new User();
        user2.setName("nameUserDB2");
        user2.setSurname("surnameUserDB2");
        user2.setAreaOfResidence("areaUserDB2");
        user2.setMail("mailUserDB2");
        user2.setDateOfBirth(new Date());

        Post post1 = new Post();
        post1.setStatus(1);
        post1.setText("Post Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(user1);
        post1.setDiscussion(discussion);

        this.provider.begin();

        userService.createUser(user1);

        userService.createUser(user2);

        Long postId = postService.savePost(post1);

        PublicUserBean publicUserBean = new PublicUserBean();
        publicUserBean.setName("nameUserPublic");
        publicUserBean.setSurname("surnameUserPublic");
        publicUserBean.setAreaOfResidence("areaUserPublic");
        publicUserBean.setPolicyMaker(true);
        publicUserBean.setUserId(userService.getUserByMail("mailUserDB").getUserId());

        UserBean userBean = new UserBean();
        userBean.setName("nameUser");
        userBean.setSurname("surnameUser");
        userBean.setAreaOfResidence("areaUser");
        userBean.setPolicyMakerID("ThisPolicyMakerId");
        userBean.setMail("mailUser");
        userBean.setDateOfBirth(new Date());
        userBean.setUserId(userService.getUserByMail("mailUserDB2").getUserId());

        PostBean post = new PostBean();
        post.setStatus(1);
        post.setTimestamp(new Date());
        post.setCreator(publicUserBean);

        assertEquals(postId, postService.getPostById(postId).getPostId());
        assertNotEquals(userBean.getUserId(), publicUserBean.getUserId());

        assertThrows(Exception.class, () -> postController.modifyPost(post, userBean));
        this.provider.rollback();
    }

    @Test
    public void tryToRetrievePostOfNotExistingUser() {
        this.provider.begin();
        assertThrows(Exception.class, () -> postController.getPostsByUser(0L));
        this.provider.rollback();
    }
}
