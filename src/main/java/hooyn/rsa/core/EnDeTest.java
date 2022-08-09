package hooyn.rsa.core;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.*;
import java.util.Base64;

public class EnDeTest {
    public static void main(String[] args) throws Exception {
        //AES 키 생성
        String AESKey = RandomStringUtils.randomAlphanumeric(32);
        System.out.println("AESKey = " + AESKey);

        // RSA 키쌍을 생성합니다.
        KeyPair keyPair = RSAUtil.genRSAKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        Data member = new Data("Jung", "hoyun");

        String AESEncryptedText = AESUtil.encrypt(String.valueOf(member), AESKey);
        System.out.println("AESEncryptedText = " + AESEncryptedText);

//        // Base64 인코딩된 암호화 문자열 입니다.
//        String encrypted = RSAUtil.getEncryptText(AESKey, publicKey);
//        System.out.println("encrypted = " + encrypted);
//
//        // 복호화 합니다.
//        String decrypted = RSAUtil.getDecryptText(encrypted, privateKey);
//        System.out.println("decrypted = " + decrypted);

        // 공개키를 Base64 인코딩한 문자일을 만듭니다.
        byte[] bytePublicKey = publicKey.getEncoded();
        String base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
        System.out.println("Base64 Public Key : " + base64PublicKey);

        // 개인키를 Base64 인코딩한 문자열을 만듭니다.
        byte[] bytePrivateKey = privateKey.getEncoded();
        String base64PrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);
        System.out.println("Base64 Private Key : " + base64PrivateKey);

        // 문자열로부터 PrivateKey와 PublicKey를 얻습니다.
        PrivateKey prKey = RSAUtil.getPrivateKeyFromBase64String(base64PrivateKey);
        PublicKey puKey = RSAUtil.getPublicKeyFromBase64String(base64PublicKey);

        // 공개키로 암호화 합니다.
        String encrypted2 = RSAUtil.getEncryptText(AESKey, puKey);
        System.out.println("encrypted : " + encrypted2);

        // 복호화 합니다.
        String decrypted2 = RSAUtil.getDecryptText(encrypted2, prKey);
        System.out.println("decrypted : " + decrypted2);

        String result = AESUtil.decrypt(AESEncryptedText, decrypted2);

        System.out.println("result = " + result);
    }
}
