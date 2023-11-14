package release.release_proj.domain;

public class MemberVO {
    private String memberId; //사용자 아이디
    private String memberName;
    private String memberPassword;
    private String memberEmail;
    private String memberPhone;
    private String memberAddress;
    private String memberType;
    private String memberGrade;


    public MemberVO() {}

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getMemberGrade() {
        return memberGrade;
    }

    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberPassword='" + memberPassword + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", memberAddress='" + memberAddress + '\'' +
                ", memberType='" + memberType + '\'' +
                ", memberGrade='" + memberGrade + '\'' +
                '}';
    }
}

