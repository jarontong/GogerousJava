package mapper;


import com.sun.deploy.ui.AppInfo;
import dto.AppInfoDto;
import dto.PictureDto;

import java.util.List;

public interface IAppInfoMapper {

     int add(AppInfoDto appUpdateBean);

     AppInfoDto query(int appVersionCode);

     int updateApp(AppInfoDto appUpdateBean);

     AppInfoDto queryRelease();


}
