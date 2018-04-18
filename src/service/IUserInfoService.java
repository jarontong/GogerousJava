package service;

import dto.AdminLoginDto;
import dto.JsonResponseDto;
import dto.PageDto;
import dto.UserInfoDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserInfoService {

    JsonResponseDto queryUserList();

    JsonResponseDto queryUserById(int id);

    JsonResponseDto queryUserListByPage(PageDto pageDto);

    JsonResponseDto register(UserInfoDto userInfoDto);

    JsonResponseDto login(String account, String password ,HttpServletRequest request, HttpServletResponse response);

    JsonResponseDto deleteUser(int userId);

    JsonResponseDto preUpdateAvatar(CommonsMultipartFile file, HttpServletRequest request);

    JsonResponseDto deletePreAvatar(String avatarPath);

    JsonResponseDto upupdateUserInfo(UserInfoDto userInfoDto);

    int queryUserCount();

    JsonResponseDto adminLogin(AdminLoginDto adminLoginDto, HttpServletRequest  request);

    JsonResponseDto getCode(String account);
}
