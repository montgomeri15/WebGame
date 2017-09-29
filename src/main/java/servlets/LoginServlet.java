package servlets;

import database.Constructor;
import database.DbManager;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    DbManager db = new DbManager();
    public static Connection connection;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("name", "");

        response.getWriter().println(PageGenerator.instance().getPage("login.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        String name = request.getParameter("name");
        response.setContentType("text/html;charset=utf-8");

        if (name.equals(null) || name.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("name", name.equals(null) ? "" : name);

        /***Считываем введенные данные***/
        String login = request.getParameter("name");
        String password = request.getParameter("password");

        /***Подключение к БД***/
        try {
            connection = db.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /***Считывание таблицы***/
        List<Constructor> list = null;
        try {
            list = db.readTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean a = false;  //Переменная для проверки

        for (int i = 0; i<list.size(); i++){
            String dbNames = list.get(i).getName().toString();
            String dbPasswords = list.get(i).getPass().toString();

            if (login.equals(dbNames) && password.equals(dbPasswords)){
                a = true;
            }
        }

        if (a==true){
            response.getWriter().println(PageGenerator.instance().getPage("postlogin.html", pageVariables));
        }else {
            response.getWriter().println(PageGenerator.instance().getPage("notlogin.html", pageVariables));
        }
    }
}
