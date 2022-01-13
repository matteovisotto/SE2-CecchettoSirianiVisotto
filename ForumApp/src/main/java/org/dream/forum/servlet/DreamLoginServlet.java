package org.dream.forum.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dream/login")
public class DreamLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get attributes from shibboleth SP
        String uid = (String) req.getAttribute("uid");
        String surname = (String) req.getAttribute("sn");
        String name = (String) req.getAttribute("givenName");
        String commonName = (String) req.getAttribute("cn");
        String mail = (String) req.getAttribute("mail");
        String displayName = (String) req.getAttribute("displayName");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
