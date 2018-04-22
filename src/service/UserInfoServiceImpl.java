package service;

import dto.*;
import mapper.IPictureMapper;
import mapper.IPostPictureInfoMapper;
import mapper.IUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import utils.Constants;
import utils.EmailCodeUtil;
import utils.FileUtil;
import utils.PasswordSecretUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Constants.STATUE_FAIL;
import static utils.Constants.STATUE_OK;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    IUserInfoMapper iUserInfoMapper;
    @Autowired
    IPictureMapper iPictureMapper;
    @Autowired
    IPostPictureInfoMapper iPostPictureInfoMapper;


    @Override
    public JsonResponseDto queryUserList() {
        List<UserInfoDto> userInfoDtoList = iUserInfoMapper.queryUserList();
        if (null == userInfoDtoList) {
            return new JsonResponseDto<>(STATUE_FAIL, "获取失败,查询数据为空", null);
        } else {
            return new JsonResponseDto<>(STATUE_OK, "获取成功", userInfoDtoList);
        }
    }

    @Override
    public JsonResponseDto queryUserById(int id) {
        UserInfoDto userInfoDto = iUserInfoMapper.queryUserById(id);
        if (null == userInfoDto) {
            return new JsonResponseDto<>(STATUE_FAIL, "获取失败,查询数据为空", null);
        } else {
            return new JsonResponseDto<>(STATUE_OK, "获取成功", userInfoDto);
        }

    }

    @Override
    public JsonResponseDto queryUserListByPage(PageDto pageDto) {
        return new JsonResponseDto<>(STATUE_OK, "查询成功", iUserInfoMapper.queryUserListByPage(pageDto));
    }


    @Override
    public JsonResponseDto register(String account, String password, int code, HttpServletRequest request) {
        UserInfoDto user = iUserInfoMapper.queryUserByAccount(account);
        if (user != null) {
            return new JsonResponseDto<>(STATUE_FAIL, "注册失败,账号已存在", "");
        } else {
            int cookieCode = 0;
            String cookieAccount="";
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.VERIFICATION_CODE)) {
                    cookieCode = Integer.valueOf(cookie.getValue());
                }else if(cookie.getName().equals(Constants.VERIFICATION_ACCOUNT)){
                    cookieAccount=cookie.getValue();
                }
            }
            if (cookieCode <= 0||cookieAccount.equals("")) {
                return new JsonResponseDto<>(STATUE_FAIL, "注册失败,验证码已过期", "");
            } else if (cookieCode != code||!account.equals(cookieAccount)) {
                return new JsonResponseDto<>(STATUE_FAIL, "注册失败,验证码错误", "");
            }
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setAccount(account);
            userInfoDto.setPassword(PasswordSecretUtil.secretPassword(password));
            int index = iUserInfoMapper.register(userInfoDto);
            if (index > 0) {
                return new JsonResponseDto<>(STATUE_OK, "注册成功", "");
            } else {
                return new JsonResponseDto<>(STATUE_FAIL, "注册失败", "");
            }
        }
    }

    @Override
    public JsonResponseDto registerByAdmin(UserInfoDto userInfoDto) {
        UserInfoDto user = iUserInfoMapper.queryUserByAccount(userInfoDto.getAccount());
        if (user != null) {
            return new JsonResponseDto<>(STATUE_FAIL, "注册失败,账号已存在", "");
        } else {
            userInfoDto.setPassword(PasswordSecretUtil.secretPassword(userInfoDto.getPassword()));
            int index = iUserInfoMapper.register(userInfoDto);
            if (index > 0) {
                return new JsonResponseDto<>(STATUE_OK, "注册成功", "");
            } else {
                return new JsonResponseDto<>(STATUE_FAIL, "注册失败", "");
            }
        }
    }

    @Override
    public JsonResponseDto login(String account, String password, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("account", account);
        loginInfo.put("password", PasswordSecretUtil.secretPassword(password));
        UserInfoDto userInfoDto = iUserInfoMapper.login(loginInfo);
        if (null == userInfoDto) {
            return new JsonResponseDto<>(Constants.STATUE_FAIL, "用户名或密码错误", "");
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("isLogin", true);
            // 将token放进Cookie
            Cookie cookie = new Cookie(Constants.USER_IS_LOGIN, "login");
            cookie.setPath("/");
            // 过期时间设为10min
            cookie.setMaxAge(Constants.COOKIE_TIME);
            response.addCookie(cookie);
            return new JsonResponseDto<>(Constants.STATUE_OK, "登录成功", userInfoDto);
        }
    }


    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @Override
    public JsonResponseDto deleteUser(int userId,HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
        String path = servletContext.getRealPath(File.separator);   //设定文件保存的目录
        List<PostPictureInfoDto> postPictureInfoDtos=iPostPictureInfoMapper.queryPostPictureListByUserId(userId);
        if(postPictureInfoDtos!=null&&postPictureInfoDtos.size()>0){
            for (PostPictureInfoDto temp : postPictureInfoDtos) {
                PostPictureInfoDto postPictureInfoDto=iPostPictureInfoMapper.queryPostPictureById(temp.getId());
                if(null!=postPictureInfoDto) {
                    //删除封面图片
                    File file=new File(path+postPictureInfoDto.getCover());
                    if(file.exists()){
                        file.delete();
                    }
                    //删除套图图片
                    List<PictureDto> pictureDtos=iPictureMapper.queryPostPictureByPostId(temp.getId());
                    if(pictureDtos!=null&&pictureDtos.size()>0){
                        for (PictureDto pictureDto : pictureDtos) {
                            File pictureFile=new File(path+pictureDto.getPictureAddress());
                            if(pictureFile.exists()){
                                pictureFile.delete();
                            }
                        }
                    }
                    iPostPictureInfoMapper.deletePostPicture(temp.getId());
                }
            }
        }
        UserInfoDto userInfoDto=iUserInfoMapper.queryUserById(userId);
        if(null!=userInfoDto){
            File file=new File(path+userInfoDto.getAvatar());
            if(file.exists()){
                file.delete();
            }
        }
        int index = iUserInfoMapper.deleteUser(userId);
        if (index > 0) {
            return new JsonResponseDto<>(STATUE_OK, "删除成功", "1");
        } else {
            return new JsonResponseDto<>(STATUE_FAIL, "删除失败", "0");
        }
    }

    @Override
    public JsonResponseDto updateAvatar(int userId,CommonsMultipartFile file, HttpServletRequest request) {
        if (null == file) {
            return new JsonResponseDto<>(STATUE_FAIL, "文件为空", "");
        } else {
            String fileName = FileUtil.upLoadFile(file,request,Constants.USER_AVATAR,String.valueOf(userId));
            if(fileName.equals("")){
                return new JsonResponseDto<>(STATUE_FAIL, "图片上传失败", "");
            }else {
                UserInfoDto userInfoDto=new UserInfoDto();
                userInfoDto.setId(userId);
                userInfoDto.setAvatar(fileName);
                int index=iUserInfoMapper.updateUserInfo(userInfoDto);
                if(0>=index){
                    return new JsonResponseDto<>(STATUE_FAIL, "更新失败", "");
                }else {
                    return new JsonResponseDto<>(STATUE_OK, "更新成功", fileName);
                }
            }
        }

    }

    //后台管理用
    @Override
    public JsonResponseDto predateAvatar(CommonsMultipartFile file, HttpServletRequest request) {
        if (null == file) {
            return new JsonResponseDto<>(STATUE_FAIL, "文件为空", "");
        } else {
            String fileName =FileUtil.upLoadFile(file,request,Constants.USER_AVATAR,String.valueOf(System.currentTimeMillis()));
            if(fileName.equals("")){
                return new JsonResponseDto<>(STATUE_FAIL, "图片上传失败", "");
            }else {
                return new JsonResponseDto<>(STATUE_OK, "上传成功", fileName);
            }

        }
    }

    @Override
    public JsonResponseDto deletePreAvatar(String avatarPath) {
        return null;
    }

    @Override
    public JsonResponseDto updateUserInfo(UserInfoDto userInfoDto) {
        if(userInfoDto.getPassword()!=null&&!userInfoDto.getPassword().equals("")){
            userInfoDto.setPassword(PasswordSecretUtil.secretPassword(userInfoDto.getPassword()));
        }
        if (0 >= iUserInfoMapper.updateUserInfo(userInfoDto)) {
            return new JsonResponseDto<>(STATUE_FAIL, "更新失败", "0");
        } else {
            return new JsonResponseDto<>(STATUE_OK, "更新成功", "1");
        }

    }

    @Override
    public int queryUserCount() {
        return iUserInfoMapper.queryUserCount();
    }

    @Override
    public JsonResponseDto adminLogin(AdminLoginDto adminLoginDto, HttpServletRequest request) {
        if (adminLoginDto.getAccount().equals(Constants.ADMIN_ACCOUNT) && adminLoginDto.getPassword().equals(Constants.ADMIN_PASSWORD)) {
            request.getSession().setAttribute("admin_login", true);
            return new JsonResponseDto<>(STATUE_OK, "登录成功", "1");
        } else {
            return new JsonResponseDto<>(STATUE_FAIL, "用户或密码错误", "0");
        }
    }

    @Override
    public JsonResponseDto getCode(String account, HttpServletRequest request, HttpServletResponse response) {
        String code = EmailCodeUtil.qqSendMail(account);
        if (code != null && !code.equals("")) {
            Cookie cookie = new Cookie(Constants.VERIFICATION_CODE, code);
            Cookie accountCookie=new Cookie(Constants.VERIFICATION_ACCOUNT,account);
            cookie.setPath("/");
            accountCookie.setPath("/");
            // 过期时间设为10min
            cookie.setMaxAge(Constants.COOKIE_CODE_TIME);
            accountCookie.setMaxAge(Constants.COOKIE_CODE_TIME);
            response.addCookie(cookie);
            response.addCookie(accountCookie);
            return new JsonResponseDto<>(STATUE_OK, "发送成功", code);
        } else {
            return new JsonResponseDto<>(STATUE_FAIL, "发送失败", "");
        }
    }

    @Override
    public JsonResponseDto forgetPassword(String account, String password, int code, HttpServletRequest request) {
        int cookieCode = 0;
        String cookieAccount="";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.VERIFICATION_CODE)) {
                cookieCode = Integer.valueOf(cookie.getValue());
            }else if(cookie.getName().equals(Constants.VERIFICATION_ACCOUNT)){
                cookieAccount=cookie.getValue();
            }
        }
        if (cookieCode <= 0||cookieAccount.equals("")) {
            return new JsonResponseDto<>(STATUE_FAIL, "修改失败,验证码已过期", "");
        } else if (cookieCode != code||!cookieAccount.equals(account)) {
            return new JsonResponseDto<>(STATUE_FAIL, "修改失败,验证码错误", "");
        }
        UserInfoDto userInfoDto=iUserInfoMapper.queryUserByAccount(account);
        if(null==userInfoDto){
            return new JsonResponseDto<>(STATUE_FAIL, "修改失败,用户不存在", "");
        }else {
            userInfoDto.setPassword(password);
            return updateUserInfo(userInfoDto);
        }
    }
}
