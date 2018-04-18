package utils;

import java.io.File;

public class Constants {

    public static final int STATUE_OK=200;
    public static final int STATUE_FAIL=400;
    public static final int STATUE_FAIL_NOT_LOGIN=401; //未登陆状态码

    public static final String BASE_URL="http://localhost:8080/";
    public static final String BASE_IMG_URL="usr"+ File.separator+"local"+  File.separator+"tomcat7"+File.separator+"webapps"+File.separator+"gogerousImg";
    public static final String ADMIN_ACCOUNT="admin";
    public static final String ADMIN_PASSWORD="admin";
    public static final String USER_AVATAR="preUpdateAvatar";
    public static final String PICTURE_COVER="preUpdateCover";
    public static final String PICTURES="prePostPicture";

}
