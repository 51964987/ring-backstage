package ringbackstage.web.controller.login;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import ringbackstage.common.consts.PlatformConstant;
import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.service.login.UserLoginService;

@Controller
public class LoginController {
	@Autowired
	private UserLoginService userLoginService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String loginName,String loginPwd,String loginCode,
			Map<String, Object> model,HttpServletRequest request){
		try {
			if(StringUtils.isEmpty(loginCode)){
				throw new ResultException(ResultCode.LOGIN_CODE_EMPTY_ERROR);
			}
			if(!request.getSession().getAttribute(PlatformConstant.SESSION_LOGIN_CODE).toString().equalsIgnoreCase(loginCode)){
				throw new ResultException(ResultCode.LOGIN_CODE_ERROR);
			}
			userLoginService.login(loginName, loginPwd,request);
		} catch (ResultException e) {
			model.put("error", e.getResultCode());
			return "login";
		}
		return "redirect:index";
	}
	
	@RequestMapping(value="/logout")
	public String logout(String loginName,String password,
			Map<String, Object> model,HttpServletRequest request){
		request.getSession().removeAttribute(PlatformConstant.SESSION_USER);
		return "redirect:login";
	}
	
	@Autowired
	private DefaultKaptcha captchaProducer;
	
	@RequestMapping(value = "code/captcha-image")  
    public ModelAndView getKaptchaImage(HttpServletRequest request,  
            HttpServletResponse response) throws Exception {  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control",  
                "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
  
        String capText = captchaProducer.createText(); 
        request.getSession().setAttribute(PlatformConstant.SESSION_LOGIN_CODE, capText);
        
        /**
         * 如上面的代码，在用户登录的时候使用验证码以及cooike中的captchacode
         * 来实现唯一性验证，开始的时候我考虑到放到session中，当时想了下，
         * 感觉这不科学啊，比如讲captchacode放到session中，这时候验证码是一个，
         * 后来另一个用户再登陆，前一个用户还在登陆中，这时候会出现一系列的问题。
         * 这里使用cookie和redis，来应对用户的并发登陆验证。
         */
        
//        System.out.println("capText: " + capText);  
//        try {  
//            String uuid=UUIDUtils.getUUID32().trim().toString();              
//            redisTemplate.opsForValue().set(uuid, capText,60*5,TimeUnit.SECONDS);  
//            Cookie cookie = new Cookie("captchaCode",uuid);  
//            response.addCookie(cookie);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }  
	
}
