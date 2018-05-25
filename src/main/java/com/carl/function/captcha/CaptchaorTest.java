package com.carl.function.captcha;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

public class CaptchaorTest {

	private static Captchaor captchaor = new Captchaor();

	public static void main(String[] args) throws Exception {
		
		int num = new Random().nextInt(10000);
		String code = String.format("%04d", num);
		System.out.println("num:"+num+"; code:"+code);
		
		byte[] data = captchaor.getCaptchaImage(code);
		
		OutputStream fileOutputStream = new FileOutputStream(new File(code+".png"));
		
		fileOutputStream.write(data);
		fileOutputStream.flush();
	}
	
	/**
	 * 
	 * @desc html获取图片验证码
	 * <p>
	 * 	1. 假设该方法的请求地址为： /api/login/captcha
	 * 	2. 前端直接将请求地址写在src属性上即可获取到;
	 * 		通过js修改该src属性更新验证码， src属性更新后， 会自动请求src指定的资源； 通过random使得src属性不同
	 * 	<img src="http://localhost:8080/api/login/captcha&random=Math.random()"/>
	 * </p>
	 * @author changez@thinkive.com
	 * @time 2018年5月25日 下午1:11:31
	 * @param response
	 * @throws IOException
	 */
	public static void getCaptcha(HttpServletResponse response) throws IOException {
		
		// 生成验证码数字
		int num = new Random().nextInt(10000);
		String code = String.format("%04d", num);
		
		//获取图片验证码二进制数组
		byte[] data = captchaor.getCaptchaImage(code);
		
		//设置http响应类型为图片
		response.setContentType("image/png");
		
		//将byte数组写入response
		OutputStream stream = response.getOutputStream();
		stream.write(data);
		stream.flush();
		stream.close();
	}
}