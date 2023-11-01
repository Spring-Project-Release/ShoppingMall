package release.release_proj.domain;

public class MemberVO {
    private String memberId; //사용자 아이디
    private String email; //이메일
    private String password; //비밀번호
    private String name; //이름
    private String phone; //핸드폰 번호
    private String address; //주소

    public MemberVO() {}

    public MemberVO(String memberId, String email, String password, String name, String phone, String address) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "memberId='" + memberId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
