package hooyn.rsa.controller;

import lombok.Getter;

@Getter
public class DataEncryptionRequest {
    private String rsaKey;
    private String data;
}
