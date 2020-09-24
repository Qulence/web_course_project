package servlets;

import dao.MuseumDao;
import dao.MuseumTypeDao;
import domain.Museum;
import jdbcUtilities.JDBCConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/museum")
public class MuseumServlet extends HttpServlet {

    private JDBCConnectionUtil jdbcConnectionUtil = new JDBCConnectionUtil(
            "postgresql",
            "127.0.0.1",
            5432,
            "Museum",
            "postgres",
            "1234");
    private MuseumDao museumDao = new MuseumDao(jdbcConnectionUtil);
    private MuseumTypeDao museumTypeDao = new MuseumTypeDao(jdbcConnectionUtil);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nGET MuseumServlet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nPOST MuseumServlet");

        String action =
                req.getParameter("buttonClicked") == null ? "render" : req.getParameter("buttonClicked");
        long museumId = -1;
        try {
            museumId = Long.parseLong(req.getParameter("museumId"));
        } catch (NumberFormatException e) {
            museumId = -1;
        }
        if (action.equals("save")) {
            HttpSession session = req.getSession(false);
            session.setAttribute("targetTable", "museum");
            session.setAttribute("action", "save");
            resp.sendRedirect("/dataActions");
        } else if (action.equals("update")) {
            if (museumId != -1) {
                HttpSession session = req.getSession(false);
                session.setAttribute("targetTable", "museum");
                session.setAttribute("action", "update");
                session.setAttribute("entityId", museumId);
                Optional<Museum> museum = museumDao.getById(museumId);
                session.setAttribute("museumName", museum.get().getName());
                session.setAttribute("museumAddress", museum.get().getAddress());
                session.setAttribute("museumFullNameOfTheHead", museum.get().getFullNameOfTheHead());
                session.setAttribute("museumPhoneNumber", museum.get().getPhoneNumber());
                session.setAttribute("museumTypeId", museum.get().getMuseumType().getId());
                session.setAttribute("museumTypeName", museum.get().getMuseumType().getName());
                resp.sendRedirect("/dataActions");
            } else
                action = "render";
        } else if (action.equals("delete")) {
            if (museumId != -1)
                museumDao.deleteById(museumId);
            action = "render";
        }
        if (action.equals("render")) {
            Iterable<Museum> museums = museumDao.getAll();
            req.setAttribute("museums", museums);
            getServletContext().getRequestDispatcher("/museum.jsp").forward(req, resp);
        }
    }

}