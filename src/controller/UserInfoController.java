package controller;

import com.sun.deploy.net.HttpResponse;
import dto.AdminLoginDto;
import dto.JsonResponseDto;
import dto.PageDto;
import dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import service.IUserInfoService;
import utils.Constants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/user_info")
public class UserInfoController {

    @Autowired
    IUserInfoService iUserInfoService;

    /**
     * 获取用户列表，全部
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_user_list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto<List<UserInfoDto>> getUserList() {
        return iUserInfoService.queryUserList();
    }

    /**
     * 根据页数获取用户列表
     * @param page
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_user_list_by_page", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto<List<UserInfoDto>> getUserListByPage(int page,int size) {
        PageDto pageDto=new PageDto();
        pageDto.setStart((page-1)*size);
        pageDto.setCount(size);
        int total=iUserInfoService.queryUserCount();
        pageDto.caculateLast(total);
        return iUserInfoService.queryUserListByPage(pageDto);
    }


    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_user_by_id", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getUserById(int userId) {
        return iUserInfoService.queryUserById(userId);
    }


    /**
     * 用户注册
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponseDto register(String account,String password,int code,HttpServletRequest httpServletRequest) {
        return iUserInfoService.register(account,password,code,httpServletRequest);
    }

    /**
     * 管理员注册用户
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register_by_admin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponseDto register(@RequestBody UserInfoDto userInfoDto) {
        return iUserInfoService.registerByAdmin(userInfoDto);
    }


    /**
     * 用户登录
     * @param account
     * @param password
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponseDto register(String account, String password, HttpServletRequest request, HttpServletResponse httpResponse) {
        return iUserInfoService.login(account,password,request,httpResponse);
    }


    /**
     * 后台管理员登录验证
     * @param adminLoginDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin_login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResponseDto adminLogin(@RequestBody AdminLoginDto adminLoginDto, HttpServletRequest  request) {
        return iUserInfoService.adminLogin(adminLoginDto,request);
    }

    /**
     * 客户端更新用户头像
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/upadate_avatar", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto updateAvatar(int userId,@RequestParam(value = "file", required = false) CommonsMultipartFile file, HttpServletRequest request) {
       return iUserInfoService.updateAvatar(userId,file,request);
    }

    /**
     * 后台管理预更新用户头像
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/pre_upadate_avatar", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto preUpdateAvatar(@RequestParam(value = "file", required = false) CommonsMultipartFile file, HttpServletRequest request) {
        return iUserInfoService.predateAvatar(file,request);
    }


    /**
     * 更新用户信息
     * @param userInfoDto
     * @return
     */
    @RequestMapping(value = "/update_user_info", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  updateInfo(UserInfoDto userInfoDto){
        return iUserInfoService.updateUserInfo(userInfoDto);
    }


    /**
     * 删除用户头像
     * @param avatarPath
     * @return
     */
    @RequestMapping(value = "/delete_user_avatar", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  deleteUserAvatar(String avatarPath){
        return iUserInfoService.deletePreAvatar(avatarPath);
    }


    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/delete_user", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  deleteUser(int userId,HttpServletRequest request){
        return iUserInfoService.deleteUser(userId,request);
    }


    /**
     * 获取验证码
     * @param account
     * @return
     */
    @RequestMapping(value = "/get_code", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  getCode(String account,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        return iUserInfoService.getCode(account,httpServletRequest,httpServletResponse);
    }

    /**
     * 忘记密码
     * @param account
     * @return
     */
    @RequestMapping(value = "/forgetpassword", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  getCode(String account,String password,int code,HttpServletRequest httpServletRequest){
        return iUserInfoService.forgetPassword(account,password,code,httpServletRequest);
    }

}
