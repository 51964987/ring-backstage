package ringbackstage.common.utils.result;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import ringbackstage.common.entity.RestResult;
import ringbackstage.common.enums.ResultCode;

/**
 * Result工具类 
 * @author ring
 * @date 2017年12月4日 下午4:00:54
 * @version V1.0
 */
public class ResultGenerator {
	private static Logger logger = LoggerFactory.getLogger(ResultGenerator.class);
	
	public static <T> RestResult<T> result(ResultCode resultCode,T data,Date start){
		return result(resultCode.getCode(), resultCode.getMsg(), data, start);
	}
	
	public static <T> RestResult<T> result(String code,String msg,T data,Date start){
		RestResult<T> restResult = RestResult.newInstance();
		restResult.setStart(start);
		restResult.setEnd(new Date());
		restResult.setCode(code);
		restResult.setMessage(msg);
		restResult.setData(data);
		if(logger.isDebugEnabled()){
			logger.debug("generate rest result :"+JSON.toJSONString(restResult));
		}
		return restResult;
	}
}
