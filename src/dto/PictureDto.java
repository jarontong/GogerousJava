package dto;

public class PictureDto {

    private int id;
    private int postPictureInfoId;
    private int sort;
    private String pictureAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostPictureInfoId() {
        return postPictureInfoId;
    }

    public void setPostPictureInfoId(int postPictureInfoId) {
        this.postPictureInfoId = postPictureInfoId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPictureAddress() {
        return pictureAddress;
    }

    public void setPictureAddress(String pictureAddress) {
        this.pictureAddress = pictureAddress;
    }

    @Override
    public String toString() {
        return "PictureDto{" +
                "id=" + id +
                ", postPictureInfoId=" + postPictureInfoId +
                ", sort=" + sort +
                ", pictureAddress='" + pictureAddress + '\'' +
                '}';
    }
}
