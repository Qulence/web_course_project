package servlets;

import dao.ExhibitTypeDao;
import domain.ExhibitType;
import jdbcUtilities.JDBCConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/exhibitType")
public class ExhibitTypeServlet extends HttpServlet {

    private JDBCConnectionUtil jdbcConnectionUtil = new JDBCConnectionUtil(
            "postgresql",
            "127.0.0.1",
            5432,
            "Museum",
            "postgres",
            "1234");
    private ExhibitTypeDao exhibitTypeDao = new ExhibitTypeDao(jdbcConnectionUtil);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nGET ExhibitTypeServlet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nPOST ExhibitTypeServlet");

        String action =
                req.getParameter("buttonClicked") == null ? "render" : req.getParameter("buttonClicked");
        long exhibitTypeId = -1;
        try {
            exhibitTypeId = Long.parseLong(req.getParameter("exhibitTypeId"));
        } catch (NumberFormatException e) {
            exhibitTypeId = -1;
        }
        if (action.equals("save")) {
            HttpSession session = req.getSession(false);
            session.setAttribute("targetTable", "exhibitType");
            session.setAttribute("action", "save");
            resp.sendRedirect("/dataActions");
        } else if (action.equals("update")) {
            if (exhibitTypeId != -1) {
                HttpSession session = req.getSession(false);
                session.setAttribute("targetTable", "exhibitType");
                session.setAttribute("action", "update");
                session.setAttribute("entityId", exhibitTypeId);
                Optional<ExhibitType> exhibitType = exhibitTypeDao.getById(exhibitTypeId);
                session.setAttribute("exhibitTypeName", exhibitType.get().getName());
                resp.sendRedirect("/dataActions");
            } else
                action = "render";
        } else if (action.equals("delete")) {
            if (exhibitTypeId != -1)
                exhibitTypeDao.deleteById(exhibitTypeId);
            action = "render";
        }
        if (action.equals("render")) {
            Iterable<ExhibitType> exhibitTypes = exhibitTypeDao.getAll();
            req.setAttribute("exhibitTypes", exhibitTypes);
            getServletContext().getRequestDispatcher("/exhibitType.jsp").forward(req, resp);
        }
    }

}