package service;

import dto.AppInfoDto;
import dto.JsonResponseDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface ILikeInfoService {

    JsonResponseDto addLike(int userId,int postId);

    JsonResponseDto deleteLike(int userId,int postId);


}
