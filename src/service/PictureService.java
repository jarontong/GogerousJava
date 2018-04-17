package service;

import dto.JsonResponseDto;
import dto.PictureDto;
import mapper.IPictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import utils.Constants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

import static utils.Constants.STATUE_OK;

@Service
public class PictureService implements IPictureService {

    @Autowired
    private IPictureMapper iPictureMapper;

    @Override
    public JsonResponseDto postPicture(PictureDto pictureDto) {
        int index=iPictureMapper.postPicture(pictureDto);
        if(0>=index){
            return  new JsonResponseDto(Constants.STATUE_FAIL,"上传失败","");
        }else {
            return  new JsonResponseDto(Constants.STATUE_OK,"上传成功","");
        }
    }

    @Override
    public JsonResponseDto deletePicture(int pictureId) {
        int index=iPictureMapper.deletePicture(pictureId);
        if(0>=index){
            return  new JsonResponseDto(Constants.STATUE_FAIL,"删除失败","0");
        }else {
            return  new JsonResponseDto(Constants.STATUE_OK,"删除成功","1");
        }
    }

    @Override
    public JsonResponseDto queryPostPictureByPostId(int postId) {
        List<PictureDto> pictureDtos=iPictureMapper.queryPostPictureByPostId(postId);
        if(null==pictureDtos||0>=pictureDtos.size()){
            return  new JsonResponseDto(Constants.STATUE_FAIL,"列表为空","");
        }else {
            return  new JsonResponseDto(Constants.STATUE_OK,"查询成功",pictureDtos);
        }
    }

    @Override
    public JsonResponseDto updatePostPictureInfo(List<PictureDto> pictureDtos) {
        if(null==pictureDtos||0>=pictureDtos.size()){
            return  new JsonResponseDto(Constants.STATUE_FAIL,"更新失败,数据为空","");
        }else {
            for (PictureDto pictureDto : pictureDtos) {
                int index=iPictureMapper.updatePostPictureInfo(pictureDto);
                if(0>=index){
                    return  new JsonResponseDto(Constants.STATUE_FAIL,"更新出错，数据库操作异常","");
                }
            }
            return  new JsonResponseDto(Constants.STATUE_OK,"更新成功","");
        }

    }

    @Override
    public JsonResponseDto prePostPicture(CommonsMultipartFile file, HttpServletRequest request) {
        if (null == file) {
            return new JsonResponseDto(0, "文件为空", "");
        } else {
            String fileAddress = "";
            ServletContext servletContext = request.getSession().getServletContext();
            //上传位置
            String path = Constants.BASE_IMG_URL + File.separator;   //设定文件保存的目录
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            if (!file.isEmpty()) {
                fileAddress = Constants.BASE_IMG_URL + File.separator + Constants.PICTURES+ File.separator + file.getOriginalFilename();
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
}
