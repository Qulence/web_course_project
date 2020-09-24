package servlets;

import dao.*;
import domain.*;
import jdbcUtilities.JDBCConnectionUtil;
import jspControls.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dataActions")
public class DataActionsServlet extends HttpServlet {

    private JDBCConnectionUtil jdbcConnectionUtil = new JDBCConnectionUtil(
            "postgresql",
            "127.0.0.1",
            5432,
            "Museum",
            "postgres",
            "1234");
    private AuthorDao authorDao = new AuthorDao(jdbcConnectionUtil);
    private ExhibitTypeDao exhibitTypeDao = new ExhibitTypeDao(jdbcConnectionUtil);
    private MuseumTypeDao museumTypeDao = new MuseumTypeDao(jdbcConnectionUtil);
    private MuseumDao museumDao = new MuseumDao(jdbcConnectionUtil);
    private ExhibitDao exhibitDao = new ExhibitDao(jdbcConnectionUtil);

    private String targetTable;
    private String action;
    private long entityId;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\nGET DataActionsServlet");
        HttpSession session = req.getSession(false);
        targetTable = (String) session.getAttribute("targetTable");
        action = (String) session.getAttribute("action");
        entityId = session.getAttribute("entityId") == null ?
                -1 : (Long) session.getAttribute("entityId");
        System.out.println("from: " + targetTable);

