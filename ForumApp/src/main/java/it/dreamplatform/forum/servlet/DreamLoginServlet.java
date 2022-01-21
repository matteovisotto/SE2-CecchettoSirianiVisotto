package it.dreamplatform.forum.servlet;

import it.dreamplatform.forum.bean.UserBean;
import it.dreamplatform.forum.controller.UserController;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.services.UserService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements registration and login for user authenticated by Shibboleth SP
 */
@WebServlet("/dream/login")
public class DreamLoginServlet extends HttpServlet {
    @Inject
    UserController userController;

    private final String[] requiredParameters = {"sn", "givenName", "mail", "areaOfResidence", "policyMakerID", "dateOfBirth"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Mail parameter is required
        if(req.getAttribute("mail") == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "A mail address must be provided");
            return;
        }

        UserBean user = null;
        user = userController.searchUser((String) req.getAttribute("mail"));

        if(user != null){
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath()+"/");
            return;
        }

        Map<String, String> availableParams = new HashMap<>();
        for(String param : requiredParameters){
            if(req.getAttribute(param) != null){
                availableParams.put(param, (String) req.getAttribute(param));
            }
        }

        user = new UserBean();
        user.setAreaOfResidence(availableParams.get("areaOfResidence"));
        user.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        user.setName(availableParams.get("givenName"));
        user.setMail(availableParams.get("mail"));
        user.setPolicyMakerID(availableParams.get("policyMakerID"));
        user.setSurname(availableParams.get("sn"));
        try{
            user.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(availableParams.get("dateOfBirth")));
        } catch (Exception e){
            user.setDateOfBirth(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }

        if(availableParams.size() == 6) {
            Long id = userController.createUser(user);
            user.setUserId(id);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath()+"/");
        } else {
            req.getSession().setAttribute("registerUser", user);
            resp.sendRedirect(req.getContextPath()+"/register");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
