package mapper;



import dto.PageDto;
import dto.PostPictureInfoDto;
import dto.UserInfoDto;

import java.util.List;

public interface IPostPictureInfoMapper {

     int postPicture(PostPictureInfoDto postPictureInfoDto);

     int deletePostPicture(int postPictureId);

     List<PostPictureInfoDto> queryPostPictureList();

     List<PostPictureInfoDto> queryPostPictureListByUserId(int userId);

     PostPictureInfoDto queryPostPictureById(int postPictureId);

     List<PostPictureInfoDto> queryPostPictureListByPage(PageDto pageDto);

     int queryPostPictureCount();

     int updatePostPictureInfo(PostPictureInfoDto postPictureInfoDto);


}
