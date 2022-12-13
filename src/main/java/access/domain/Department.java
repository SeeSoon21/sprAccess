package access.domain;

import lombok.Data;

@Data
public class Department {
    private long id;
    private long store_id;
    private String name;
    private long manager_number;

    public enum fieldLabel {
        id, store_id, name, manager_number;
    }
}
