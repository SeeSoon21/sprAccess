package access.service;

import access.domain.Contract;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                while (set.next()) {
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
     *
     * @param className -- класс(сущность)
     * @param id        -- номер записи
     * @return object -- экземпляр класса, собранный по строке из БД
     */
    public Object getSelectById(String className, String id) {

        switch (className) {
            case "contract" -> {
                return selectContractRecord(id);
            }

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
                while (set.next()) {
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

    /**
     * Изменение записи в таблице contract по id-шнику
     *
     * @param className -- название таблицы
     */
    public void updateContractRecord(String className, String id,
                                     String provider_id, String store_id, String date_contract) {
        String sql = String.format("UPDATE %s SET id=?, provider_id=?, store_id=?, date_contract=? WHERE id = %s;",
                className, id);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(id));
                preparedStatement.setInt(2, Integer.parseInt(provider_id));
                preparedStatement.setInt(3, Integer.parseInt(store_id));
                preparedStatement.setDate(4, Date.valueOf(date_contract));

                preparedStatement.executeUpdate();
                System.out.println("Запись обновлена!");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void addToContract(String id, String providerId, String storeId, String date_contract) {
        String sql = "INSERT INTO contract VALUES(?,?,?,?)";
        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(id));
                preparedStatement.setInt(2, Integer.parseInt(providerId));
                preparedStatement.setInt(3, Integer.parseInt(storeId));
                preparedStatement.setDate(4, Date.valueOf(date_contract));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void insertContractRecord(String className, String providerId, String storeId, String dateContract) {
        String sql = String.format("INSERT INTO %s(provider_id, store_id, date_contract) VALUES(?,?,?)", className);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(providerId));
                preparedStatement.setInt(2, Integer.parseInt(storeId));
                preparedStatement.setDate(3, Date.valueOf(dateContract));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteRecord(String className, String id) {
        String sql = String.format("delete from %s where id=%s", className, id);
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.executeUpdate();
            } catch(SQLException exc) {
                exc.printStackTrace();
            }
        }
    }
}
