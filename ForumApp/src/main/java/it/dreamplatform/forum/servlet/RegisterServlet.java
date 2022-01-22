package it.dreamplatform.forum.servlet;

import it.dreamplatform.forum.bean.UserBean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(getServletContext());
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("registerUser") == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "You cannot access this page before accessing SSO page");
            return;
        }
        UserBean user = (UserBean) req.getSession().getAttribute("registerUser") ;
        String path = "templates/register";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("user", user);
        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = (String) req.getParameter("name");
        if (name == null || name.isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing name value");
            return;
        }
        String surname = (String) req.getParameter("surname");
        if (surname == null || surname.isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing surname value");
            return;
        }
        Date birthdate = null;
        try {
            birthdate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("dateOfBirth"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (birthdate == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing birthdate value");
            return;
        }
        String areaOfResidence = (String) req.getParameter("areaOfResidence");
        if (areaOfResidence == null || areaOfResidence.isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing areaOfResidence value");
            return;
        }
        String mail = (String) req.getParameter("mail");
        if (mail == null || mail.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing mail value");
            return;
        }
        String policyMakerID = (String) req.getParameter("policyMakerID");
        if (policyMakerID == null || policyMakerID.isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing policyMakerID value");
            return;
        }
        UserBean user = new UserBean();
        user.setUserId(1L);
        user.setName(name);
        user.setSurname(surname);
        user.setAreaOfResidence(areaOfResidence);
        user.setMail(mail);
        user.setDateOfBirth(new Date(Calendar.getInstance().getTime().getTime()));
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setPolicyMakerID(policyMakerID);
        
    }
}
