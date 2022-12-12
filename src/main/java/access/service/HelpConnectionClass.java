package access.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

/**
 * Класс, устанавливающий соединение с базой данных
 */
@Service
public class HelpConnectionClass {
    //@Value("${spring.datasource.url}")
    private final String url = "jdbc:mysql://localhost:3306/access_migration?serverTimezone=UTC";
    //@Value("${spring.datasource.username}")
    private String username = "dbuser";
    //@Value("${spring.datasource.password}")
    private String password = "11afamet";
    //@Value("${spring.datasource.driver-class-name}")
    private String driver = "com.mysql.cj.jdbc.Driver";

    //мы должны возвратить преобразованный connection, у которого
    //открыто соединение с бд
    public  Connection getConnection() {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("url: " + url);

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        }

        return null;
    }


}
