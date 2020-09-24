package servlets;

import dao.ExhibitDao;
import dao.ExhibitTypeDao;
import dao.MuseumDao;
import domain.*;
import jdbcUtilities.JDBCConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/exhibit")
public class ExhibitServlet extends HttpServlet {

    private JDBCConnectionUtil jdbcConnectionUtil = new JDBCConnectionUtil(
            "postgresql",
            "127.0.0.1",
            5432,
            "Museum",
            "postgres",
            "1234");
    private ExhibitDao exhibitDao = new ExhibitDao(jdbcConnectionUtil);
    private ExhibitTypeDao exhibitTypeDao = new ExhibitTypeDao(jdbcConnectionUtil);
    private MuseumDao museumDao = new MuseumDao(jdbcConnectionUtil);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nGET ExhibitServlet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nPOST ExhibitServlet");

        String action =
                req.getParameter("buttonClicked") == null ? "render" : req.getParameter("buttonClicked");
        long exhibitId = -1;
        try {
            exhibitId = Long.parseLong(req.getParameter("exhibitId"));
        } catch (NumberFormatException e) {
            exhibitId = -1;
        }
        if (action.equals("save")) {
            HttpSession session = req.getSession(false);
            session.setAttribute("targetTable", "exhibit");
            session.setAttribute("action", "save");
            resp.sendRedirect("/dataActions");
        } else if (action.equals("update")) {
            if (exhibitId != -1) {
                HttpSession session = req.getSession(false);
                session.setAttribute("targetTable", "exhibit");
                session.setAttribute("action", "update");
                session.setAttribute("entityId", exhibitId);
                Optional<Exhibit> exhibit = exhibitDao.getById(exhibitId);
                session.setAttribute("exhibitName", exhibit.get().getName());
                session.setAttribute("exhibitMuseumId", exhibit.get().getMuseum().getId());
                session.setAttribute("exhibitMuseumName", exhibit.get().getMuseum().getName());
                session.setAttribute("exhibitTypeId", exhibit.get().getExhibitType().getId());
                session.setAttribute("exhibitTypeName", exhibit.get().getExhibitType().getName());
                session.setAttribute("exhibitOnExposition", exhibit.get().isOnExposition());
                resp.sendRedirect("/dataActions");
            } else
                action = "render";
        } else if (action.equals("delete")) {
            if (exhibitId != -1)
                exhibitDao.deleteById(exhibitId);
            action = "render";
        }
        if (action.equals("render")) {
            Iterable<Exhibit> exhibits = exhibitDao.getAll();
            req.setAttribute("exhibits", exhibits);
            getServletContext().getRequestDispatcher("/exhibit.jsp").forward(req, resp);
        }
    }

}

/*
 String buttonClicked =
                req.getParameter("buttonClicked") == null ? "" : req.getParameter("buttonClicked");
        long exhibitId = -1;
        try {
            exhibitId = Long.parseLong(req.getParameter("exhibitId"));
        } catch (NumberFormatException e) {
            exhibitId = -1;
        }
        String exhibitName = req.getParameter("exhibitName");
        String onExposition = req.getParameter("exhibitOnExposition");
        System.out.println(onExposition);
        boolean exhibitOnExposition = onExposition == null ? false : true;
        long museumId = -1;
        try {
            museumId = Long.parseLong(req.getParameter("museumId"));
        } catch (NumberFormatException e) {
            museumId = -1;
        }
        long exhibitTypeId = -1;
        try {
            exhibitTypeId = Long.parseLong(req.getParameter("exhibitTypeId"));
        } catch (NumberFormatException e) {
            exhibitTypeId = -1;
        }
        System.out.println("buttonClicked: " + buttonClicked);
        System.out.println("exhibitId: " + exhibitId);
        System.out.println("exhibitName: " + exhibitName);
        System.out.println("exhibitOnExposition: " + exhibitOnExposition);
        System.out.println("museumId: " + museumId);
        System.out.println("exhibitTypeId: " + exhibitTypeId);
        switch (buttonClicked) {
            case "save":
                exhibitDao.save(new Exhibit(
                        exhibitName,
                        exhibitOnExposition,
                        new Museum(
                                museumId,
                                "",
                                "",
                                "",
                                "",
                                new MuseumType(
                                        -1,
                                        "")),
                        new ExhibitType(
                                exhibitTypeId,
                                "")));
                break;
            case "update":
                if (exhibitId > 0)
                    exhibitDao.update(new Exhibit(
                            exhibitId,
                            exhibitName,
                            exhibitOnExposition,
                            new Museum(
                                    museumId,
                                    "",
                                    "",
                                    "",
                                    "",
                                    new MuseumType(
                                            -1,
                                            "")),
                            new ExhibitType(
                                    exhibitTypeId,
                                    "")));
                break;
            case "delete":
                if (exhibitId > 0)
                    exhibitDao.deleteById(exhibitId);
                break;
        }
        Iterable<Exhibit> exhibits = exhibitDao.getAll();
        Iterable<ExhibitType> exhibitTypes = exhibitTypeDao.getAll();
        Iterable<Museum> museums = museumDao.getAll();
        req.setAttribute("exhibits", exhibits);
        req.setAttribute("exhibitTypes", exhibitTypes);
        req.setAttribute("museums", museums);
        getServletContext().getRequestDispatcher("/exhibit.jsp").forward(req, resp);
* */