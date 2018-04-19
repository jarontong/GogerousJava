package controller;

import com.sun.deploy.ui.AppInfo;
import dto.AppInfoDto;
import dto.JsonResponseDto;
import dto.PictureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import service.IAppInfoService;
import service.IPictureService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/app_info")
public class AppInfoController {

    @Autowired
    private IAppInfoService iAppInfoService;

    @RequestMapping(value = "/update_app_info", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto updateApp(AppInfoDto appUpdateBean, @RequestParam(value = "appFile", required = false) CommonsMultipartFile[] files, HttpServletRequest request) {
        return iAppInfoService.updateApp(appUpdateBean, files, request);
    }

    @RequestMapping(value = "/get_update_app_info", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto updateApp() {
        return iAppInfoService.getUpdateAppInfo();
    }

}
