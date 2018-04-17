package mapper;


import dto.PictureDto;


import java.util.List;

public interface IPictureMapper {

     int postPicture(PictureDto PictureDto);

     int deletePicture(int pictureId);

     List<PictureDto> queryPostPictureByPostId(int postId);

     int updatePostPictureInfo(PictureDto PictureDto);
}
