package ringbackstage.common.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ringbackstage.common.entity.RestResult;
import ringbackstage.common.utils.result.ResultGenerator;

/**
 * 统一异常处理
 * @author ring
 * @date 2017年12月4日 下午4:11:24
 * @version V1.0
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(Exception.class)
	private <T> RestResult<T> handleException(Exception e,HttpServletRequest request){
		logger.error(e.getMessage(),e);
		return ResultGenerator.result("500",e.getMessage(), e.getMessage(), null, new Date());
	}
}
