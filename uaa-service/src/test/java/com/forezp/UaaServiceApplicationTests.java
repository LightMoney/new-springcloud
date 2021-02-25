package com.forezp;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class UaaServiceApplicationTests {

	@Test
	public void contextLoads() throws UnsupportedEncodingException {

		final Base64.Decoder decoder = Base64.getDecoder();
		final Base64.Encoder encoder = Base64.getEncoder();
		final String text = "user-service:123456";
		final byte[] textByte = text.getBytes("UTF-8");
//编码
		final String encodedText = encoder.encodeToString(textByte);
		System.out.println(encodedText);
//解码
//		System.out.println(new String(decoder.decode("Zg/UupIewCVQl769oq1ckbQebHo4XeR2un+Zd67KQDYSvBpfFQZjoB8ifMx9THFoikI2vPdrd1FJagTtOzro2zpErXIYXsZQlddBQk5z28Bkq2tep+yNoykU7Uawoy+M0Z4NXqan3dnY5h8gP9XG524O3P2HGWMsXncIkbN1rYzG6+2qRkUOYFPeu6GD+wx1jyiaVNUzC57ECAeUzC2ChzXch0IO4VTvuISz05PvMArcdmjubovJ7YMtLehsJ6pmR2HjMe/3G9w0+E8f1cFD+5NriXgqRCafYMj+sko5Z3k1uzK9CEvh4FtVbUK+9OOym3CO/Fbo4md3LQXXlR/ZBg=="), "UTF-16"));
////		boolean matches = new BCryptPasswordEncoder()
////				.matches("123456", "Zg/UupIewCVQl769oq1ckbQebHo4XeR2un+Zd67KQDYSvBpfFQZjoB8ifMx9THFoikI2vPdrd1FJagTtOzro2zpErXIYXsZQlddBQk5z28Bkq2tep+yNoykU7Uawoy+M0Z4NXqan3dnY5h8gP9XG524O3P2HGWMsXncIkbN1rYzG6+2qRkUOYFPeu6GD+wx1jyiaVNUzC57ECAeUzC2ChzXch0IO4VTvuISz05PvMArcdmjubovJ7YMtLehsJ6pmR2HjMe/3G9w0+E8f1cFD+5NriXgqRCafYMj+sko5Z3k1uzK9CEvh4FtVbUK+9OOym3CO/Fbo4md3LQXXlR/ZBg==");
////		System.out.println(matches);
//		String encode = new BCryptPasswordEncoder().encode("123456");
//		boolean matches = new BCryptPasswordEncoder().matches("123456", encode);
//		System.out.println(matches);

	}

}
