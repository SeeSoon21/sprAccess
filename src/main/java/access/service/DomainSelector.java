package access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Service;

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

        System.out.println("Мы в функции defineClass");
        System.out.println("CLASSNAME: " + className);
        switch (className) {
            case "contract": {
                //databaseRequestService.addNewContractRecord("1");
                return databaseRequestService.selectAllContract();
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
        }
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
        }
    }

    public void postDelete(String className, String id) {
        databaseRequestService.deleteRecord(className, id);
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
