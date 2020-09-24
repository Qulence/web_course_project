package servlets;

import dao.AuthorDao;
import domain.Author;
import jdbcUtilities.JDBCConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {

    private JDBCConnectionUtil jdbcConnectionUtil = new JDBCConnectionUtil(
            "postgresql",
            "127.0.0.1",
            5432,
            "Museum",
            "postgres",
            "1234");
    private AuthorDao authorDao = new AuthorDao(jdbcConnectionUtil);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nGET AuthorServlet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nPOST AuthorServlet");

        String action =
                req.getParameter("buttonClicked") == null ? "render" : req.getParameter("buttonClicked");
        long authorId = -1;
        try {
            authorId = Long.parseLong(req.getParameter("authorId"));
        } catch (NumberFormatException e) {
            authorId = -1;
        }
        if (action.equals("save")) {
            HttpSession session = req.getSession(false);
            session.setAttribute("targetTable", "author");
            session.setAttribute("action", "save");
            resp.sendRedirect("/dataActions");
        } else if (action.equals("update")) {
            if (authorId != -1) {
                HttpSession session = req.getSession(false);
                session.setAttribute("targetTable", "author");
                session.setAttribute("action", "update");
                session.setAttribute("entityId", authorId);
                Optional<Author> author = authorDao.getById(authorId);
                session.setAttribute("authorFullName", author.get().getFullName());
                resp.sendRedirect("/dataActions");
            } else
                action = "render";
        } else if (action.equals("delete")) {
            if (authorId != -1)
                authorDao.deleteById(authorId);
            action = "render";
        }
        if (action.equals("render")) {
            Iterable<Author> authors = authorDao.getAll();
            req.setAttribute("authors", authors);
            getServletContext().getRequestDispatcher("/author.jsp").forward(req, resp);
        }
    }

}