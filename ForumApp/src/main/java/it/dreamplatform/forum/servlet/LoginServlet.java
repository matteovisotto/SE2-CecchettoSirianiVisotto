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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;


@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {
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
        String pathInfo = req.getPathInfo();
        if(pathInfo!=null && pathInfo.equals("/user")){
            System.out.println("In user");
            UserBean user = new UserBean();
            user.setUserId(4L);
            user.setName("Test");
            user.setSurname("User");
            user.setAreaOfResidence("Milano");
            user.setMail("tototia98@gmail.com");
            user.setDateOfBirth(new Date(Calendar.getInstance().getTime().getTime()));
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath()+"/");
            return;
        } else if(pathInfo!=null && pathInfo.equals("/policymaker")) {
            UserBean user = new UserBean();
            user.setUserId(3L);
            user.setName("Test");
            user.setSurname("PolicyMaker");
            user.setAreaOfResidence("Milano");
            user.setMail("matev1998@gmail.com");
            user.setDateOfBirth(new Date(Calendar.getInstance().getTime().getTime()));
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            user.setPolicyMakerID("thisisthepolicymakerid");
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath()+"/");
            return;
        }
        //Delete the part above
        String path = "templates/login";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath()+"/");
    }


}
