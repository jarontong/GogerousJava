package service;

import dto.JsonResponseDto;
import dto.PictureDto;
import mapper.IPictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import utils.Constants;
import utils.FileUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

import static utils.Constants.STATUE_FAIL;
import static utils.Constants.STATUE_OK;

@Service
public class PictureServiceImpl implements IPictureService {

    @Autowired
    private IPictureMapper iPictureMapper;

    @Override
    public JsonResponseDto postPicture(PictureDto pictureDto) {
        int index=iPictureMapper.postPicture(pictureDto);
        if(0>=index){
            return  new JsonResponseDto<>(Constants.STATUE_FAIL,"上传失败","");
        }else {
            return  new JsonResponseDto<>(Constants.STATUE_OK,"上传成功","");
        }
    }



    @Override
    public JsonResponseDto deletePicture(int pictureId,HttpServletRequest request) {
        PictureDto pictureDto=iPictureMapper.queryById(pictureId);
        if(null==pictureDto){
            return  new JsonResponseDto<>(Constants.STATUE_FAIL,"删除失败,id错误","0");
        }else {
            ServletContext servletContext = request.getSession().getServletContext();
            String path = servletContext.getRealPath(File.separator);   //设定文件保存的目录
            File file=new File(path+pictureDto.getPictureAddress());
            if(file.exists()){
                file.delete();
            }
            int index=iPictureMapper.deletePicture(pictureId);
            if(0>=index){
                return  new JsonResponseDto<>(Constants.STATUE_FAIL,"删除失败","0");
            }else {
                return  new JsonResponseDto<>(Constants.STATUE_OK,"删除成功","1");
            }
        }
    }

    @Override
    public JsonResponseDto queryPostPictureByPostId(int postId) {
        List<PictureDto> pictureDtos=iPictureMapper.queryPostPictureByPostId(postId);
        if(null==pictureDtos||0>=pictureDtos.size()){
            return  new JsonResponseDto<>(Constants.STATUE_FAIL,"列表为空","");
        }else {
            return  new JsonResponseDto<>(Constants.STATUE_OK,"查询成功",pictureDtos);
        }
    }

    @Override
    public JsonResponseDto updatePostPictureInfo(List<PictureDto> pictureDtos) {
        if(null==pictureDtos||0>=pictureDtos.size()){
            return  new JsonResponseDto<>(Constants.STATUE_FAIL,"更新失败,数据为空","");
        }else {
            for (PictureDto pictureDto : pictureDtos) {
                int index=iPictureMapper.updatePostPictureInfo(pictureDto);
                if(0>=index){
                    return  new JsonResponseDto<>(Constants.STATUE_FAIL,"更新出错，数据库操作异常","");
                }
            }
            return  new JsonResponseDto<>(Constants.STATUE_OK,"更新成功","");
        }

    }

    @Override
    public JsonResponseDto prePostPicture(CommonsMultipartFile file, HttpServletRequest request) {
        if (null == file) {
            return new JsonResponseDto<>(0, "文件为空", "");
        } else {
            String fileName = FileUtil.upLoadFile(file,request,Constants.PICTURES);
            if(fileName.equals("")){
                return new JsonResponseDto<>(STATUE_FAIL, "上传失败","0");
            }else {
                return new JsonResponseDto<>(STATUE_OK, "上传成功", fileName);
            }

        }
    }

    @Override
    public JsonResponseDto postPictures(int postId,CommonsMultipartFile[] files, HttpServletRequest request) {
        List<String> fileAddress=FileUtil.upLoadFiles(files,request,Constants.PICTURES);
        if(null==fileAddress){
            return  new JsonResponseDto<>(Constants.STATUE_FAIL,"上传失败","");
        }else {
            for (String address : fileAddress) {
                PictureDto pictureDto=new PictureDto();
                pictureDto.setPostPictureInfoId(postId);
                pictureDto.setPictureAddress(address);
                int index=iPictureMapper.postPicture(pictureDto);
                if(0>=index){
                    return  new JsonResponseDto<>(Constants.STATUE_FAIL,"上传失败","");
                }
            }
            return  new JsonResponseDto<>(Constants.STATUE_OK,"上传成功","");
        }
    }
}
