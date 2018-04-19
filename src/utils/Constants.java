package utils;

import java.io.File;

public class Constants {

    public static final int STATUE_OK=200;
    public static final int STATUE_FAIL=400;
    public static final int STATUE_FAIL_NOT_LOGIN=401; //未登陆状态码
    public static final int COOKIE_TIME=60*60*24; //cooike登陆有效时长
    public static final int COOKIE_CODE_TIME=60;//验证码有效时长

    public static final String BASE_URL="http://localhost:8080/";
    public static final String ADMIN_ACCOUNT="admin";
    public static final String ADMIN_PASSWORD="admin";

    public static final String USER_AVATAR="preUpdateAvatar";
    public static final String PICTURE_COVER="preUpdateCover";
    public static final String APK_File="apkFile";
    public static final String PICTURES="prePostPicture";

    public static final String USER_IS_LOGIN="user_is_login";
    public static final String VERIFICATION_CODE="verification_code";
}
