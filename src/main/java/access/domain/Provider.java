package access.domain;

import lombok.Data;

@Data
public class Provider {
    private long id;
    private String name;
    private String address;

    public enum fieldLabel {
        id, name, address;
    }
}
