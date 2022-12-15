package access.service;

import access.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;

@Service
public class HelpRecordService {

    //функция оказалась максимально бесполезной потому что названия всех полей пойдут вместе с данными Gson-объекта
    public static ArrayList<Enum<?>> getStringFieldsOfClass(String className) {
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

    public static HashMap<String, Object> fieldsNamesAndValues(ArrayList<Enum<?>> fieldsNames, ArrayList<Object> fieldsValues) {
        int length = fieldsNames.size();
        HashMap<String, Object> hashMap = new HashMap<>();
        for (int i = 0; i < length; i++) {
            hashMap.put(fieldsNames.get(i).toString(), fieldsValues.get(i));
        }

        return hashMap;
    }
}
