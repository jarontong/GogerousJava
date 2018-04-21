package service;

import dto.JsonResponseDto;
import dto.PageDto;
import dto.PictureDto;
import dto.PostPictureInfoDto;
import mapper.IPictureMapper;
import mapper.IPostPictureInfoMapper;
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
public class PostPictureInfoServiceImpl implements IPostPictureInfoService {

    @Autowired
    IPostPictureInfoMapper iPostPictureInfoMapper;

    @Autowired
    IPictureMapper iPictureMapper;

    @Override
    public JsonResponseDto queryPostPictureList() {
        List<PostPictureInfoDto> postPictureInfoDtos = iPostPictureInfoMapper.queryPostPictureList();
        if (null == postPictureInfoDtos || 0 > postPictureInfoDtos.size()) {
            return new JsonResponseDto<>(Constants.STATUE_FAIL, "列表为空", "");
        } else {
            return new JsonResponseDto<>(Constants.STATUE_OK, "获取列表成功", postPictureInfoDtos);
        }
    }

    @Override
    public JsonResponseDto queryPostPictureById(int id) {
        PostPictureInfoDto postPictureInfoDto = iPostPictureInfoMapper.queryPostPictureById(id);
        if (null == postPictureInfoDto) {
            return new JsonResponseDto<>(Constants.STATUE_FAIL, "id不存在", "");
        } else {
            return new JsonResponseDto<>(Constants.STATUE_OK, "查询成功", postPictureInfoDto);
        }
    }

    @Override
    public JsonResponseDto queryPostPictureListByPage(PageDto pageDto) {
        return new JsonResponseDto<>(STATUE_OK, "查询成功", iPostPictureInfoMapper.queryPostPictureListByPage(pageDto));
    }

    //后台保存用
    @Override
    public JsonResponseDto postPicture(PostPictureInfoDto postPictureInfoDto) {
        int index = iPostPictureInfoMapper.postPicture(postPictureInfoDto);
        if (0 >= index) {
            return new JsonResponseDto<>(Constants.STATUE_FAIL, "发布失败", "");
        } else {
            return new JsonResponseDto<>(Constants.STATUE_OK, "发布成功", postPictureInfoDto.getId());
        }
    }

    @Override
    public JsonResponseDto postPictureHasCover(PostPictureInfoDto postPictureInfoDto, CommonsMultipartFile file, HttpServletRequest request) {
            String fileName= FileUtil.upLoadFile(file,request,Constants.PICTURE_COVER);
            if(fileName.equals("")){
                return new JsonResponseDto<>(STATUE_FAIL, "发布失败", "");
            }else {
                postPictureInfoDto.setCover(fileName);
                int index=iPostPictureInfoMapper.postPicture(postPictureInfoDto);
                if(0>=index){
                    return new JsonResponseDto<>(STATUE_FAIL, "发布失败", "");
                }else {
                    return new JsonResponseDto<>(STATUE_OK, "发布成功", postPictureInfoDto);
                }
            }
    }

    @Override
    public JsonResponseDto deletePostPicture(int postPictureId,HttpServletRequest request) {
        PostPictureInfoDto postPictureInfoDto=iPostPictureInfoMapper.queryPostPictureById(postPictureId);
        if(null==postPictureInfoDto){
            return new JsonResponseDto<>(Constants.STATUE_FAIL, "删除失败,id错误", "0");
        }else {
            //删除封面图片
            ServletContext servletContext = request.getSession().getServletContext();
            String path = servletContext.getRealPath(File.separator);   //设定文件保存的目录
            File file=new File(path+postPictureInfoDto.getCover());
            if(file.exists()){
                file.delete();
            }
            //删除套图图片
            List<PictureDto> pictureDtos=iPictureMapper.queryPostPictureByPostId(postPictureId);
            if(pictureDtos!=null&&pictureDtos.size()>0){
                for (PictureDto pictureDto : pictureDtos) {
                    File pictureFile=new File(path+pictureDto.getPictureAddress());
                    if(pictureFile.exists()){
                        pictureFile.delete();
                    }
                }
            }
            int index = iPostPictureInfoMapper.deletePostPicture(postPictureId);
            if (0 >= index) {
                return new JsonResponseDto<>(Constants.STATUE_FAIL, "删除失败", "0");
            } else {
                return new JsonResponseDto<>(Constants.STATUE_OK, "删除成功", "1");
            }
        }

    }


    //后台上传封面用
    @Override
    public JsonResponseDto preUpdateCover(CommonsMultipartFile file, HttpServletRequest request) {
        String fileName=FileUtil.upLoadFile(file,request,Constants.PICTURE_COVER);
        if(fileName.equals("")){
            return new JsonResponseDto<>(STATUE_FAIL, "上传失败", "");
        }else {
            return new JsonResponseDto<>(STATUE_OK, "上传成功", fileName);
        }
    }

    //后台修改信息
    @Override
    public JsonResponseDto upDatePostPictureInfo(PostPictureInfoDto postPictureInfoDto) {
        int index = iPostPictureInfoMapper.updatePostPictureInfo(postPictureInfoDto);
        if (0 >= index) {
            return new JsonResponseDto<>(Constants.STATUE_FAIL, "更新失败", "0");
        } else {
            return new JsonResponseDto<>(Constants.STATUE_OK, "更新成功", "1");
        }
    }

    @Override
    public int queryPostPictureCount() {
        return iPostPictureInfoMapper.queryPostPictureCount();
    }




}

