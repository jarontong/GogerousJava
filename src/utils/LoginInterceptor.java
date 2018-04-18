package utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import dto.JsonResponseDto;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //后台登录判断
        System.out.println("后台登录验证");
        if(httpServletRequest.getSession().getAttribute("admin_login")!=null){
            return true;
        }else {
            if(!httpServletRequest.getRequestURL().toString().contains("ServiceController")){
                httpServletResponse.setContentType("application/json; charset=utf-8");
                PrintWriter writer = httpServletResponse.getWriter();
                JsonResponseDto jsonResponseDto=new JsonResponseDto(Constants.STATUE_FAIL_NOT_LOGIN,"未登陆","");
                writer.print(JSONObject.toJSONString(jsonResponseDto, SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat));
                writer.close();
                httpServletResponse.flushBuffer();
            }
            return false;
        }


    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
