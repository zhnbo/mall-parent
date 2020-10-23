package com.woniuxy.order.exception;


import com.woniuxy.commons.result.ResponseResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 * @author zh_o
 * @date 2020-9-23
 */
@RestControllerAdvice
public class GlobalExceptionResolver {

	/**
	 * 异常
	 */
	@ExceptionHandler({Exception.class})
	public ResponseResult<?> othersException(Exception e) {
		e.printStackTrace();
		return ResponseResult.error(e.getMessage());
	}

	/**
	 * 方法参数异常
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
	public ResponseResult<?> methodArgumentNotValidException(Exception e) {
		e.printStackTrace();
		return ResponseResult.error("参数不合法");
	}

}
