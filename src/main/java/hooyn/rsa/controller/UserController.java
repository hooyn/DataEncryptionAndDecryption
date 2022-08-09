package hooyn.rsa.controller;

import hooyn.rsa.core.AESUtil;
import hooyn.rsa.core.RSAUtil;
import hooyn.rsa.entity.User;
import hooyn.rsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/join")
    @Transactional
    public String join(@RequestBody JoinRequest request){
        User user = new User(request.getEmail(), request.getPassword());

        User user1 = userRepository.save(user);
        return user1.toString();
    }

    @PostMapping("/login222")
    public Map<String, String> login(@RequestBody LoginRequest request) throws NoSuchAlgorithmException {
        User byEmail = userRepository.findByEmail(request.getEmail());
        Map<String, String> response = new HashMap<>();

        // RSA에서 필요한 공개키와 개인키를 가져옵니다.
        KeyPair keyPair = RSAUtil.genRSAKeyPair();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("publicKey = " + publicKey);

        // 공개키를 Base64 인코딩한 문자일을 만듭니다.
        byte[] bytePublicKey = publicKey.getEncoded();
        String base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey);

        if(request.getPassword().equals(byEmail.getPassword())){
            response.put("RSAKey", base64PublicKey);
            response.put("message", "로그인 성공");
            return response;
        } else {
            response.put("RSAKey", null);
            response.put("message", "로그인 실패");
            return response;
        }
    }

    //client
    @PostMapping("/data/en")
    public Map<String, String> dataEncryption(@RequestBody DataEncryptionRequest request) throws Exception {
        Map<String, String> response = new HashMap<>();
        String aesKey = RandomStringUtils.randomAlphanumeric(32);

        String encrypt = AESUtil.encrypt(request.getData(), aesKey);

        PublicKey publicKey = RSAUtil.getPublicKeyFromBase64String(request.getRsaKey());
        String encryptAESKey = RSAUtil.getEncryptText(aesKey, publicKey);
        response.put("EncryptedAESKey", encryptAESKey);
        response.put("EncryptedData", encrypt);

        return response;
    }

    @PostMapping("/data/de")
    public Map<String, String> dataDecryption(@RequestBody DataDecryptionRequest request) throws Exception {
        Map<String, String> response = new HashMap<>();

        String decryptAesKey = RSAUtil.getDecryptText(request.getEncryptedAESKey(), RSAUtil.genRSAKeyPair().getPrivate());
        String data = AESUtil.decrypt(request.getEncryptedData(), decryptAesKey);

        response.put("data", data);

        return response;
    }
}
