package service;

import dto.JsonResponseDto;
import dto.PictureDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPictureService {

    JsonResponseDto postPicture(PictureDto PictureDto);

    JsonResponseDto deletePicture(int pictureId);

    JsonResponseDto queryPostPictureByPostId(int postId);

    JsonResponseDto updatePostPictureInfo(List<PictureDto> pictureDtos);

    JsonResponseDto prePostPicture(CommonsMultipartFile file, HttpServletRequest request);
}
