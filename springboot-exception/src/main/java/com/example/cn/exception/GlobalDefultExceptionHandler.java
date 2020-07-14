package com.example.cn.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalDefultExceptionHandler {
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public String handleNullPointerException(NullPointerException e) {
		
		return  "参数为空，不能操作,请重新操作";
	}
	
	@ExceptionHandler(value= { ArithmeticException.class,ArrayIndexOutOfBoundsException.class})
	@ResponseBody
	public String handleArithmeticException(Exception ex) {
		 
		if(ex instanceof ArithmeticException) {
			return "0不能作为除数，请检查";
		}else if(ex instanceof ArrayIndexOutOfBoundsException) {
			return  "下标越界异常，请检查";
			L
		}
		return "未知错误，请联系管理员";
	}
	

}
