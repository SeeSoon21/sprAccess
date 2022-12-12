package access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class DomainSelector {
    @Autowired
    private DatabaseRequestService databaseRequestService = new DatabaseRequestService();

    public LinkedList<?> defineClassToSelectAll(String className) {

        System.out.println("Мы в функции defineClass");
        System.out.println("CLASSNAME: " + className);
        switch (className) {
            case "contract": {return databaseRequestService.selectAllContract();}
        }

        return null;
    }
}
