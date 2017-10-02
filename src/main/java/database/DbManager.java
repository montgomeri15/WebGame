package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    List<Constructor> usersCollection = new ArrayList<>();  //Коллекция, принимающая данные через существующую

    /***Подключение к БД***/
    public static Connection getConnection() throws ClassNotFoundException, SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/location", "root", "root");
        return connection;
    }

    /*** Читаем и добавляем в коллекцию***/
    public List<Constructor> readTable() throws SQLException {

        usersCollection.clear();  //Очищаем коллекцию перед чтением

        String name;
        String password;

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM `table`");

        while (rs.next()) {  //Должно происходить строго в цикле

            name = rs.getString(1);
            password = rs.getString(2);

            Constructor constructor =  new Constructor();
            constructor.setName(name);
            constructor.setPass(password);

            usersCollection.add(constructor);
        }
        return usersCollection;
    }
}