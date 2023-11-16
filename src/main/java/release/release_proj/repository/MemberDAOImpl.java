package release.release_proj.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import release.release_proj.domain.MemberVO;
@Repository
public class MemberDAOImpl implements MemberDAO{

    @Autowired
    private SqlSession sqlSession;

    public static final String NS = "sql.member.mapper.";

    @Override
    public int insertMember(MemberVO memberVO) {
        return sqlSession.insert(NS+"insertMember", memberVO);
    }

    @Override
    public int deleteMember(MemberVO memberVO) {
        sqlSession.delete("sql.cart.mapper.deleteByItemId", memberVO.getMemberId()); //해당하는 cart부터 먼저 삭제
        return sqlSession.delete(NS+"deleteMember", memberVO);
    }

    @Override
    public int updateMemberInfo(MemberVO memberVO) {
        return sqlSession.update(NS+"updateMember", memberVO);
    }

    @Override
    public int updateMemberPassword(MemberVO memberVO) {
        return sqlSession.update(NS + "updateMemberPassword", memberVO);
    }

    @Override
    public MemberVO login(MemberVO memberVO) {
        return sqlSession.selectOne(NS + "selectMember", memberVO);
    }

    @Override
    public String findMemberId(MemberVO memberVO) {
        return sqlSession.selectOne(NS + "selectMemberId", memberVO);
    }

    @Override
    public int isExistMemberId(String memberId) {
        return sqlSession.selectOne(NS + "isExistMemberId", memberId);
    }

    @Override
    public MemberVO findMemberById(String memberId) {
        return sqlSession.selectOne(NS+"findMemberById", memberId);
    }
}
