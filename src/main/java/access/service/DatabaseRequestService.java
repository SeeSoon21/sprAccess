package access.service;

import access.domain.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static access.service.HelpRecordService.convertEnumFieldToStringArrayList;

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

    /************************************* SELECT ALL ************************************/
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
                    contract.setStore_id(set.getInt("store_id"));
                    contract.setDate_contract(set.getDate("date_contract"));

                    list.add(contract);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //не закрываем, потому что должно быть открыто, пока активна сессия
                //DbUtil.close(set);
                //DbUtil.close(statement);
                //DbUtil.close(connection);
            }
        }

        return list;
    }

    public LinkedList<Department> selectAllDepartment() {
        String sql = "SELECT * FROM department;";
        LinkedList<Department> list = new LinkedList<>();
        Department department = null;
        ResultSet set = null;

        if (connection != null) {
            System.out.println("Соединение прошло успешно");
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    //если расположить создание объекта не в цикле -- в лист пойдет одна и та же ссылка на объект
                    department = new Department();

                    department.setId(set.getInt("id"));
                    department.setStore_id(set.getInt("store_id"));
                    department.setName(set.getString("name"));
                    department.setManager_number(set.getInt("manager_number"));

                    list.add(department);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public LinkedList<Employee> selectAllEmployee() {
        String sql = "SELECT * FROM employee;";
        LinkedList<Employee> list = new LinkedList<>();
        Employee employee = null;
        ResultSet set = null;

        if (connection != null) {
            System.out.println("Соединение прошло успешно");
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    //если расположить создание объекта не в цикле -- в лист пойдет одна и та же ссылка на объект
                    employee = new Employee();

                    employee.setId(set.getInt("id"));
                    employee.setName(set.getString("name"));
                    employee.setSurname(set.getString("surname"));
                    employee.setPatronymic(set.getString("surname"));
                    employee.setAddress(set.getString("address"));
                    employee.setGender(set.getString("gender"));
                    employee.setMarital_status(set.getString("marital_status"));
                    employee.setBirthday(set.getDate("birthday"));
                    employee.setDepartment_id(set.getInt("department_id"));
                    employee.setStore_id(set.getInt("store_id"));

                    list.add(employee);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public LinkedList<Product> selectAllProduct() {
        String sql = "SELECT * FROM product;";
        LinkedList<Product> list = new LinkedList<>();
        Product product = null;
        ResultSet set = null;

        if (connection != null) {
            System.out.println("Соединение прошло успешно");
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    //если расположить создание объекта не в цикле -- в лист пойдет одна и та же ссылка на объект
                    product = new Product();

                    product.setId(set.getInt("id"));
                    product.setProvider_id(set.getInt("provider_id"));
                    product.setDepartment_id(set.getInt("department_id"));
                    product.setStore_id(set.getInt("store_id"));
                    product.setPrice(set.getInt("price"));
                    product.setQuantity(set.getInt("quantity"));
                    product.setExpiration_date(set.getInt("expiration_date"));
                    product.setDelivery_date(set.getDate("delivery_date"));

                    list.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public LinkedList<Provider> selectAllProvider() {
        String sql = "SELECT * FROM provider;";
        LinkedList<Provider> list = new LinkedList<>();
        Provider provider = null;
        ResultSet set = null;

        if (connection != null) {
            System.out.println("Соединение прошло успешно");
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    //если расположить создание объекта не в цикле -- в лист пойдет одна и та же ссылка на объект
                    provider = new Provider();

                    provider.setId(set.getInt("id"));
                    provider.setName(set.getString("name"));
                    provider.setAddress(set.getString("address"));

                    list.add(provider);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public LinkedList<Store> selectAllStore() {
        String sql = "SELECT * FROM store;";
        LinkedList<Store> list = new LinkedList<>();
        Store store = null;
        ResultSet set = null;

        if (connection != null) {
            System.out.println("Соединение прошло успешно");
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    //если расположить создание объекта не в цикле -- в лист пойдет одна и та же ссылка на объект
                    store = new Store();

                    store.setId(set.getInt("id"));
                    store.setSpecialization(set.getString("specialization"));
                    store.setINN(set.getString("INN"));
                    store.setAddress(set.getString("address"));
                    store.setDirector_number(set.getInt("director_number"));

                    list.add(store);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

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
            case "department" -> {
                return selectDepartmentRecord(id);
            }
            case "employee" -> {
                return selectEmployeeRecord(id);
            }
            case "product" -> {
                return selectProductRecord(id);
            }
            case "provider" -> {
                return selectProviderRecord(id);
            }
            case "store" -> {
                return selectStoreRecord(id);
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

    public Department selectDepartmentRecord(String id) {
        String sql = String.format("SELECT * FROM department where id = %s;", id);
        ResultSet set;
        Department department = new Department();

        if (connection != null) {
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    department.setId(set.getInt("id"));
                    department.setStore_id(set.getInt("store_id"));
                    department.setName(set.getString("name"));
                    department.setManager_number(set.getInt("manager_number"));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        return department;
    }

    public Employee selectEmployeeRecord(String id) {
        String sql = String.format("SELECT * FROM employee where id = %s;", id);
        ResultSet set;
        Employee employee = new Employee();

        if (connection != null) {
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    employee.setId(set.getInt("id"));
                    employee.setName(set.getString("name"));
                    employee.setSurname(set.getString("surname"));
                    employee.setPatronymic(set.getString("surname"));
                    employee.setAddress(set.getString("address"));
                    employee.setGender(set.getString("gender"));
                    employee.setMarital_status(set.getString("marital_status"));
                    employee.setBirthday(set.getDate("birthday"));
                    employee.setDepartment_id(set.getInt("department_id"));
                    employee.setStore_id(set.getInt("store_id"));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        return employee;
    }

    public Product selectProductRecord(String id) {
        String sql = String.format("SELECT * FROM product where id = %s;", id);
        ResultSet set;
        Product product = new Product();

        if (connection != null) {
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    product.setId(set.getInt("id"));
                    product.setProvider_id(set.getInt("provider_id"));
                    product.setDepartment_id(set.getInt("department_id"));
                    product.setStore_id(set.getInt("store_id"));
                    product.setPrice(set.getInt("price"));
                    product.setQuantity(set.getInt("quantity"));
                    product.setExpiration_date(set.getInt("expiration_date"));
                    product.setDelivery_date(set.getDate("delivery_date"));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        return product;
    }

    public Provider selectProviderRecord(String id) {
        String sql = String.format("SELECT * FROM provider where id = %s;", id);
        ResultSet set;
        Provider provider = new Provider();

        if (connection != null) {
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    provider.setId(set.getInt("id"));
                    provider.setName(set.getString("name"));
                    provider.setAddress(set.getString("address"));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        return provider;
    }

    public Provider selectStoreRecord(String id) {
        String sql = String.format("SELECT * FROM store where id = %s;", id);
        ResultSet set;
        Provider provider = new Provider();

        if (connection != null) {
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    provider.setId(set.getInt("id"));
                    provider.setName(set.getString("name"));
                    provider.setAddress(set.getString("address"));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        return provider;
    }

    /******************************************SELECT BY VALUES***********************************************/
    public String selectByValues(ArrayList<String> values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (var str : values) {
            stringBuilder.append(str).append(", ");
        }

        //разбиваем строку на массив, чтобы вычленить оттуда имя класса и поля, которые нужно вывести
        String[] arrayValues = stringBuilder.toString().split(", ");
        String className = arrayValues[0];
        System.out.println("className" + className);

        //подготавливаем поля, которые будут в с
        StringBuilder resultValues = new StringBuilder();
        for (int i = 1; i < arrayValues.length; i++) {
            resultValues.append(arrayValues[i]).append(", ");
        }
        System.out.println("result-str:" + resultValues);

        String result = resultValues.substring(0, resultValues.length() - 2);
        System.out.println("getted VALUES:" + result);

        String sql = String.format("SELECT %s FROM %s;", result, className);
        System.out.println("sql:" + sql);

        ResultSet set;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();


        //лист, в котором будем хранить мэп, чтобы вывести сразу несколько записей
        LinkedList<LinkedHashMap<String, String>> linkedList = new LinkedList<>();


        if (connection != null) {
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while (set.next()) {
                    for (int i = 1; i < arrayValues.length; i++) {
                        LinkedHashMap<String, String> mapValues = new LinkedHashMap<>();
                        System.out.println("arrayValues[i]:" + arrayValues[i]);
                        mapValues.put(arrayValues[i], String.valueOf(set.getString(arrayValues[i])));
                        linkedList.add(mapValues);
                    }
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        /*for (var key: mapValues.keySet()) {
            System.out.println("key:" + key + ", value:" + mapValues.get(key));
        }*/
        String json = gson.toJson(linkedList);
        System.out.println("json:" + json);

        return json;
    }


    /**
     * По клиентскому запросу выдаёт данные из БД
     *
     * @param values - значения, пришедшие с клиентской стороны(класс + запрос)
     */
    public String selectByParameter(ArrayList<String> values) {
        String className = values.get(0);
        String userQuery = values.get(1);
        String answerJson = null;

        //classname & values from bd
        //ArrayList<HashMap<String, String>> commonList = new ArrayList<>();
        //object from dynamically-define class
        ArrayList<Object> objectList = new ArrayList<>();

        String sql = String.format("SELECT * from %s where %s;", className, userQuery);
        ArrayList<String> arrayList = convertEnumFieldToStringArrayList(className);

        //объект, гибко принимающий в себя значения полей из БД
        Object mainObject = null;

        if (connection != null) {
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                //перебираем каждый объект resultSet из запроса
                //после чего присваиваем добавляем в мапу два значение: имя поля & val из бд
                while (resultSet.next()) {
                    ArrayList<String> tempValuesFromDB = new ArrayList<>();
                    for (var val : arrayList) {
                        /*HashMap<String, String> valuesFromBD = new HashMap<>();
                        valuesFromBD.put(val, resultSet.getString(val));
                        commonList.add(valuesFromBD);*/
                        tempValuesFromDB.add(resultSet.getString(val));
                    }
                    mainObject = HelpRecordService.getObjectByClassname(className, tempValuesFromDB);
                    objectList.add(mainObject);
                }
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                answerJson = gson.toJson(objectList);
                System.out.println("полученные с БД значения: " + answerJson);

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return answerJson;
    }

    /****************************************** UPDATE ******************************************************
     /**
     * Изменение записи в таблице contract по id-шнику
     * @param className -- название таблицы
     */
    public void updateContractRecord(String className, String id,
                                     String provider_id, String store_id, String date_contract) {
        String sql = String.format("UPDATE %s SET provider_id=?, store_id=?, date_contract=? WHERE id = %s;",
                className, id);
        System.out.println("update: " + className + ", " + id + ", " + provider_id + ", " + store_id + ", " + date_contract);
        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(provider_id));
                preparedStatement.setInt(2, Integer.parseInt(store_id));
                preparedStatement.setDate(3, Date.valueOf(date_contract));

                preparedStatement.executeUpdate();
                System.out.println("Запись обновлена!");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void updateDepartmentRecord(String className, String id,
                                       String store_id, String name, String manager_number) {
        String sql = String.format("UPDATE %s SET store_id=?, name=?, manager_number=? WHERE id = %s;",
                className, id);
        System.out.println("update: " + className + ", " + id + ", " + store_id + ", " + name + ", " + manager_number);
        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(store_id));
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, Integer.parseInt(manager_number));

                preparedStatement.executeUpdate();
                System.out.println("Запись обновлена!");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void updateEmployeeRecord(String className, String id,
                                     String name, String surname, String patronymic,
                                     String address, String gender, String marital_status,
                                     String birthday, String department_id, String store_id) {
        String sql = String.format(
                "UPDATE %s SET name=?, surname=?, patronymic=?, " +
                        "address=?, gender=?, marital_status=?, " +
                        "birthday=?, department_id=?, store_id=? " +
                        "WHERE id = %s;",
                className, id);
        System.out.println("update: " + className + ", " + id + ", " + name + ", " + surname + ", " + patronymic);
        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, patronymic);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, gender);
                preparedStatement.setString(6, marital_status);
                preparedStatement.setDate(7, Date.valueOf(birthday));
                preparedStatement.setInt(8, Integer.parseInt(department_id));
                preparedStatement.setInt(9, Integer.parseInt(store_id));

                preparedStatement.executeUpdate();
                System.out.println("Запись обновлена!");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void updateProductRecord(String className, String id,
                                    String provider_id, String department_id, String store_id, String price,
                                    String quantity, String expiration_date, String delivery_date) {
        String sql = String.format(
                "UPDATE %s SET provider_id=?, department_id=?, store_id=?, price=?, " +
                        "quantity=?, expiration_date=?, delivery_date=? " +
                        "WHERE id = %s;",
                className, id);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(provider_id));
                preparedStatement.setInt(2, Integer.parseInt(department_id));
                preparedStatement.setInt(3, Integer.parseInt(store_id));
                preparedStatement.setFloat(4, Float.parseFloat(price));
                preparedStatement.setInt(5, Integer.parseInt(quantity));
                preparedStatement.setInt(6, Integer.parseInt(expiration_date));
                preparedStatement.setDate(7, Date.valueOf(delivery_date));

                preparedStatement.executeUpdate();
                System.out.println("Запись обновлена!");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void updateProviderRecord(String className, String id,
                                     String name, String address) {
        String sql = String.format(
                "UPDATE %s SET name=?, address=? WHERE id = %s;",
                className, id);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);

                preparedStatement.executeUpdate();
                System.out.println("Запись обновлена!");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void updateStoreRecord(String className, String id, String name, String specialization,
                                  String INN, String address, String director_number) {
        String sql = String.format(
                "UPDATE %s SET name=?, specialization=?, INN=?, " +
                        "address=?, director_number=? WHERE id = %s;",
                className, id);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, specialization);
                preparedStatement.setString(3, INN);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, director_number);

                preparedStatement.executeUpdate();
                System.out.println("Запись обновлена!");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }


    /******************************************** INSERT ************************************************/


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

    public void insertDepartmentRecord(String className, String store_id, String name, String manager_number) {
        String sql = String.format("INSERT INTO %s(store_id, name, manager_number) VALUES(?,?,?)", className);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(store_id));
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, Integer.parseInt(manager_number));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertEmployeeRecord(String className, String name, String surname, String patronymic,
                                     String address, String gender, String marital_status, String birthday,
                                     String department_id, String store_id) {
        String sql = String.format("INSERT INTO %s(name, surname, patronymic, address, gender, " +
                "marital_status, birthday, department_id, store_id) VALUES(?,?,?,?,?,?,?,?,?)", className);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, patronymic);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, gender);
                preparedStatement.setString(6, marital_status);
                preparedStatement.setDate(7, Date.valueOf(birthday));
                preparedStatement.setInt(8, Integer.parseInt(department_id));
                preparedStatement.setInt(9, Integer.parseInt(store_id));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertProductRecord(String className, String provider_id, String department_id, String store_id,
                                    String price, String quantity, String expiration_date, String delivery_date) {
        String sql = String.format("INSERT INTO %s(provider_id, department_id, store_id, price, quantity, " +
                "expiration_date, delivery_date) VALUES(?,?,?,?,?,?,?)", className);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(provider_id));
                preparedStatement.setInt(2, Integer.parseInt(department_id));
                preparedStatement.setInt(3, Integer.parseInt(store_id));
                preparedStatement.setFloat(4, Float.parseFloat(price));
                preparedStatement.setInt(5, Integer.parseInt(quantity));
                preparedStatement.setInt(6, Integer.parseInt(expiration_date));
                preparedStatement.setDate(7, Date.valueOf(delivery_date));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertProviderRecord(String className, String name, String address) {
        String sql = String.format("INSERT INTO %s(name, address) VALUES(?,?)", className);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertStoreRecord(String className, String name, String specialization,
                                  String INN, String address, String director_number) {
        String sql = String.format("INSERT INTO %s(name, specialization, INN, address, director_number) " +
                "VALUES(?,?,?,?,?)", className);

        if (connection != null) {
            try {
                System.out.println("Connection is not null, запись готова к обновлению");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, specialization);
                preparedStatement.setString(3, INN);
                preparedStatement.setString(4, address);
                preparedStatement.setInt(5, Integer.parseInt(director_number));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteRecord(String className, String fieldName, String fieldValue) {
        String sql = String.format("delete from %s where %s=%s", className, fieldName, fieldValue);
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();

                System.out.println("Запись успешно удалена!");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }


}
