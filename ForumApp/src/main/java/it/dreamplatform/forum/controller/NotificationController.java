package it.dreamplatform.forum.controller;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.services.DiscussionService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.List;

/**
 * This class contains all the controller used to send notification to the User who interacted with a Discussion.
 */
public class NotificationController {
    @Inject
    DiscussionService discussionService;

    /**
     * This function notifies the followers of a discussion by emailing them.
     * @param discussionId is the id of the selected Discussion.
     */
    public void notifyFollowers(Long discussionId) throws MailjetException, Exception {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        Discussion discussion = discussionService.getDiscussionById(discussionId);

        List<User> discussionFollowers = discussionService.getDiscussionFollowers(discussionId);
        JSONArray receivers = new JSONArray();
        for (User user : discussionFollowers) {
            receivers.put(new JSONObject()
                    .put("Email", user.getMail())
                    .put("Name", user.getName() + " " + user.getSurname()));
        }
        client = new MailjetClient("b6f77ce70757e21a3bf469064b4d1e4d", "3493a06a827721a61fd331945a863690", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "noreply@dreamplatform.it")
                                        .put("Name", "Dream Platform Forum"))
                                .put(Emailv31.Message.TO, receivers)
                                .put(Emailv31.Message.SUBJECT, "The discussion \""+discussion.getTitle()+"\"  was updated!")
                                .put(Emailv31.Message.TEXTPART, "Dear passenger 1, welcome to Mailjet! May the delivery force be with you!")
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href=\"https://www.mailjet.com/\">Mailjet</a>!</h3><br />May the delivery force be with you!")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}
