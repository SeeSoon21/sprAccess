package access.domain;

import lombok.Data;

@Data
public class Store {
    private long id;
    private String name;
    private String specialization;
    private String INN;
    private String address;
    private long director_number;
}
