package service;

import com.sun.deploy.ui.AppInfo;
import dto.AppInfoDto;
import dto.JsonResponseDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IAppInfoService {

    JsonResponseDto updateApp(AppInfoDto appUpdateBean, CommonsMultipartFile[] files, HttpServletRequest request);

    JsonResponseDto getUpdateAppInfo();

    JsonResponseDto add(AppInfoDto appInfo);
}
