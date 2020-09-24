package servlets;

import dao.MuseumTypeDao;
import domain.MuseumType;
import jdbcUtilities.JDBCConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/museumType")
public class MuseumTypeServlet extends HttpServlet {

    private JDBCConnectionUtil jdbcConnectionUtil = new JDBCConnectionUtil(
            "postgresql",
            "127.0.0.1",
            5432,
            "Museum",
            "postgres",
            "1234");
    private MuseumTypeDao museumTypeDao = new MuseumTypeDao(jdbcConnectionUtil);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nGET MuseumTypeServlet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nPOST MuseumTypeServlet");

        String action =
                req.getParameter("buttonClicked") == null ? "render" : req.getParameter("buttonClicked");
        long museumTypeId = -1;
        try {
            museumTypeId = Long.parseLong(req.getParameter("museumTypeId"));
        } catch (NumberFormatException e) {
            museumTypeId = -1;
        }
        if (action.equals("save")) {
            HttpSession session = req.getSession(false);
            session.setAttribute("targetTable", "museumType");
            session.setAttribute("action", "save");
            resp.sendRedirect("/dataActions");
        } else if (action.equals("update")) {
            if (museumTypeId != -1) {
                HttpSession session = req.getSession(false);
                session.setAttribute("targetTable", "museumType");
                session.setAttribute("action", "update");
                session.setAttribute("entityId", museumTypeId);
                Optional<MuseumType> museumType = museumTypeDao.getById(museumTypeId);
                session.setAttribute("museumTypeName", museumType.get().getName());
                resp.sendRedirect("/dataActions");
            } else
                action = "render";
        } else if (action.equals("delete")) {
            if (museumTypeId != -1)
                museumTypeDao.deleteById(museumTypeId);
            action = "render";
        }
        if (action.equals("render")) {
            Iterable<MuseumType> museumTypes = museumTypeDao.getAll();
            req.setAttribute("museumTypes", museumTypes);
            getServletContext().getRequestDispatcher("/museumType.jsp").forward(req, resp);
        }
    }

}