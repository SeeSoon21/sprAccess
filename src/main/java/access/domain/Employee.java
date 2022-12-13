package access.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Employee {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private String address;
    private String gender;
    private String marital_status;
    private Date birthday;
    private long department_id;
    private long store_id;

    public enum fieldLabel {
        id, name, surname, patronymic, address, gender, marital_status, birthday, department_id, store_id
    }
}
