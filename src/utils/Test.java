package utils;

import dto.PageDto;
import dto.PictureDto;
import dto.PostPictureInfoDto;
import dto.UserInfoDto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.IPictureService;
import service.IPostPictureInfoService;
import service.IUserInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test {
//    @Autowired
//    private LoginService loginService;
    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private IPostPictureInfoService iPostPictureInfoService;

    @Autowired
    private IPictureService iPictureService;
    @org.junit.Test
    public void test() {
//        for (int i = 0; i < 50; i++) {
//            UserInfoDto userInfoDto=new UserInfoDto();
//            userInfoDto.setAccount("abc"+i);
//            userInfoDto.setNickname("无猫元"+i+"号");
//            userInfoDto.setPassword("abc");
//            System.out.println( iUserInfoService.register(userInfoDto));
//
//        }
//        PageDto pageDto=new PageDto();
//        pageDto.setStart(1*10);
//        pageDto.setCount(10);
//        int total=iUserInfoService.queryUserCount();
//        pageDto.caculateLast(total);
//        System.out.println("用户数量为:"+total);
//        System.out.println( iUserInfoService.queryUserListByPage(pageDto));
//        UserInfoDto userInfoDto=new UserInfoDto();
//        userInfoDto.setId(4);
//        userInfoDto.setNickname("更新用户信息测试");
//        PictureDto postPictureInfoDto=new PictureDto();
//        postPictureInfoDto.setPictureAddress("http://localhost:8080/preUpdateCover/6.jpg");
//        postPictureInfoDto.setPostPictureInfoId(3);
//        postPictureInfoDto.setId(50);
//        postPictureInfoDto.setSort(100);
//        iPictureService.updatePostPictureInfo(postPictureInfoDto);
    }



}
