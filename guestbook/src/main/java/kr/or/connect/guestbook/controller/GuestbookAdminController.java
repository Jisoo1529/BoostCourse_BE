package kr.or.connect.guestbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GuestbookAdminController {
	@GetMapping(path="/loginform")
	public String loginform() {
		return "loginform";
	}
	
	@PostMapping(path="/login")//form 태그의 action부분과 동일해야함.
	public String login(@RequestParam(name="passwd",required=true)String passwd,
			HttpSession session,
			RedirectAttributes redirectAttr) {
		if("1234".contentEquals(passwd)) {
			session.setAttribute("isAdmin", "true");//비밀번호가 일치하는 경우 session이름 = isAdmin
		}else {
			redirectAttr.addFlashAttribute("errorMessage","암호가 틀렸습니다");
			return "redirect:/loginform";//redirect할때 한번만 값을 유지하기 위해서 사용함.loginform의 요청을 다시 내보낼 때 위에 있는 redirectAttr의 값을 사용. 
		}
		
		return "redirect:/list";
	}
}
