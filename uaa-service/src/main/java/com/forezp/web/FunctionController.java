package com.forezp.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hasRole：
 *
 * 角色授权：授权代码，在我们返回的UserDetails的Authority需要加ROLE_前缀，Controller上使用时不要加前缀；
 *
 *  
 *
 * hasAuthority：
 *
 * 权限授权：用户自定义的权限，返回的UserDetails的Authority只要与这里匹配就可以，这里不需要加ROLE_，名称保持一至即可

 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/2/23 9:21
 */
@RestController
public class FunctionController {
    @GetMapping("/test/lg")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String testLogin(){
        return "success";
    }

    @PreAuthorize("hasAuthority('test')")
    @GetMapping("/pass/lg")
    public String test22(){
        return "pass";
    }
}
