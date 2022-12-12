package access.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Product {
    private long id;
    private long provider_id;
    private long department_id;
    private float price;
    private long quantity;
    private long expiration_date;
    private Date delivery_date;
}
