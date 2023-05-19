package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);//저장
        return "redirect:/";// 첫번째 페이지로 넘어감
    }

    //회원 목록 컨트롤러 추가
    @GetMapping(value = "/members")
    public String list(Model model) { //model이라는 객체를 통해서 화면에 전달
        List<Member> members = memberService.findMembers();//JPA에서 JPQL로 모든 멤버를 조회
        model.addAttribute("members", members);//"members"라는 키를 이용해서 리스트 꺼냄, model에 담음
        return "members/memberList";//실행할 뷰 이름을 반환
    }
}
