package access.service;

import access.domain.Contract;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;

@Component
public class DatabaseRequestService {

    //устанавливаем соединение
    private Connection connection;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

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
        String sql = "SELECT * FROM contract;";
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

    /**
     * определяем объект, выбранный для редактирования юзером
     * @param className -- класс(сущность)
     * @param id -- номер записи
     * @return object -- экземпляр класса, собранный по строке из БД
     */
    public Object getSelectById(String className, String id) {

        switch (className) {
            case "contract" -> { return selectContractRecord(id); }

        }

        return null;
    }


    /**
     * Возвращает запись таблицы contract по id
     */
    public Contract selectContractRecord(String id) {
        String sql = String.format("SELECT * FROM contract where id = %s;", id);
        ResultSet set;
        Contract contract = new Contract();

        if (connection != null) {
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while(set.next()) {
                    contract.setId(set.getInt("id"));
                    contract.setProvider_id(set.getInt("provider_id"));
                    contract.setStore_id(set.getInt("store_id"));
                    contract.setDate_contract(set.getDate("date_contract"));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
        System.out.println("CONTRACT: " + contract);

        return contract;
    }

    public void addToContract(long id, long providerId, long storeId, Date date) {
        String sql = "INSERT INTO contract VALUES(?,?,?,?)";
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
