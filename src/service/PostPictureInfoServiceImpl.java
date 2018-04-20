package service;

import dto.JsonResponseDto;
import dto.PageDto;
import dto.PostPictureInfoDto;
import mapper.IPostPictureInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import utils.Constants;

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
            String fileName=loadFile(file,request,Constants.PICTURE_COVER);
            if(fileName.equals("")){
                return new JsonResponseDto<>(STATUE_FAIL, "上传失败", "");
            }else {
                postPictureInfoDto.setCover(File.separator + Constants.PICTURE_COVER +  File.separator+loadFile(file,request,Constants.PICTURE_COVER));
                int index=iPostPictureInfoMapper.postPicture(postPictureInfoDto);
                if(0>=index){
                    return new JsonResponseDto<>(STATUE_FAIL, "上传失败", "");
                }else {
                    return new JsonResponseDto<>(STATUE_OK, "上传成功", postPictureInfoDto);
                }
            }
    }

    @Override
    public JsonResponseDto deletePostPicture(int postPictureId) {
        int index = iPostPictureInfoMapper.deletePostPicture(postPictureId);
        if (0 >= index) {
            return new JsonResponseDto<>(Constants.STATUE_FAIL, "删除失败", "0");
        } else {
            return new JsonResponseDto<>(Constants.STATUE_OK, "删除成功", "1");
        }
    }

    @Override
    public JsonResponseDto preUpdateCover(CommonsMultipartFile file, HttpServletRequest request) {
        String fileName=loadFile(file,request,Constants.PICTURE_COVER);
        if(fileName.equals("")){
            return new JsonResponseDto<>(STATUE_FAIL, "上传失败", "");
        }else {
            return new JsonResponseDto<>(STATUE_OK, "上传成功", fileName);
        }
    }

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


    private String loadFile(CommonsMultipartFile file,HttpServletRequest request,String fileAddress) {
        String fileName = "";
        if (null == file) {
            return fileName;
        } else {
            ServletContext servletContext = request.getSession().getServletContext();
            //上传位置
            String path = servletContext.getRealPath(File.separator + fileAddress) + File.separator;   //设定文件保存的目录
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            if (!file.isEmpty()) {
                fileName = file.getOriginalFilename();
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    fos = new FileOutputStream(path + fileName);
                    is = file.getInputStream();
                    byte[] b = new byte[1024 * 1024];
                    int len;
                    while ((len = is.read(b)) != -1) {
                        fos.write(b, 0, len);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    fileName = "";
                } catch (IOException e) {
                    e.printStackTrace();
                    fileName = "";
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
        }
        return (File.separator + fileAddress + File.separator+fileName);
    }

}

