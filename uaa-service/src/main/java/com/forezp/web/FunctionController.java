package com.forezp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/2/23 9:21
 */
@RestController
public class FunctionController {
    @GetMapping("/test/lg")
    public String testLogin(){
        return "success";
    }

    @GetMapping("/pass/lg")
    public String test22(){
        return "pass";
    }
}
