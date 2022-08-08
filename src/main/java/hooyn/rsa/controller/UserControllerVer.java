package hooyn.rsa.controller;

import hooyn.rsa.core.RSAUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

@Controller
public class UserControllerVer {


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(HttpSession session, Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PrivateKey privateKey = RSAUtil.genRSAKeyPair().getPrivate();
        PublicKey publicKey = RSAUtil.genRSAKeyPair().getPublic();

        RSAPublicKeySpec publicKeySpec = KeyFactory.getInstance("RSA").getKeySpec(publicKey, RSAPublicKeySpec.class);
        String modulus = publicKeySpec.getModulus().toString(16);
        String exponent = publicKeySpec.getPublicExponent().toString(16);

        model.addAttribute("modulus", modulus);
        model.addAttribute("exponent", exponent);
        session.setAttribute("RSAPrivateKey",privateKey);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpSession session, RedirectAttributes ra) {
        System.out.println("email = " + user.getEmail());
        System.out.println("password = " + user.getPassword());

        // 개인키 취득
        PrivateKey key = (PrivateKey) session.getAttribute("RSAPrivateKey");
        System.out.println(key);

        // 아이디/비밀번호 복호화
        try {
            String decrypt_email = RSAUtil.getDecryptText(user.getEmail(), key);
            String decrypt_password = RSAUtil.getDecryptText(user.getPassword(), key);

            System.out.println(decrypt_email + " " +decrypt_password);

            // 복호화된 평문을 재설정
        } catch (Exception e) {
            ra.addFlashAttribute("resultMsg", "비정상 적인 접근 입니다.");
            return "redirect:/login";
        }

        // 로그인 로직 실행
        return "home";
    }
}
