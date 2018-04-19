package dto;

import java.sql.Timestamp;

public class AppInfoDto {

    private int id;
    private int version;
    private int versionMin;
    private Timestamp updateTime;
    private String versionName;
    private String updateContent;
    private String downloadAddress;

    @Override
    public String toString() {
        return "AppInfoDto{" +
                "id=" + id +
                ", version=" + version +
                ", versionMin=" + versionMin +
                ", updateTime=" + updateTime +
                ", versionName='" + versionName + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", downloadAddress='" + downloadAddress + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersionMin() {
        return versionMin;
    }

    public void setVersionMin(int versionMin) {
        this.versionMin = versionMin;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getDownloadAddress() {
        return downloadAddress;
    }

    public void setDownloadAddress(String downloadAddress) {
        this.downloadAddress = downloadAddress;
    }
}
