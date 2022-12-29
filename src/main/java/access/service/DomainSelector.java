package access.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Service-методы класса предназначены, чтобы возращать объекты, используемые при
 * работе с веб-сокетами.
 * этот класс -- ещё один уровень абстракции -- лежит между сервисом базы данных и веб-сокетов.
 */
@Service
public class DomainSelector {
    @Autowired
    private DatabaseRequestService databaseRequestService = new DatabaseRequestService();

    //возвращает все строки, связанные с классом
    public LinkedList<?> defineClassToSelectAll(String className) {

        System.out.println("CLASSNAME: " + className);
        switch (className) {
            case "contract": {
                //databaseRequestService.addNewContractRecord("1");
                return databaseRequestService.selectAllContract();
            }
            case "department": {
                return databaseRequestService.selectAllDepartment();
            }
            case "employee": {
                return databaseRequestService.selectAllEmployee();
            }
            case "product": {
                return databaseRequestService.selectAllProduct();
            }
            case "provider": {
                return databaseRequestService.selectAllProvider();
            }
            case "store": {
                return databaseRequestService.selectAllStore();
            }
        }

        return null;
    }

    /** Возвращает запись по входящему id
     * @param className имя класса
     * @param id записи
     * @return запись по id
     */
    public Object getRecordById(String className, String id) {
        switch (className) {
            case "contract", "department", "employee", "product", "provider", "store" -> {
                return databaseRequestService.getSelectById(className, id);
            }
        }

        return null;
    }

    public void postUpdate(String[] fields) {
        String className = fields[0];
        String id = fields[1];

        switch (className) {
            case "contract" -> {
                String provider_id = fields[2];
                String store_id = fields[3];
                String date_contract = fields[4];
                databaseRequestService.updateContractRecord(className, id, provider_id, store_id, date_contract);
            }
            case "department" -> {
                String store_id = fields[2];
                String name = fields[3];
                String manager_number = fields[4];
                databaseRequestService.updateDepartmentRecord(className, id, store_id, name, manager_number);
            }
            case "employee" -> {
                String name = fields[2];
                String surname = fields[3];
                String patronymic = fields[4];
                String address = fields[5];
                String gender = fields[6];
                String marital_status = fields[7];
                String birthday = fields[8];
                String department_id = fields[9];
                String store_id = fields[10];
                databaseRequestService.updateEmployeeRecord(className, id, name, surname, patronymic,
                        address, gender, marital_status, birthday, department_id, store_id);
            }

            case "product" -> {
                String provider_id = fields[2];
                String department_id = fields[3];
                String store_id = fields[4];
                String price = fields[5];
                String quantity = fields[6];
                String expiration_date = fields[7];
                String delivery_date = fields[8];
                databaseRequestService.updateProductRecord(className, id, provider_id,
                        department_id, store_id, price, quantity, expiration_date, delivery_date);
            }

            case "provider" -> {
                String name = fields[2];
                String address = fields[3];
                databaseRequestService.updateProviderRecord(className, id, name, address);
            }

            case "store" -> {
                String name = fields[2];
                String specialization = fields[3];
                String INN = fields[4];
                String address = fields[5];
                String director_number = fields[6];
                databaseRequestService.updateStoreRecord(className, id, name, specialization, INN,
                        address,director_number);
            }
        }
    }

    public String postSelectByValues(ArrayList<String> arrayList) {
        return databaseRequestService.selectByValues(arrayList);
    }

    public void postInsert(String[] fields) {
        String className = fields[0];
        switch (className) {
            case "contract" -> {
                String provider_id = fields[1];
                String store_id = fields[2];
                String date_contract = fields[3];
                databaseRequestService.insertContractRecord(className, provider_id, store_id, date_contract);
            }
            case "department" -> {
                String store_id = fields[1];
                String name = fields[2];
                String manager_number = fields[3];
                databaseRequestService.insertDepartmentRecord(className, store_id, name, manager_number);
            }
            case "employee" -> {
                String name = fields[1];
                String surname = fields[2];
                String patronymic = fields[3];
                String address = fields[4];
                String gender = fields[5];
                String marital_status = fields[6];
                String birthday = fields[7];
                String department_id = fields[8];
                String store_id = fields[9];
                databaseRequestService.insertEmployeeRecord(className,  name, surname, patronymic,
                        address, gender, marital_status, birthday, department_id, store_id);
            }

            case "product" -> {
                String provider_id = fields[1];
                String department_id = fields[2];
                String store_id = fields[3];
                String price = fields[4];
                String quantity = fields[5];
                String expiration_date = fields[6];
                String delivery_date = fields[7];
                databaseRequestService.insertProductRecord(className, provider_id,
                        department_id, store_id, price, quantity, expiration_date, delivery_date);
            }

            case "provider" -> {
                String name = fields[1];
                String address = fields[2];
                databaseRequestService.insertProviderRecord(className, name, address);
            }

            case "store" -> {
                String name = fields[1];
                String specialization = fields[2];
                String INN = fields[3];
                String address = fields[4];
                String director_number = fields[5];
                databaseRequestService.insertStoreRecord(className, name, specialization, INN,
                        address, director_number);
            }
        }
    }

    public void postDelete(String className, String fieldName, String fieldValue) {
        databaseRequestService.deleteRecord(className, fieldName, fieldValue);
    }

    //создает полное имя класса по названию класса
    public static String getClassNameByString(String className) {
        switch (className) {
            case "contract" -> { return "access.domain.Contract"; }
            case "department" -> { return "access.domain.Department"; }
            case "employee" -> { return "access.domain.Employee"; }
            case "product" -> { return "access.domain.Product"; }
            case "provider" -> { return "access.domain.Provider"; }
            case "store" -> { return "access.domain.Store"; }
        }

        return null;
    }

    //возвращает экземпляр
}
