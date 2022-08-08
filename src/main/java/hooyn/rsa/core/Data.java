package hooyn.rsa.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@lombok.Data
public class Data {
    private String username;
    private String userid;

    public Data(String username, String userid) {
        this.username = username;
        this.userid = userid;
    }
}
