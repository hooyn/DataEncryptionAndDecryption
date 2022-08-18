package hooyn.rsa.controller.request;

import lombok.Getter;

@Getter
public class DataEncryptionRequest {
    private String rsaKey;
    private String data;
}
