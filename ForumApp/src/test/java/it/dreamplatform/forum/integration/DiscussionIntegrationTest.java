package it.dreamplatform.forum.integration;

import it.dreamplatform.forum.EntityManagerProvider;
import it.dreamplatform.forum.controller.DiscussionController;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.entities.Post;
import it.dreamplatform.forum.entities.Topic;
import it.dreamplatform.forum.entities.User;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiscussionIntegrationTest {

    /*private static EntityManagerFactory emf;
    private static EntityManager em;

    private DiscussionService discussionService;
    private TopicService topicService;
    private UserService userService;
    private PostService postService;

    private Discussion discussion1;
    private Discussion discussion2;*/

    private DiscussionService discussionService;
    private TopicService topicService;
    private UserService userService;
    private PostService postService;

    private DiscussionController discussionController;
    //private DiscussionMapper discussionMapper;

    private User policyMaker;
    //private Topic topic;

    @Rule
    public EntityManagerProvider provider = EntityManagerProvider.withUnit("forum-test");

    /*@BeforeAll
    public static void setUpBeforeClass() {
        emf = Persistence.createEntityManagerFactory("forum-test");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        discussionService = new DiscussionService(em);
        topicService = new TopicService(em);
        userService = new UserService(em);
        postService = new PostService(em);
        createTestData();
    }

    @AfterEach
    public void tearDown() {
        if (em != null) {
            removeTestData();
            em.close();
        }
    }

    private void createTestData() {
        Topic topic = new Topic();
        topic.setTitle("First topic");
        topic.setTimestamp(new Date());

        User user = new User();
        user.setMail("mail");
        user.setDateOfBirth(new Date());
        user.setSurname("surname");
        user.setName("name");
        user.setAreaOfResidence("area");
        user.setPolicyMakerID("policy");

        em.getTransaction().begin();
        em.persist(topic);
        em.persist(user);
        em.flush();
        em.getTransaction().commit();

        discussion1 = new Discussion();
        discussion1.setText("Discussion Text 1");
        discussion1.setTimestamp(new Date());
        discussion1.setTitle("First Discussion");
        discussion1.setTopic(topic);

        discussion2 = new Discussion();
        discussion2.setText("Discussion Text 2");
        discussion2.setTimestamp(new Date());
        discussion2.setTitle("Second Discussion");
        discussion2.setTopic(topic);
    }

    private void removeTestData() {
        em.getTransaction().begin();

        //Discussions deleted on cascade
        Topic topic = em.find(Topic.class, 1L);
        if (topic != null) {
            em.remove(topic);
        }
        em.getTransaction().commit();
    }*/

    @Before
    public void setUp() {
        discussionService = new DiscussionService(this.provider.em());
        topicService = new TopicService(this.provider.em());
        userService = new UserService(this.provider.em());
        postService = new PostService(this.provider.em());

        discussionController = new DiscussionController();

        policyMaker = new User();
        policyMaker.setName("name");
        policyMaker.setSurname("surname");
        policyMaker.setAreaOfResidence("area");
        policyMaker.setDateOfBirth(new java.util.Date());
        policyMaker.setMail("mail");
        policyMaker.setPolicyMakerID("policy");

        /*topic = new Topic();
        topic.setTitle("First topic");
        topic.setTimestamp(new Date());*/

        this.provider.em().persist(policyMaker);
        //this.provider.em().persist(topic);
    }

    @After
    public void close() {
        this.provider.em().remove(policyMaker);
        //this.provider.em().remove(topic);
    }

    @Test
    public void insertFirstDiscussionWithPostTest() {
        this.provider.begin();

        Topic topic = topicService.getTopicById(27L);
        /*PublicUserBean userBean = new PublicUserBean();
        userBean.setName("name");
        userBean.setSurname("surname");
        userBean.setAreaOfResidence("area");
        userBean.setUserId(userService.getUserByMail("mail").getUserId());
        userBean.setPolicyMaker(true);

        PostBean post = new PostBean();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(userBean);

        List<PostBean> posts = new ArrayList<>();
        posts.add(post);*/

        /*DiscussionContentBean discussionTest = new DiscussionContentBean();
        //Discussion discussionTest = new Discussion();
        discussionTest.setText("Discussion Text 1");
        discussionTest.setTimestamp(new Date());
        discussionTest.setTitle("First Discussion");
        discussionTest.setTopicId(topicService.getAllTopics().get(0).getTopicId());
        discussionTest.setPosts(posts);*/

        Post post = new Post();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(policyMaker);

        List<Post> posts = new ArrayList<>();

        Discussion discussionTest = new Discussion();
        discussionTest.setDiscussionId(null);
        discussionTest.setText("Discussion Text 1");
        discussionTest.setTimestamp(new Date());
        discussionTest.setTitle("First Discussion");
        discussionTest.setTopic(topic);
        discussionTest.setPosts(posts);

        //Discussion discussion = discussionMapper.mapContentBeanToEntity(discussionTest);

        //discussionController.createDiscussion(discussionTest);

        Long discussionId = discussionService.saveDiscussion(discussionTest);
        post.setDiscussion(discussionTest);
        Long postId = postService.savePost(post);

        /*assertEquals("First Discussion", discussionService.getDiscussionByTitle("First Discussion").get(0).getText());
        assertEquals("Discussion Text 1", discussionService.getDiscussionByTitle("First Discussion").get(0).getTitle());
        assertEquals(topic.getTitle(), discussionService.getDiscussionByTitle("First Discussion").get(0).getTitle());

        //To check if the first added post is in the discussion that we have created
        assertEquals("Discussion Text 1", postService.getPostsByDiscussionId(discussionService.getDiscussionByTitle("First Discussion").get(0).getDiscussionId()).get(0).getText());*/

        assertEquals(discussionId, discussionService.getDiscussionById(discussionId).getDiscussionId());
        assertEquals("Discussion Text 1", discussionService.getDiscussionById(discussionId).getText());
        assertEquals("First Discussion", discussionService.getDiscussionById(discussionId).getTitle());
        assertEquals(topic.getTitle(), discussionService.getDiscussionById(discussionId).getTopic().getTitle());

        //To check if the first added post is in the discussion that we have created
        assertEquals(postId, postService.getPostsByDiscussionId(discussionId).get(0).getPostId());
        this.provider.rollback();
    }

    @Test
    public void deleteDiscussionTest() {
        this.provider.begin();

        Topic topic = topicService.getTopicById(27L);

        Post post = new Post();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(policyMaker);

        List<Post> posts = new ArrayList<>();

        Discussion discussionTest = new Discussion();
        discussionTest.setDiscussionId(null);
        discussionTest.setText("Discussion Text 1");
        discussionTest.setTimestamp(new Date());
        discussionTest.setTitle("First Discussion");
        discussionTest.setTopic(topic);
        discussionTest.setPosts(posts);

        Long discussionId = discussionService.saveDiscussion(discussionTest);
        post.setDiscussion(discussionTest);
        Long postId = postService.savePost(post);

        assertEquals(discussionId, discussionService.getDiscussionById(discussionId).getDiscussionId());
        assertEquals("Discussion Text 1", discussionService.getDiscussionById(discussionId).getText());
        assertEquals("First Discussion", discussionService.getDiscussionById(discussionId).getTitle());
        assertEquals(topic.getTitle(), discussionService.getDiscussionById(discussionId).getTopic().getTitle());

        //To check if the first added post is in the discussion that we have created
        assertEquals(postId, postService.getPostsByDiscussionId(discussionId).get(0).getPostId());

        discussionService.deleteDiscussion(discussionTest);

        assertEquals(0, discussionService.getDiscussionsByTopicId(topic.getTopicId()).size());
        this.provider.rollback();
    }

    @Test
    public void updateDiscussionTest() {
        this.provider.begin();

        Topic topic = topicService.getTopicById(27L);

        Post post = new Post();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(policyMaker);

        List<Post> posts = new ArrayList<>();

        Discussion discussionTest = new Discussion();
        discussionTest.setDiscussionId(null);
        discussionTest.setText("Discussion Text 1");
        discussionTest.setTimestamp(new Date());
        discussionTest.setTitle("First Discussion");
        discussionTest.setTopic(topic);
        discussionTest.setPosts(posts);

        Long discussionId = discussionService.saveDiscussion(discussionTest);
        post.setDiscussion(discussionTest);
        Long postId = postService.savePost(post);

        assertEquals(discussionId, discussionService.getDiscussionById(discussionId).getDiscussionId());
        assertEquals("Discussion Text 1", discussionService.getDiscussionById(discussionId).getText());
        assertEquals("First Discussion", discussionService.getDiscussionById(discussionId).getTitle());
        assertEquals(topic.getTitle(), discussionService.getDiscussionById(discussionId).getTopic().getTitle());

        //To check if the first added post is in the discussion that we have created
        assertEquals(postId, postService.getPostsByDiscussionId(discussionId).get(0).getPostId());

        discussionTest.setTitle("New Title");

        discussionService.saveDiscussion(discussionTest);

        assertNotEquals("First Discussion", discussionService.getDiscussionById(discussionId).getTitle());
        assertEquals("New Title", discussionService.getDiscussionByTitle("New Title").get(0).getTitle());

        this.provider.rollback();
    }

    @Test
    public void retrieveDiscussionByPolicyMakerTest() {
        this.provider.begin();

        Topic topic = topicService.getTopicById(27L);

        Post post = new Post();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(policyMaker);

        List<Post> posts = new ArrayList<>();

        Discussion discussionTest = new Discussion();
        discussionTest.setDiscussionId(null);
        discussionTest.setText("Discussion Text 1");
        discussionTest.setTimestamp(new Date());
        discussionTest.setTitle("First Discussion");
        discussionTest.setTopic(topic);
        discussionTest.setPosts(posts);

        Long discussionId = discussionService.saveDiscussion(discussionTest);
        post.setDiscussion(discussionTest);
        Long postId = postService.savePost(post);

        assertEquals(discussionId, discussionService.getDiscussionByPolicyMaker(userService.getUserByMail("mail").getPolicyMakerID()).get(0).getDiscussionId());

        this.provider.rollback();
    }

    @Test
    public void retrieveMostActiveDiscussionTest() {
        this.provider.begin();

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

        Post post2 = new Post();
        post2.setStatus(1);
        post2.setText("Post Text 2");
        post2.setTimestamp(new Date());
        post2.setCreator(policyMaker);
        post2.setDiscussion(discussionTest);

        Long postId2 = postService.savePost(post2);

        List<Post> posts2 = new ArrayList<>();

        Discussion discussionTest2 = new Discussion();
        discussionTest2.setText("Discussion Text 2");
        discussionTest2.setTimestamp(new Date());
        discussionTest2.setTitle("Second Discussion");
        discussionTest2.setTopic(topic);
        discussionTest2.setPosts(posts2);

        Long discussionId2 = discussionService.saveDiscussion(discussionTest);

        Post post3 = new Post();
        post3.setPostId(null);
        post3.setStatus(1);
        post3.setText("Post Text 1");
        post3.setTimestamp(new Date());
        post3.setCreator(policyMaker);
        post3.setDiscussion(discussionTest2);

        //Long postId3 = postService.savePost(post3);

        assertEquals(discussionId, discussionService.getMostActiveDiscussions(2).get(0).getDiscussionId());
        //assertEquals(discussionId2, discussionService.getMostActiveDiscussions(2).get(1).getDiscussionId());

        this.provider.rollback();
    }

    @Test
    public void retrieveDiscussionFollowersTest() {
        this.provider.begin();

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
        user1.setName("name1");
        user1.setSurname("surname1");
        user1.setAreaOfResidence("area1");
        user1.setDateOfBirth(new java.util.Date());
        user1.setMail("mail1");

        userService.createUser(user1);

        User user2 = new User();
        user2.setName("name2");
        user2.setSurname("surname2");
        user2.setAreaOfResidence("area2");
        user2.setDateOfBirth(new java.util.Date());
        user2.setMail("mail2");

        userService.createUser(user2);

        Post post2 = new Post();
        post2.setStatus(0);
        post2.setText("Post Text 3");
        post2.setTimestamp(new Date());
        post2.setCreator(user1);
        post2.setDiscussion(discussionTest);

        Long postId2 = postService.savePost(post2);

        Post post3 = new Post();
        post3.setPostId(null);
        post3.setStatus(1);
        post3.setText("Post Text 2");
        post3.setTimestamp(new Date());
        post3.setCreator(user2);
        post3.setDiscussion(discussionTest);

        Long postId3 = postService.savePost(post3);

        List<User> usersFollowers = discussionService.getDiscussionFollowers(discussionService.getDiscussionByPolicyMaker(userService.getUserByMail("mail").getPolicyMakerID()).get(0).getDiscussionId());

        assertEquals(3, usersFollowers.size());

        //The first user is the Policy maker.
        assertEquals(userService.getUserByMail("mail").getPolicyMakerID(), usersFollowers.get(0).getPolicyMakerID());

        this.provider.rollback();
    }

    @Test
    public void tryToDeleteDiscussionNotPresentTest() {
        this.provider.begin();
        assertThrows(Exception.class, () -> discussionController.deleteDiscussion(0L));
        this.provider.rollback();
    }

    @Test
    public void tryToRetrieveADiscussionByAPolicyMakerThatHasNotCreatedDiscussionsTest() {
        this.provider.begin();
        assertThrows(Exception.class, () -> discussionController.getDiscussionsByPolicyMaker("tryThis"));
        this.provider.rollback();
    }
}
