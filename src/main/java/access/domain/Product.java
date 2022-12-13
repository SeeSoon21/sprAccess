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

    public enum fieldLabel {
        id, provider_id, department_id, price, quantity, expiration_date, delivery_date
    }
}
