package service;

import dto.JsonResponseDto;
import dto.PageDto;
import dto.PostPictureInfoDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IPostPictureInfoService {

    JsonResponseDto queryPostPictureList();

    JsonResponseDto queryPostPictureById(int id);

    JsonResponseDto queryPostPictureByUserId(int userId);

    JsonResponseDto queryPostPictureListByPage(PageDto pageDto);

    JsonResponseDto postPicture(PostPictureInfoDto postPictureInfoDto);

    JsonResponseDto postPictureHasCover(PostPictureInfoDto postPictureInfoDto,CommonsMultipartFile file,HttpServletRequest request);

    JsonResponseDto updatePostPictureCover(int postPictureId ,CommonsMultipartFile file,HttpServletRequest request);

    JsonResponseDto deletePostPicture(int postPictureId,HttpServletRequest request);

    JsonResponseDto preUpdateCover(CommonsMultipartFile file, HttpServletRequest request);

    JsonResponseDto upDatePostPictureInfo(PostPictureInfoDto postPictureInfoDto);

    JsonResponseDto addView(int postPictureId);

    int queryPostPictureCount();
}
