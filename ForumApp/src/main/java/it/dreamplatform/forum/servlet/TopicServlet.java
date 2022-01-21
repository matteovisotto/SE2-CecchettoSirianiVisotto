package it.dreamplatform.forum.servlet;

import it.dreamplatform.forum.bean.TopicBean;
import it.dreamplatform.forum.bean.UserBean;
import it.dreamplatform.forum.controller.TopicController;
import it.dreamplatform.forum.entities.Topic;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.services.TopicService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/topic/*")
public class TopicServlet extends HttpServlet {

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
        if(pathInfo == null || pathInfo.equals("/")){
            resp.sendRedirect(req.getContextPath()+"/");
            return;
        }
        Long topicId;
        try{
            topicId = Long.parseLong(pathInfo.replace("/",""));
        } catch (Exception e){
            System.out.println("Error parsing topic id");
            resp.sendRedirect(req.getContextPath()+"/");
            return;
        }

        String path = "templates/topic";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("topicId", topicId);
        if(req.getSession().getAttribute("user")!=null){
            ctx.setVariable("user", req.getSession().getAttribute("user"));
            ctx.setVariable("isPolicyMaker", !(((UserBean) req.getSession().getAttribute("user")).getPolicyMakerID()==null));
        }
        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

}
