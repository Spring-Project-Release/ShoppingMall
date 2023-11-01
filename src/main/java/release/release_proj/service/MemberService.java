package release.release_proj.service;

import release.release_proj.domain.MemberVO;

public interface MemberService {
    public MemberVO login(MemberVO memberVO);
    public String findMemberId(MemberVO memberVO);
    public int insertMember(MemberVO memberVO);
    public int updateMemberInfo(MemberVO memberVO);
    public int updateMemberPassword(MemberVO memberVO);
    public int deleteMember(MemberVO memberVO);
    public int isExistMemberId(String memberId);

    public MemberVO findMemberById(String memberId);
}
