package service;

import dto.JsonResponseDto;
import dto.PageDto;
import dto.UserInfoDto;
import mapper.IUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import utils.Constants;

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
        return new JsonResponseDto(STATUE_OK, "查询成功", iUserInfoMapper.queryUserListByPage(pageDto));
    }


    @Override
    public JsonResponseDto register(UserInfoDto userInfoDto) {
        UserInfoDto user = iUserInfoMapper.queryUserByAccount(userInfoDto.getAccount());
        if (user != null) {
            return new JsonResponseDto<>(STATUE_FAIL, "注册失败,账号已存在", "");
        } else {
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
        Map<String,String> loginInfo=new HashMap<>();
        loginInfo.put("account",account);
        loginInfo.put("password",password);
        UserInfoDto userInfoDto = iUserInfoMapper.login(loginInfo);
        if (null == userInfoDto) {
            return new JsonResponseDto(Constants.STATUE_FAIL, "用户名或密码错误", "");
        } else {
            HttpSession session=request.getSession(true);
            session.setAttribute("isLogin",true);
            String      token = "aaa";
            // 将token放进Cookie
            Cookie cookie = new Cookie("JWT", token);
            cookie.setPath("/");
            // 过期时间设为10min
            cookie.setMaxAge(60*10);
            response.addCookie(cookie);
            return new JsonResponseDto(Constants.STATUE_OK, "登录成功", userInfoDto);
        }
    }


    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @Override
    public JsonResponseDto deleteUser(int userId) {
        int index = iUserInfoMapper.deleteUser(userId);
        if (index > 0) {
            return new JsonResponseDto<>(STATUE_OK, "删除成功", "1");
        } else {
            return new JsonResponseDto<>(STATUE_FAIL, "删除失败", "0");
        }
    }

    @Override
    public JsonResponseDto preUpdateAvatar(CommonsMultipartFile file, HttpServletRequest request) {
        if (null == file) {
            return new JsonResponseDto(0, "文件为空", "");
        } else {
            String fileAddress = "";
            ServletContext servletContext = request.getSession().getServletContext();
            //上传位置
            String path = Constants.BASE_IMG_URL + File.separator + Constants.USER_AVATAR + File.separator;
            //     String path = servletContext.getRealPath(File.separator + Constants.USER_AVATAR) + File.separator;   //设定文件保存的目录
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            if (!file.isEmpty()) {
                fileAddress = Constants.BASE_IMG_URL + File.separator + Constants.USER_AVATAR + File.separator + file.getOriginalFilename();
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    fos = new FileOutputStream(path + file.getOriginalFilename());
                    is = file.getInputStream();
                    byte[] b = new byte[1024 * 1024];
                    int len;
                    while ((len = is.read(b)) != -1) {
                        fos.write(b, 0, len);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != fos) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != is) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return new JsonResponseDto(STATUE_OK, "上传成功", fileAddress);
        }

    }

    @Override
    public JsonResponseDto deletePreAvatar(String avatarPath) {
        return null;
    }

    @Override
    public JsonResponseDto upupdateUserInfo(UserInfoDto userInfoDto) {
        if (0 >= iUserInfoMapper.updateUserInfo(userInfoDto)) {
            return new JsonResponseDto(STATUE_FAIL, "更新失败", "0");
        } else {
            return new JsonResponseDto(STATUE_OK, "更新成功", "1");
        }

    }

    @Override
    public int queryUserCount() {
        return iUserInfoMapper.queryUserCount();
    }
}
