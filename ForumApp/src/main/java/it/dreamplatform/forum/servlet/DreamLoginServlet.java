package it.dreamplatform.forum.servlet;

import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.services.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@WebServlet("/dream/login")
public class DreamLoginServlet extends HttpServlet {
    @EJB(name = "it.dreamplatform.forum.services/UserService")
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get attributes from Shibboleth SP
        String uid = (String) req.getAttribute("uid");
        String surname = (String) req.getAttribute("sn");
        String name = (String) req.getAttribute("givenName");
        String commonName = (String) req.getAttribute("cn");
        String mail = (String) req.getAttribute("mail");
        String displayName = (String) req.getAttribute("displayName");
        String areaOfResidence = (String) req.getAttribute("areaOfResidence");
        String policyMakerID = (String) req.getAttribute("policyMakerID");
        String dateOfBirth = (String) req.getAttribute("dateOfBirth");
        String memberOf = (String) req.getAttribute("memberOf");

        if(mail == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "A mail address must be provided");
            return;
        }

        User user = null;
        user = userService.findByMail(mail);
        if(user == null){
            user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setMail(mail);
            user.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
            user.setAreaOfResidence(areaOfResidence);
            user.setPolicyMakerID(policyMakerID);
            try{
                user.setDateOfBirth(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth).getTime()));
            } catch (Exception e){
                user.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
            }
            Long id = userService.createUser(user);
            user.setUserId(id);
            req.getSession().setAttribute("user", user);
        } else {
            req.getSession().setAttribute("user", user);
        }
        resp.sendRedirect(req.getContextPath()+"/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
