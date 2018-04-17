package mapper;



import dto.PageDto;
import dto.UserInfoDto;

import java.util.List;
import java.util.Map;

public interface IUserInfoMapper {

     int register(UserInfoDto userInfoDto);

     int deleteUser(int userId);

     UserInfoDto login(Map<String,String> loginInfo);

     List<UserInfoDto> queryUserList();

     UserInfoDto queryUserById(int userId);

     UserInfoDto queryUserByAccount(String account);

     List<UserInfoDto> queryUserListByPage(PageDto pageDto);

     int queryUserCount();

     int updateUserInfo(UserInfoDto userInfoDto);
}
