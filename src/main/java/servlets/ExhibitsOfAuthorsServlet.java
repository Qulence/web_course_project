package servlets;

import dao.AuthorDao;
import domain.AuthorExhibit;
import jdbcUtilities.JDBCConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/exhibitsOfAuthors")
public class ExhibitsOfAuthorsServlet extends HttpServlet {

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
        System.out.println("\n\nGET ExhibitsOfAuthorsServlet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nPOST ExhibitsOfAuthorsServlet");

        String action =
                req.getParameter("buttonClicked") == null ? "render" : req.getParameter("buttonClicked");
        long authorDeleteId = -1;
        long exhibitDeleteId = -1;
        try {
            if (req.getParameter("ids") != null) {
                String[] ids = req.getParameter("ids").split("_");
                authorDeleteId = Long.parseLong(ids[0]);
                exhibitDeleteId = Long.parseLong(ids[1]);
            }
        } catch (NumberFormatException e) {
            authorDeleteId = -1;
            exhibitDeleteId = -1;
        }
        if (action.equals("save")) {
            HttpSession session = req.getSession(false);
            session.setAttribute("targetTable", "exhibitsOfAuthors");
            session.setAttribute("action", "save");
            resp.sendRedirect("/dataActions");
        } else if (action.equals("delete")) {
            if (authorDeleteId != -1 && exhibitDeleteId != -1)
                authorDao.detachExhibitFromAuthor(authorDeleteId, exhibitDeleteId);
            action = "render";
        }
        if (action.equals("render")) {
            Iterable<AuthorExhibit> authorExhibits = authorDao.getAllAuthorExhibits();
            req.setAttribute("authorExhibits", authorExhibits);
            getServletContext().getRequestDispatcher("/exhibitsOfAuthors.jsp").forward(req, resp);
        }
    }

}