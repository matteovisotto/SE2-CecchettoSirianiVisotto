package it.dreamplatform.forum.integration;

import it.dreamplatform.forum.EntityManagerProvider;
import it.dreamplatform.forum.bean.UserBean;
import it.dreamplatform.forum.controller.DiscussionController;
import it.dreamplatform.forum.controller.UserController;
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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserIntegrationTest {
    private DiscussionService discussionService;
    private TopicService topicService;
    private UserService userService;
    private PostService postService;

    private UserController userController;

    @Rule
    public EntityManagerProvider provider = EntityManagerProvider.withUnit("forum-test");

    @Before
    public void setUp() {
        discussionService = new DiscussionService(this.provider.em());
        topicService = new TopicService(this.provider.em());
        userService = new UserService(this.provider.em());
        postService = new PostService(this.provider.em());
        UserMapper userMapper = new UserMapper();

        userController = new UserController(userMapper, userService);

        //userController = new UserController();
    }

    @Test
    public void createNewPolicyMakerTest() {
        /*User user = new User();
        user.setName("nameUserDB");
        user.setSurname("surnameUserDB");
        user.setAreaOfResidence("areaUserDB");
        user.setMail("mailUserDB");
        user.setDateOfBirth(new Date());
        user.setPolicyMakerID("ThisPolicyMakerId");*/

        UserBean user = new UserBean();
        user.setName("nameUserDB");
        user.setSurname("surnameUserDB");
        user.setAreaOfResidence("areaUserDB");
        user.setMail("mailUserDB");
        user.setDateOfBirth(new Date());
        user.setPolicyMakerID("ThisPolicyMakerId");

        this.provider.begin();

        userController.createUser(user);

        //userService.createUser(user);

        User userRetrieved = userService.getUserByPolicyMakerId("ThisPolicyMakerId");

        assertEquals("nameUserDB", userService.getUserById(userRetrieved.getUserId()).getName());
        assertEquals("surnameUserDB", userService.getUserById(userRetrieved.getUserId()).getSurname());
        assertEquals("areaUserDB", userService.getUserById(userRetrieved.getUserId()).getAreaOfResidence());
        assertEquals("mailUserDB", userService.getUserById(userRetrieved.getUserId()).getMail());
        assertEquals("ThisPolicyMakerId", userService.getUserById(userRetrieved.getUserId()).getPolicyMakerID());

        this.provider.rollback();
    }

    @Test
    public void createNewUserTest() {
        UserBean user = new UserBean();
        user.setName("nameUserDB");
        user.setSurname("surnameUserDB");
        user.setAreaOfResidence("areaUserDB");
        user.setMail("mailUserDB");
        user.setDateOfBirth(new Date());

        this.provider.begin();

        userController.createUser(user);

        User userRetrieved = userService.getUserByMail("mailUserDB");

        assertEquals("nameUserDB", userService.getUserById(userRetrieved.getUserId()).getName());
        assertEquals("surnameUserDB", userService.getUserById(userRetrieved.getUserId()).getSurname());
        assertEquals("areaUserDB", userService.getUserById(userRetrieved.getUserId()).getAreaOfResidence());
        assertEquals("mailUserDB", userService.getUserById(userRetrieved.getUserId()).getMail());
        assertNull(userService.getUserById(userRetrieved.getUserId()).getPolicyMakerID());

        this.provider.rollback();
    }

    @Test
    public void searchNotExistingUserTest() {
        this.provider.begin();

        userService.getUserByMail("mail");

        this.provider.rollback();
    }

    @Test
    public void updateUserTest() {
        User user = new User();
        user.setName("nameUserDB");
        user.setSurname("surnameUserDB");
        user.setAreaOfResidence("areaUserDB");
        user.setMail("mailUserDB");
        user.setDateOfBirth(new Date());

        this.provider.begin();

        userService.createUser(user);

        User userRetrieved = userService.getUserByMail("mailUserDB");

        assertEquals("nameUserDB", userService.getUserById(userRetrieved.getUserId()).getName());
        assertEquals("surnameUserDB", userService.getUserById(userRetrieved.getUserId()).getSurname());
        assertEquals("areaUserDB", userService.getUserById(userRetrieved.getUserId()).getAreaOfResidence());
        assertEquals("mailUserDB", userService.getUserById(userRetrieved.getUserId()).getMail());
        assertNull(userService.getUserById(userRetrieved.getUserId()).getPolicyMakerID());

        user.setMail("newMail");

        userService.updateUser(user);

        assertEquals("newMail", userService.getUserById(userRetrieved.getUserId()).getMail());

        this.provider.rollback();
    }

    @Test
    public void searchForMostActiveUsers() {
        this.provider.begin();

        User policyMaker = new User();
        policyMaker.setName("name1");
        policyMaker.setSurname("surname1");
        policyMaker.setAreaOfResidence("area1");
        policyMaker.setDateOfBirth(new java.util.Date());
        policyMaker.setMail("mail1");
        policyMaker.setPolicyMakerID("ThisPolicyMakerId");

        userService.createUser(policyMaker);

        Topic topic = topicService.getTopicById(27L);

        Post post1 = new Post();
        post1.setStatus(1);
        post1.setText("Discussion Text 1");
        post1.setTimestamp(new Date());
        post1.setCreator(policyMaker);

        List<Post> posts = new ArrayList<>();

        Discussion discussionTest = new Discussion();
        discussionTest.setText("Discussion Text 1");
        discussionTest.setTimestamp(new Date());
        discussionTest.setTitle("First Discussion");
        discussionTest.setTopic(topic);
        discussionTest.setPosts(posts);

        Long discussionId = discussionService.saveDiscussion(discussionTest);
        post1.setDiscussion(discussionTest);
        Long postId = postService.savePost(post1);

        User user1 = new User();
        user1.setName("name2");
        user1.setSurname("surname2");
        user1.setAreaOfResidence("area2");
        user1.setDateOfBirth(new java.util.Date());
        user1.setMail("mail2");

        userService.createUser(user1);

        User user2 = new User();
        user2.setName("name3");
        user2.setSurname("surname3");
        user2.setAreaOfResidence("area3");
        user2.setDateOfBirth(new java.util.Date());
        user2.setMail("mail3");

        userService.createUser(user2);

        Post post2 = new Post();
        post2.setStatus(0);
        post2.setText("Post Text 2");
        post2.setTimestamp(new Date());
        post2.setCreator(user1);
        post2.setDiscussion(discussionTest);

        Long postId2 = postService.savePost(post2);

        Post post3 = new Post();
        post3.setPostId(null);
        post3.setStatus(1);
        post3.setText("Post Text 3");
        post3.setTimestamp(new Date());
        post3.setCreator(user1);
        post3.setDiscussion(discussionTest);

        Long postId3 = postService.savePost(post3);

        Post post4 = new Post();
        post4.setPostId(null);
        post4.setStatus(1);
        post4.setText("Post Text 4");
        post4.setTimestamp(new Date());
        post4.setCreator(user2);
        post4.setDiscussion(discussionTest);

        Long postId4 = postService.savePost(post4);


        List<User> mostActiveUsers = userService.getMostActiveUsers(3);

        assertEquals(2, mostActiveUsers.size());
        assertEquals("mail2", mostActiveUsers.get(0).getMail());
        assertEquals("mail3", mostActiveUsers.get(1).getMail());

        this.provider.rollback();
    }
}
