package hooyn.rsa.controller;

import lombok.Getter;

@Getter
public class DataDecryptionRequest {
    private String encryptedAESKey;
    private String encryptedData;
}
