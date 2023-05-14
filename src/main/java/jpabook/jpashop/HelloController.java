package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")//hello라는 url로 오면 이 controller 호출
    public String hello(Model model) {//controller에서 model에 data를 실어서 뷰에 넘길 수 있음
        model.addAttribute("data", "hello!!");//data 키의 값을 hello!!로 넘길 것
        return "hello";//.html이 자동으로 붙음
    }
}
