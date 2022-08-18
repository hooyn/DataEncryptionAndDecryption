package hooyn.rsa.controller.request;

import lombok.Getter;

@Getter
public class DataDecryptionRequest {
    private String encryptedAESKey;
    private String encryptedData;
}
