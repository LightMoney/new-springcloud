package com.forezp.filter;

//import com.model.dto.UserDTO;
//import com.service.UserService;
//import com.service.impl.MyUserDetailsService;

import com.forezp.service.UserServiceDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Cipher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * 2019/10/29 3:21 PM
 *
 * @author: zhouximin
 * @Description:
 **/
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    UserServiceDetail userDetailsService;
    //    @Autowired
//    LoginProvider loginProvider;
//    @Autowired
//    UserService userService;
    public static String publicKeyValue = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhIMk0muSD/UbVcvCrwPp\n" +
            "PBr7lcKnuxmEo/IfL9NN4BfVwOkt+ghF7IvQRYKNng32yZzxRcklZbbjST0x8/Ze\n" +
            "aVF4cdn6TKMKveGQV+MwIsfpifcHyEvPiauufvDTLzCyohWUVlRVSzJAU9vYa35+\n" +
            "aAK9ALwuaKGPm7gM7mVhw7m0aiLFgmZ6W3NHulnaNOPVJb53ERq/XuRbK2YLf9o7\n" +
            "zrU3r0d0Ldv0bXCDWp8hfkFeyt8hUPBI3OhlvKsC+Foar63JcMHgmOXAmHZGF5Gm\n" +
            "WABHh8fH8HEW+gdtVukWUuMfOuTg9DKkiGO+1EM8ZE70XTUKKD2I2qST2b2k6dac\n" +
            "OwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 验证token并返回对应的用户信息，自行实现即可
//        UserDTO user = userService.check(request);
        Claims claims = null;
        try {


//            PublicKey publicKey = CertificateFactory.getInstance("X.509")
//                    .generateCertificate(resource.getInputStream())
//                    .getPublicKey();
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("fzp-jwt.jks"), "fzp123".toCharArray());
            PublicKey aPublic = keyStoreKeyFactory.getKeyPair("fzp-jwt").getPublic();

//本来采用直接读取公钥，一直报错，只好通过私钥 来获取公钥
//            PublicKey publicKey = CertificateFactory.getInstance("X.509")
//                    .generateCertificate(new ByteArrayInputStream(tt.getBytes()))
//                    .getPublicKey();

            try {
                claims = Jwts.parser().setSigningKey(aPublic)
                        .parseClaimsJws(request.getHeader("Authorization").replace("Bearer ", "")).getBody();
                if (claims != null) {
                    String mobile = "user.getMobile()";
                    String name = (String) claims.get("name");
                    if (mobile != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

                        if (userDetails != null) {
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            } catch (ExpiredJwtException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        chain.doFilter(request, response);
    }
}


