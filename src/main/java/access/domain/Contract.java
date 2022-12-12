package access.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Contract {
    private long id;
    private long provider_id;
    private long store_id;
    private Date date_contract;
}
