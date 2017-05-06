package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.ResultUtil;
import com.beardream.dao.UserMapper;
import com.beardream.model.User;
import com.beardream.service.LoginService;
import com.beardream.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by soft01 on 2017/4/28.
 */
@EnableAutoConfiguration
@RestController
public class LoginController {

//    @Autowired
//    private HttpServletRequest mRequest;

    @Autowired
    private UserMapper mUserMapper;

    @Autowired
    public LoginService mLoginService;

    //user:用户名和密码   model：设备型号
    @PostMapping("/login")
    public Object login(User user, @Null @RequestParam String model, BindingResult bindingResult, HttpServletRequest request, HttpSession session){
        System.out.println(user.getMobile() + "-----" + user.getPassword() + "-------" + model);
        if (session.getAttribute(Constants.USER) != null){
            System.out.println("已登陆 -->"+session.getAttribute(Constants.USER).toString());
            System.out.println("session时间 -->"+session.getCreationTime());
            return ResultUtil.success(0,"登录成功", session.getAttribute(Constants.USER).toString());
        }
        if (mLoginService.login(user, model, request, session)){
            return ResultUtil.success(session.getAttribute(Constants.USER).toString());
        }
        return ResultUtil.error(-1,"登录失败");
    }

    @PostMapping("/isLogin")
    public Object isLogin(HttpSession session){
        System.out.println(session.getAttribute(Constants.USER));
        if (session.getAttribute(Constants.USER) == null){
            return ResultUtil.success(-1,"未登录");
        }else {
            return ResultUtil.success("已登录");
        }
    }

    @PostMapping("/logout")
    public Object logout(User user, BindingResult bindingResult, HttpSession session){
        session.invalidate();
        return ResultUtil.error(0,"注销成功");
    }

}
