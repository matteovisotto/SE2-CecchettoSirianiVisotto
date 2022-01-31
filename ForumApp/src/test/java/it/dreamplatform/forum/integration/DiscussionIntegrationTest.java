package it.dreamplatform.forum.integration;

import it.dreamplatform.forum.EntityManagerProvider;
import it.dreamplatform.forum.bean.DiscussionBean;
import it.dreamplatform.forum.bean.DiscussionContentBean;
import it.dreamplatform.forum.bean.PostBean;
import it.dreamplatform.forum.bean.PublicUserBean;
import it.dreamplatform.forum.controller.DiscussionController;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.entities.Post;
import it.dreamplatform.forum.entities.Topic;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.mapper.DiscussionMapper;
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

    private User user;
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

        user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setAreaOfResidence("area");
        user.setDateOfBirth(new java.util.Date());
        user.setMail("mail");
        user.setPolicyMakerID("policy");

        /*topic = new Topic();
        topic.setTitle("First topic");
        topic.setTimestamp(new Date());*/

        this.provider.em().persist(user);
        //this.provider.em().persist(topic);
    }

    @After
    public void close() {
        this.provider.em().remove(user);
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
        post.setCreator(user);

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

    @Test
    public void deleteDiscussionTest() {
        this.provider.begin();

        Topic topic = topicService.getTopicById(27L);

        Post post = new Post();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(user);

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
    public void UpdateDiscussionTest() {
        this.provider.begin();

        Topic topic = topicService.getTopicById(27L);

        Post post = new Post();
        post.setStatus(1);
        post.setText("Discussion Text 1");
        post.setTimestamp(new Date());
        post.setCreator(user);

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
        assertEquals("New Title", discussionService.getDiscussionById(discussionId).getTitle());

        this.provider.rollback();
    }
}
