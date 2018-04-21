package dto;

import java.util.List;

public class PicturesListDto {

    private List<PictureDto> pictureDtoList;

    public List<PictureDto> getPictureDtoList() {
        return pictureDtoList;
    }

    public void setPictureDtoList(List<PictureDto> pictureDtoList) {
        this.pictureDtoList = pictureDtoList;
    }

    @Override
    public String toString() {
        return "PicturesListDto{" +
                "pictureDtoList=" + pictureDtoList +
                '}';
    }
}
