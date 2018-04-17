package dto;

public class UserInfoDto {
    private int id;
    private String account;
    private String avatar;
    private String password;
    private String nickname;
    private String sign;
    private int gender;
    private int focus;
    private int fans;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sign='" + sign + '\'' +
                ", gender=" + gender +
                ", focus=" + focus +
                ", fans=" + fans +
                '}';
    }
}
