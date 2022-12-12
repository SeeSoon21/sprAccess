package access.service;

import access.domain.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.LinkedList;

@Component
public class DatabaseRequestService {

    //устанавливаем соединение
    private Connection connection;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private String sql;

    //entity

    public DatabaseRequestService() {
        HelpConnectionClass helpConnectionClass = new HelpConnectionClass();
        connection = helpConnectionClass.getConnection();

        if (connection != null) {
            try {
                System.out.println("Соединение с БД установлено!");
                statement = connection.createStatement();
            } catch (SQLException exc) {
                System.out.println("При соединении с БД возникал ошибка");
                System.out.println(exc.getErrorCode());
            }
        }
    }

    public LinkedList<Contract> selectAllContract() {
        System.out.println("Мы в функции selectAllContract");
        sql = "SELECT * FROM contract;";
        LinkedList<Contract> list = new LinkedList<>();
        Contract contract = null;
        ResultSet set = null;

        if (connection != null) {
            System.out.println("Соединение прошло успешно");
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while(set.next()) {
                    //если расположить создание объекта не в цикле -- в лист пойдет одна и та же ссылка на объект
                    contract = new Contract();

                    contract.setId(set.getInt("id"));
                    contract.setProvider_id(set.getInt("provider_id"));
                    contract.setStore_id(set.getInt("provider_id"));
                    contract.setDate_contract(set.getDate("date_contract"));

                    System.out.println("contract: " + contract);
                    list.add(contract);
                }
            } catch (SQLException e) {
                System.out.println("При соденинении произошла ошибка(DBReqService)");
                e.printStackTrace();
            } finally {
                //не закрываем, потому что должно быть открыто, пока активна сессия
                //DbUtil.close(set);
                //DbUtil.close(statement);
                //DbUtil.close(connection);
            }
        } else {
            System.out.println("Соединение не было установлено(DBReqService)");
        }
        System.out.println("List(in logic): ");
        list.forEach(System.out::println);

        return list;
    }

    public void addToContract(long id, long providerId, long storeId, Date date) {
        sql = "INSERT INTO contract VALUES(?,?,?,?)";
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.setLong(1, id);
                preparedStatement.setLong(2, providerId);
                preparedStatement.setLong(3, storeId);
                preparedStatement.setDate(4, date);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
