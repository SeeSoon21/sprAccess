package access.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Contract {
    private long id;
    private long provider_id;
    private long store_id;
    private Date date_contract;

    public enum fieldLabel {
        id, provider_id, store_id, date_contract;
    }

    public void setData(String id, String provider_id, String store_id, String date_contract) {
        this.id = Integer.parseInt(id);
        this.provider_id = Integer.parseInt(provider_id);
        this.provider_id = Integer.parseInt(store_id);
        this.provider_id = Integer.parseInt(date_contract);
    }
}