        List<DynamicInputControl> dynamicInputControls = new ArrayList<>();
        List<DynamicSelectControl> dynamicSelectControls = new ArrayList<>();
        List<DynamicCheckBoxControl> dynamicCheckBoxControls = new ArrayList<>();
        List<DynamicButtonControl> dynamicButtonControls = new ArrayList<>();
        switch (targetTable) {
            case "author":
                DynamicInputControl authorFullNameInputControl = new DynamicInputControl();
                authorFullNameInputControl.setId("authorFullName");
                authorFullNameInputControl.setName("authorFullName");
                authorFullNameInputControl.setLabelText("ФИО автора");
                authorFullNameInputControl.setPlaceholderText("Введите ФИО автора");
                DynamicButtonControl applyAuthorButtonControl = new DynamicButtonControl();
                switch (action) {
                    case "save":
                        applyAuthorButtonControl.setName("save");
                        applyAuthorButtonControl.setValue("save");
                        applyAuthorButtonControl.setButtonType(ButtonType.SUCCESS);
                        applyAuthorButtonControl.setText("Добавить");
                        break;
                    case "update":
                        authorFullNameInputControl.setValue((String) session.getAttribute("authorFullName"));
                        applyAuthorButtonControl.setName("update");
                        applyAuthorButtonControl.setValue("update");
                        applyAuthorButtonControl.setButtonType(ButtonType.PRIMARY);
                        applyAuthorButtonControl.setText("Изменить");
                        break;
                }
                dynamicInputControls.add(authorFullNameInputControl);
                dynamicButtonControls.add(applyAuthorButtonControl);
                break;
            case "exhibit":
                DynamicInputControl exhibitNameInputControl = new DynamicInputControl();
                exhibitNameInputControl.setId("exhibitName");
                exhibitNameInputControl.setName("exhibitName");
                exhibitNameInputControl.setLabelText("Название");
                exhibitNameInputControl.setPlaceholderText("Введите название");

                DynamicSelectControl museumSelectControl = new DynamicSelectControl();
                museumSelectControl.setId("museum");
                museumSelectControl.setName("museumId");
                Iterable<Museum> museums = museumDao.getAll();
                List<DynamicOptionControl> museumOptionControls = new ArrayList<>();
                if (action == "update")
                    museumOptionControls.add(
                            new DynamicOptionControl(String.valueOf(session.getAttribute("exhibitMuseumId")),
                                    (String) session.getAttribute("exhibitMuseumName")));
                for (Museum museum : museums) {
                    if (!museumOptionControls.contains((new DynamicOptionControl(
                            String.valueOf(museum.getId()), museum.getName())))) {
                        museumOptionControls.add(new DynamicOptionControl(
                                String.valueOf(museum.getId()), museum.getName()));
                    }
                }
                museumSelectControl.setDynamicOptionControls(museumOptionControls);
                museumSelectControl.setLabelText("Музей");
                DynamicSelectControl exhibitTypeSelectControl = new DynamicSelectControl();
                exhibitTypeSelectControl.setId("exhibitType");
                exhibitTypeSelectControl.setName("exhibitTypeId");
                Iterable<ExhibitType> exhibitTypes = exhibitTypeDao.getAll();
                List<DynamicOptionControl> exhibitTypeOptionControls = new ArrayList<>();
                if (action == "update")
                    exhibitTypeOptionControls.add(
                            new DynamicOptionControl(String.valueOf(session.getAttribute("exhibitTypeId")),
                                    (String) session.getAttribute("exhibitTypeName")));
                for (ExhibitType exhibitType : exhibitTypes) {
                    if (!museumOptionControls.contains((new DynamicOptionControl(
                            String.valueOf(exhibitType.getId()), exhibitType.getName())))) {
                        exhibitTypeOptionControls.add(new DynamicOptionControl(
                                String.valueOf(exhibitType.getId()), exhibitType.getName()));
                    }
                }
                exhibitTypeSelectControl.setDynamicOptionControls(exhibitTypeOptionControls);
                exhibitTypeSelectControl.setLabelText("Тип экспоната");
                DynamicButtonControl applyExhibitButtonControl = new DynamicButtonControl();
                DynamicCheckBoxControl exhibitOnExpositionCheckBoxControl = new DynamicCheckBoxControl();
                exhibitOnExpositionCheckBoxControl.setId("exhibitOnExposition");
                exhibitOnExpositionCheckBoxControl.setName("exhibitOnExposition");
                exhibitOnExpositionCheckBoxControl.setLabelText("Участвует в экспозиции");
                switch (action) {
                    case "save":
                        applyExhibitButtonControl.setName("save");
                        applyExhibitButtonControl.setValue("save");
                        applyExhibitButtonControl.setButtonType(ButtonType.SUCCESS);
                        applyExhibitButtonControl.setText("Добавить");
                        break;
                    case "update":
                        exhibitNameInputControl.setValue((String) session.getAttribute("exhibitName"));
                        if ((Boolean) session.getAttribute("exhibitOnExposition"))
                            exhibitOnExpositionCheckBoxControl.setState("checked");
                        else
                            exhibitOnExpositionCheckBoxControl.setState(null);
                        applyExhibitButtonControl.setName("update");
                        applyExhibitButtonControl.setValue("update");
                        applyExhibitButtonControl.setButtonType(ButtonType.PRIMARY);
                        applyExhibitButtonControl.setText("Изменить");
                        break;
                }
                dynamicInputControls.add(exhibitNameInputControl);
                dynamicSelectControls.add(museumSelectControl);
                dynamicSelectControls.add(exhibitTypeSelectControl);
                dynamicButtonControls.add(applyExhibitButtonControl);
                dynamicCheckBoxControls.add(exhibitOnExpositionCheckBoxControl);
                break;
            case "exhibitType":
                DynamicInputControl exhibitTypeNameInputControl = new DynamicInputControl();
                exhibitTypeNameInputControl.setId("exhibitTypeName");
                exhibitTypeNameInputControl.setName("exhibitTypeName");
                exhibitTypeNameInputControl.setLabelText("Тип экспоната");
                exhibitTypeNameInputControl.setPlaceholderText("Введите тип экспоната");
                DynamicButtonControl applyExhibitTypeButtonControl = new DynamicButtonControl();
                switch (action) {
                    case "save":
                        applyExhibitTypeButtonControl.setName("save");
                        applyExhibitTypeButtonControl.setValue("save");
                        applyExhibitTypeButtonControl.setButtonType(ButtonType.SUCCESS);
                        applyExhibitTypeButtonControl.setText("Добавить");
                        break;
                    case "update":
                        exhibitTypeNameInputControl.setValue((String) session.getAttribute("exhibitTypeName"));
                        applyExhibitTypeButtonControl.setName("update");
                        applyExhibitTypeButtonControl.setValue("update");
                        applyExhibitTypeButtonControl.setButtonType(ButtonType.PRIMARY);
                        applyExhibitTypeButtonControl.setText("Изменить");
                        break;
                }
                dynamicInputControls.add(exhibitTypeNameInputControl);
                dynamicButtonControls.add(applyExhibitTypeButtonControl);
                break;
            case "museum":
                DynamicInputControl museumNameInputControl = new DynamicInputControl();
                museumNameInputControl.setId("museumName");
                museumNameInputControl.setName("museumName");
                museumNameInputControl.setLabelText("Название");
                museumNameInputControl.setPlaceholderText("Введите название");
                DynamicInputControl museumAddressInputControl = new DynamicInputControl();
                museumAddressInputControl.setId("museumAddress");
                museumAddressInputControl.setName("museumAddress");
                museumAddressInputControl.setLabelText("Адрес");
                museumAddressInputControl.setPlaceholderText("Введите адрес");
                DynamicInputControl museumFullNameOfTheHeadInputControl = new DynamicInputControl();
                museumFullNameOfTheHeadInputControl.setId("museumFullNameOfHead");
                museumFullNameOfTheHeadInputControl.setName("museumFullNameOfHead");
                museumFullNameOfTheHeadInputControl.setLabelText("ФИО руководителя");
                museumFullNameOfTheHeadInputControl.setPlaceholderText("Введите ФИО руководителя");
                DynamicInputControl museumPhoneNumberInputControl = new DynamicInputControl();
                museumPhoneNumberInputControl.setId("museumPhoneNumber");
                museumPhoneNumberInputControl.setName("museumPhoneNumber");
                museumPhoneNumberInputControl.setLabelText("Номер телефона");
                museumPhoneNumberInputControl.setPlaceholderText("Введите номер телефона");
                DynamicSelectControl museumTypeSelectControl = new DynamicSelectControl();
                museumTypeSelectControl.setId("museumType");
                museumTypeSelectControl.setName("museumTypeId");
                Iterable<MuseumType> museumTypes = museumTypeDao.getAll();
                List<DynamicOptionControl> museumTypeOptionControls = new ArrayList<>();
                if (action == "update")
                    museumTypeOptionControls.add(
                            new DynamicOptionControl(String.valueOf(session.getAttribute("museumTypeId")),
                                    (String) session.getAttribute("museumTypeName")));
                for (MuseumType museumType : museumTypes) {
                    if (!museumTypeOptionControls.contains((new DynamicOptionControl(
                            String.valueOf(museumType.getId()), museumType.getName())))) {
                        museumTypeOptionControls.add(new DynamicOptionControl(
                                String.valueOf(museumType.getId()), museumType.getName()));
                    }
                }
                museumTypeSelectControl.setDynamicOptionControls(museumTypeOptionControls);
                museumTypeSelectControl.setLabelText("Тип музея");
                DynamicButtonControl applyMuseumButtonControl = new DynamicButtonControl();
                switch (action) {
                    case "save":
                        applyMuseumButtonControl.setName("save");
                        applyMuseumButtonControl.setValue("save");
                        applyMuseumButtonControl.setButtonType(ButtonType.SUCCESS);
                        applyMuseumButtonControl.setText("Добавить");
                        break;
                    case "update":
                        museumNameInputControl.setValue((String) session.getAttribute("museumName"));
                        museumAddressInputControl.setValue((String) session.getAttribute("museumAddress"));
                        museumFullNameOfTheHeadInputControl.setValue(
                                (String) session.getAttribute("museumFullNameOfTheHead"));
                        museumPhoneNumberInputControl.setValue(
                                (String) session.getAttribute("museumPhoneNumber"));
                        applyMuseumButtonControl.setName("update");
                        applyMuseumButtonControl.setValue("update");
                        applyMuseumButtonControl.setButtonType(ButtonType.PRIMARY);
                        applyMuseumButtonControl.setText("Изменить");
                        break;
                }
                dynamicInputControls.add(museumNameInputControl);
                dynamicInputControls.add(museumAddressInputControl);
                dynamicInputControls.add(museumFullNameOfTheHeadInputControl);
                dynamicInputControls.add(museumPhoneNumberInputControl);
                dynamicSelectControls.add(museumTypeSelectControl);
                dynamicButtonControls.add(applyMuseumButtonControl);
                break;
            case "museumType":
                DynamicInputControl museumTypeNameInputControl = new DynamicInputControl();
                museumTypeNameInputControl.setId("museumTypeName");
                museumTypeNameInputControl.setName("museumTypeName");
                museumTypeNameInputControl.setLabelText("Тип музея");
                museumTypeNameInputControl.setPlaceholderText("Введите тип музея");
                DynamicButtonControl applyMuseumTypeButtonControl = new DynamicButtonControl();
                switch (action) {
                    case "save":
                        applyMuseumTypeButtonControl.setName("save");
                        applyMuseumTypeButtonControl.setValue("save");
                        applyMuseumTypeButtonControl.setButtonType(ButtonType.SUCCESS);
                        applyMuseumTypeButtonControl.setText("Добавить");
                        break;
                    case "update":
                        museumTypeNameInputControl.setValue((String) session.getAttribute("museumTypeName"));
                        applyMuseumTypeButtonControl.setName("update");
                        applyMuseumTypeButtonControl.setValue("update");
                        applyMuseumTypeButtonControl.setButtonType(ButtonType.PRIMARY);
                        applyMuseumTypeButtonControl.setText("Изменить");
                        break;
                }
                dynamicInputControls.add(museumTypeNameInputControl);
                dynamicButtonControls.add(applyMuseumTypeButtonControl);
                break;
            case "exhibitsOfAuthors":
                DynamicSelectControl authorSelectControl = new DynamicSelectControl();
                authorSelectControl.setId("author");
                authorSelectControl.setName("authorId");
                Iterable<Author> authors = authorDao.getAll();
                List<DynamicOptionControl> authorOptionControls = new ArrayList<>();
                for (Author author : authors) {
                    authorOptionControls.add(new DynamicOptionControl(
                            String.valueOf(author.getId()), author.getFullName()));
                }
                authorSelectControl.setDynamicOptionControls(authorOptionControls);
                authorSelectControl.setLabelText("Автор");
                DynamicSelectControl exhibitSelectControl = new DynamicSelectControl();
                exhibitSelectControl.setId("exhibit");
                exhibitSelectControl.setName("exhibitId");
                Iterable<Exhibit> exhibits = exhibitDao.getAll();
                List<DynamicOptionControl> exhibitOptionControls = new ArrayList<>();
                for (Exhibit exhibit : exhibits) {
                    exhibitOptionControls.add(new DynamicOptionControl(
                            String.valueOf(exhibit.getId()), exhibit.getName()));
                }
                exhibitSelectControl.setDynamicOptionControls(exhibitOptionControls);
                exhibitSelectControl.setLabelText("Экспонат");
                dynamicSelectControls.add(authorSelectControl);
                dynamicSelectControls.add(exhibitSelectControl);
                DynamicButtonControl applyAuthorExhibitButtonControl = new DynamicButtonControl();
                applyAuthorExhibitButtonControl.setName("save");
                applyAuthorExhibitButtonControl.setValue("save");
                applyAuthorExhibitButtonControl.setButtonType(ButtonType.SUCCESS);
                applyAuthorExhibitButtonControl.setText("Добавить");
                dynamicButtonControls.add(applyAuthorExhibitButtonControl);
                break;
        }
        req.setAttribute("dynamicInputControls", dynamicInputControls);
        req.setAttribute("dynamicSelectControls", dynamicSelectControls);
        req.setAttribute("dynamicCheckBoxControls", dynamicCheckBoxControls);
        req.setAttribute("dynamicButtonControls", dynamicButtonControls);
        getServletContext().getRequestDispatcher("/dataActions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println("\n\nPOST DataActionsServlet");
        switch (targetTable) {
            case "author":
                if (action == "save")
                    authorDao.save(new Author(req.getParameter("authorFullName")));
                else if (action == "update")
                    authorDao.update(new Author(entityId, req.getParameter("authorFullName")));
                break;
            case "exhibit":
                String onExposition = req.getParameter("exhibitOnExposition");
                boolean exhibitOnExposition = onExposition == null ? false : true;
                if (action == "save")
                    exhibitDao.save(new Exhibit(
                            req.getParameter("exhibitName"),
                            exhibitOnExposition,
                            new Museum(
                                    Long.parseLong(req.getParameter("museumId")),
                                    "",
                                    "",
                                    "",
                                    "",
                                    new MuseumType(-1, "")),
                            new ExhibitType(
                                    Long.parseLong(req.getParameter("exhibitTypeId")),
                                    "")
                    ));
                else if (action == "update")
                    exhibitDao.update(new Exhibit(
                            entityId,
                            req.getParameter("exhibitName"),
                            exhibitOnExposition,
                            new Museum(
                                    Long.parseLong(req.getParameter("museumId")),
                                    "",
                                    "",
                                    "",
                                    "",
                                    new MuseumType(-1, "")),
                            new ExhibitType(
                                    Long.parseLong(req.getParameter("exhibitTypeId")),
                                    "")
                    ));
                break;
            case "exhibitType":
                if (action == "save")
                    exhibitTypeDao.save(new ExhibitType(req.getParameter("exhibitTypeName")));
                else if (action == "update")
                    exhibitTypeDao.update(new ExhibitType(entityId, req.getParameter("exhibitTypeName")));
                break;
            case "museum":
                if (action == "save")
                    museumDao.save(new Museum(
                            req.getParameter("museumName"),
                            req.getParameter("museumAddress"),
                            req.getParameter("museumFullNameOfHead"),
                            req.getParameter("museumPhoneNumber"),
                            new MuseumType(
                                    Long.parseLong(req.getParameter("museumTypeId")),
                                    ""
                            )));
                else if (action == "update")
                    museumDao.update(new Museum(
                            entityId,
                            req.getParameter("museumName"),
                            req.getParameter("museumAddress"),
                            req.getParameter("museumFullNameOfHead"),
                            req.getParameter("museumPhoneNumber"),
                            new MuseumType(
                                    Long.parseLong(req.getParameter("museumTypeId")),
                                    ""
                            )
                    ));
                break;
            case "museumType":
                if (action == "save")
                    museumTypeDao.save(new MuseumType(req.getParameter("museumTypeName")));
                else if (action == "update")
                    museumTypeDao.update(new MuseumType(entityId, req.getParameter("museumTypeName")));
                break;
            case "exhibitsOfAuthors":
                authorDao.attachExhibitToAuthor(
                        Long.parseLong(req.getParameter("authorId")),
                        Long.parseLong(req.getParameter("exhibitId")));
                break;
        }
        resp.sendRedirect("/" + targetTable);
    }

}