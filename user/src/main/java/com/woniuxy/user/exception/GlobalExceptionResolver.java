package com.woniuxy.user.exception;


import com.woniuxy.commons.result.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 * @author: zh_o
 * @date: 2020-9-23
 */
@RestControllerAdvice
public class GlobalExceptionResolver {

	/**
	 * 异常
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseResult<?> othersException(Exception e) {
		e.printStackTrace();
		return ResponseResult.error(e.getMessage());
	}

}
