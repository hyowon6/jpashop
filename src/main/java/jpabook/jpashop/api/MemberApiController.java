package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController// @Controller @ResponseBody 합친 하나의 어노테이션
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //조회 V1: 응답 값으로 엔티티를 직접 외부에 노출한다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {//list의 member를 가지고 json으로 바뀔 것
        return memberService.findMembers();
    }

    //조회 V2: 응답 값으로 엔티티가 아닌 별도의 DTO를 반환한다.
    @GetMapping("/api/v2/members")
    public Result membersV2() {//list member -> list memberDto 바꿈
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                //member 엔티티에서 이름을 꺼내와서 DTO에 넣고
                .collect(Collectors.toList());//list로 바꿈

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {// Result 객체를 사용하여 object 타입으로 데이터를 감싸고 반환
        private T data;//data 필드에는 list가 들어감
    }
    // list를 컬렉션이나 바로 내면 배열타입으로 나갈 거기 때문에 유연성이 떨어짐

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;//고객의 이름 정보만 반환
    }


    // v1: 회원 등록 api
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);//member 객체에 대한 값을 다 채워서 넘겨줌
        return new CreateMemberResponse(id);
    }

    //v2
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //간단한 이름 수정 API
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse  updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data //응답 값
    static class CreateMemberResponse{
        private Long id;//등록된 id 값 반환

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
