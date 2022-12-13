package access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * Service-методы класса предназначены, чтобы возращать объекты, используемые при
 * работе с веб-сокетами
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
            case "contract" -> {return databaseRequestService.selectContractRecord(id);}
        }

        return null;
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
