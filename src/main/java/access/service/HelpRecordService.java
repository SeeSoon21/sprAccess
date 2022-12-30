package access.service;

import access.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

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

    public static ArrayList<String> convertEnumFieldToStringArrayList(String className) {
        ArrayList<String> arrayList = new ArrayList<>();
        className = upperFirstSymbol(className);

        try {
            List<Field> list = Arrays.stream(Class.forName("access.domain." + className).getDeclaredFields()).toList();
            System.out.println("размер листа: "+ list.size());
            for (var field : list) {
                System.out.println("FIELDNAME: "+ field.getName());
                arrayList.add(field.getName());
            }
        } catch(ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return arrayList;
    }


    /**
     * введем в верхний регистр первый символ, чтобы правильно провести соответствие с классом
     * @param className - имя класса в нижнем регистре
     */
    private static String upperFirstSymbol(String className) {
        char[] tempClassName = className.toCharArray();
        for (int i = 0; i <= 1; i++) {
            tempClassName[0] = className.substring(0, 1).toUpperCase().toCharArray()[0];
        }
        className = String.valueOf(tempClassName);

        return className;
    }

    /**
     * вернём экземпляр с заполненными свойствами
     */
    public static Object getObjectByClassname(String className, ArrayList<String> fieldsValues) {
        switch (className) {
            case "contract" -> {
                Contract contract = new Contract();

                String id = fieldsValues.get(0);
                String provider_id = fieldsValues.get(1);
                String store_id = fieldsValues.get(2);
                String date_contract = fieldsValues.get(3);

                contract.setData(id, provider_id, store_id, date_contract);
                return contract;
            }
            /*case "department" -> {
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
            }*/
        }
        return null;
    }
}
