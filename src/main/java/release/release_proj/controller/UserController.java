package release.release_proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.MemberVO;
import release.release_proj.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" }, allowCredentials = "true")
@RestController
public class UserController {
    @Autowired
    private MemberService memberService;

    //로그인
    @PostMapping("/user/login")
    public ResponseEntity login(@RequestBody HashMap<String, Object> requestJsonHashMap, HttpServletRequest request) throws Exception{
        System.out.println(requestJsonHashMap);
        String memberId = (String) requestJsonHashMap.get("memberId");
        String memberPassword = (String) requestJsonHashMap.get("memberPassword");
        System.out.println("id : "+ memberId + " pw : "+ memberPassword);

        MemberVO memberVO = new MemberVO();
        memberVO.setMemberId(memberId);
        memberVO.setMemberPassword(memberPassword);
        MemberVO rvo = memberService.login(memberVO);

        if (rvo != null) { //로그인 성공
            HttpSession session = request.getSession(true);
            session.setAttribute("userInfo", rvo); //세션 바인딩
            return new ResponseEntity(rvo, HttpStatus.OK);
        } else { //로그인 실패
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    //이름과 폰번호로 아이디 찾기
    @GetMapping(value = "/user", params = {"name", "phone"})
    public ResponseEntity findUserId(@RequestParam String name, @RequestParam String phone) throws Exception {
        System.out.println("아이디 찾기 요청 들어옴");
        MemberVO member = new MemberVO();
        member.setMemberName(name);
        member.setMemberPhone(phone);
        String memberId = memberService.findMemberId(member);

        if (memberId != null) {
            return new ResponseEntity(memberId, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    // 회원가입
    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody HashMap<String, Object> requestJsonHashMap, HttpSession session) throws Exception{
        MemberVO vo = new MemberVO();
        vo.setMemberId((String) requestJsonHashMap.get("memberId"));
        vo.setMemberEmail((String) requestJsonHashMap.get("email"));
        vo.setMemberPassword((String) requestJsonHashMap.get("password"));
        vo.setMemberName((String) requestJsonHashMap.get("username"));
        vo.setMemberPhone((String) requestJsonHashMap.get("phone"));
        vo.setMemberAddress((String) requestJsonHashMap.get("address"));


        System.out.println(vo);
        //DB insert
        int result = memberService.insertMember(vo);

        if(result != 0) {
            return new ResponseEntity(vo.getMemberId(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    //아이디 중복 확인
    @GetMapping(value="/user/duplicate", params={"memberId"})
    public ResponseEntity isExistUserId(@RequestParam String memberId) throws Exception{
        System.out.println(memberId);

        int result = memberService.isExistMemberId(memberId);

        if(result == 0) { //중복되는 경우
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else //사용가능한 아이디인 경우
        {
            return new ResponseEntity(memberId, HttpStatus.OK);
        }
    }

}
