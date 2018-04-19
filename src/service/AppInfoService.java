package service;

import com.sun.deploy.ui.AppInfo;
import dto.AppInfoDto;
import dto.JsonResponseDto;
import mapper.IAppInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import utils.Constants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

import static utils.Constants.APK_File;
import static utils.Constants.STATUE_FAIL;
import static utils.Constants.STATUE_OK;

@Service
public class AppInfoService implements IAppInfoService {

    @Autowired
    private IAppInfoMapper iAppInfoMapper;


    @Override
    public JsonResponseDto updateApp(AppInfoDto appUpdateBean, CommonsMultipartFile[] files, HttpServletRequest request) {
        String fileName = "";
        if (appUpdateBean == null) {
            return new JsonResponseDto<>(Constants.STATUE_FAIL, "参数不能为空", null);
        }
        if (files != null && files.length > 0) {
            //获得项目路径
            ServletContext sc = request.getSession().getServletContext();
            //上传位置
            String path = sc.getRealPath(File.separator+APK_File) + File.separator;   //设定文件保存的目录
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            for (int i = 0; i < files.length; i++) {
                //获得原始文件名
                fileName = files[i].getOriginalFilename();
                // System.out.println("原始文件名:" + fileName);
                //
                if (!files[i].isEmpty()) {
                    try {
                        FileOutputStream fos = new FileOutputStream(path + fileName);
                        InputStream in = files[i].getInputStream();
                        int b = 0;
                        while ((b = in.read()) != -1) {
                            fos.write(b);
                        }
                        fos.close();
                        in.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                appUpdateBean.setDownloadAddress(File.separator+APK_File+File.separator + fileName);
            }
        }
        AppInfoDto updateBean=iAppInfoMapper.query(appUpdateBean.getVersion());
        if(updateBean==null){
            iAppInfoMapper.add(appUpdateBean);
            return new JsonResponseDto<>(STATUE_OK,"更新成功",null);
        }else {
            return new JsonResponseDto<>(STATUE_FAIL,"更新出错,该版本已存在",null);
        }
    }

    @Override
    public JsonResponseDto getUpdateAppInfo() {
        AppInfoDto updateBean=iAppInfoMapper.queryRelease();
        if(updateBean==null){
            return new JsonResponseDto<>(STATUE_OK,"没有版本信息",null);
        }else {
            return new JsonResponseDto<>(STATUE_OK,"最新版本信息",updateBean);
        }
    }

    @Override
    public JsonResponseDto add(AppInfoDto appInfo) {
        int index=iAppInfoMapper.add(appInfo);
        if (0>=index){
            return new JsonResponseDto<>(Constants.STATUE_FAIL,"插入失败","");
        }else {
            return new JsonResponseDto<>(Constants.STATUE_OK,"插入成功","");
        }
    }
}
