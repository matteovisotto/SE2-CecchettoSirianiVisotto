package org.dream.forum.servlet;

import org.dream.forum.entities.User;
import org.dream.forum.services.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@WebServlet("/dream/login")
public class DreamLoginServlet extends HttpServlet {
    @EJB(name = "org.dream.forum.services/UserService")
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get attributes from shibboleth SP
        String uid = (String) req.getAttribute("uid");
        String surname = (String) req.getAttribute("sn");
        String name = (String) req.getAttribute("givenName");
        String commonName = (String) req.getAttribute("cn");
        String mail = (String) req.getAttribute("mail");
        String displayName = (String) req.getAttribute("displayName");

        User user = null;
        user = userService.findByMail(mail);
        if(user == null){
            user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setMail(mail);
            user.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
            user.setAreaOfResidence("Milano");
            user.setDateOfBirth(new Date(Calendar.getInstance().getTime().getTime()));
            Long id = userService.createUser(user);
            user.setUserId(id);
            req.getSession().setAttribute("user", user);
        } else {
            req.getSession().setAttribute("user", user);
        }
        resp.sendRedirect(getServletContext().getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
