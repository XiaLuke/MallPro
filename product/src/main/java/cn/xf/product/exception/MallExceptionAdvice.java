package cn.xf.product.exception;

import cn.xf.common.exception.CommonExceptionCode;
import cn.xf.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物中心异常建议
 * 集中异常处理
 *
 * @author XF
 * @date 2022/02/16
 */
@Slf4j
@ResponseBody
@RestControllerAdvice(basePackages = "cn.xf.product.controller")
public class MallExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e){
        log.error("数据校验异常，问题{},类型{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach((item)->{
           map.put(item.getField(),item.getDefaultMessage());
        });
        return R.error(CommonExceptionCode.VALID_EXCEPTION.getCode(), CommonExceptionCode.VALID_EXCEPTION.getMsg()).put("data",map);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Exception e){
        return R.error(CommonExceptionCode.UNKNOWN_EXCEPTION.getCode(),CommonExceptionCode.UNKNOWN_EXCEPTION.getMsg());
    }
}
