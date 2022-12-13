package access.service;

import access.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;

@Service
public class HelpRecordService {

    public static ArrayList<?> getStringFieldsOfClass(String className) {
        switch (className.toLowerCase()) {
            case "contract" -> { return new ArrayList<>(EnumSet.allOf(Contract.fieldLabel.class)); }
            case "department" -> { return new ArrayList<>(EnumSet.allOf(Department.fieldLabel.class)); }
            case "employee" -> { return new ArrayList<>(EnumSet.allOf(Employee.fieldLabel.class)); }
            case "product" -> { return new ArrayList<>(EnumSet.allOf(Product.fieldLabel.class)); }
            case "provider" -> { return new ArrayList<>(EnumSet.allOf(Provider.fieldLabel.class)); }
            case "store" -> { return new ArrayList<>(EnumSet.allOf(Store.fieldLabel.class)); }
        }
        return null;
    }
}
