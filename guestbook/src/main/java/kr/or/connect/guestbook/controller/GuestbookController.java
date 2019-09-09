package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.service.GuestbookService;

@Controller
public class GuestbookController {
	@Autowired
	GuestbookService guestbookService;
	
	@GetMapping(path="/list")
	public String list(@RequestParam(name="start", required=false, defaultValue="0") int start,
					   ModelMap model,
					   HttpServletRequest request, 	
					   HttpServletResponse response){	
		//Cookie 설정
		String value = null;
		boolean find = false;
		Cookie[] cookies = request.getCookies();//실제 클라이언트가 내가 원하는 쿠키를 가지고 있는지 확인해야함.request한테 getCookie메서드 이용시 클라이어늩에게서 쿠키배열 얻어올 수 있음.
		if(cookies != null) {//null이 없다면 첫 요청이 들어왔을 때 오류발생함.
			for(Cookie cookie : cookies) {
				if("count".equals(cookie.getName())) {//Cookie에서 count 발견시 find를 true로 변경.
					find = true;
					value = cookie.getValue();
					break;
				}
			}
		}
		
      
		if(!find) {
			value = "1";//find는 첫요청시 false상태이므로 true로 변경함.
		}else { // 쿠키를 찾은 경우
			try {
				int i = Integer.parseInt(value);
				value = Integer.toString(++i);//쿠키는 String으로 된 value를 가질 수 있음.
			}catch(Exception ex) {
				value = "1";
			}
		}
		
   
		Cookie cookie = new Cookie("count", value);//새로운 쿠키를 생성
		cookie.setMaxAge(60*60*24); //쿠키가 유지되는 시간을 설정함. -1을 넣는 경우 브라우저가닫힐 때 쿠키도 닫힘.
		cookie.setPath("/"); // 현재 / 경로 이하에 모두 쿠키 적용. 
		response.addCookie(cookie);
		
		
		// start로 시작하는 방명록 목록 구하기
		List<Guestbook> list = guestbookService.getGuestbooks(start);
		
		// 전체 페이지수 구하기
		int count = guestbookService.getCount();
		int pageCount = count / GuestbookService.LIMIT;
		if(count % GuestbookService.LIMIT > 0)
			pageCount++;
		
		// 페이지 수만큼 start의 값을 리스트로 저장
		// 예를 들면 페이지수가 3이면
		// 0, 5, 10 이렇게 저장된다.
		// list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
		List<Integer> pageStartList = new ArrayList<>();
		for(int i = 0; i < pageCount; i++) {
			pageStartList.add(i * GuestbookService.LIMIT);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("cookieCount",value);
		return "list";
	}
	
		@PostMapping(path="/write")
		public String write(@ModelAttribute Guestbook guestbook,
				HttpServletRequest request) {
			String clientIp = request.getRemoteAddr();
			System.out.println("clientIp : " + clientIp);
			guestbookService.addGuestbook(guestbook, clientIp);
			return "redirect:list";
	}
		
		@GetMapping(path="/delete") //list.jsp의 delete를 실행했을 때  URL로 요청을 받아옴.
		public String delete(@RequestParam(name="id",required=true)Long id,
				@SessionAttribute("isAdmin")String isAdmin,
				HttpServletRequest request,
				RedirectAttributes redirectAttr){
			if(isAdmin==null||!"true".equals(isAdmin)) { //세션이 true가 아니거나 null인경우 error메세지 출력
				redirectAttr.addFlashAttribute("errorMessage","Login is failed");
				return "redirect:loginform";
			}
			String clientIp = request.getRemoteAddr(); //guestbookserviceimpl > delete안에 있는 log를 삭제하기 위해서 필요함. 
			guestbookService.deleteGuestbook(id, clientIp);
			return "redirect:list";
		
		}
}

/*@CookieValue 어노테이션 사용하는 경우.
@Controller
public class GuestbookController {
	@Autowired
	GuestbookService guestbookService;
	
	@GetMapping(path="/list")
	public String list(@RequestParam(name="start", required=false, defaultValue="0") int start, ModelMap model,
					   @CookieValue(value="count", defaultValue = "1",required = true)String value,
					   HttpServletResponse response){	
      
		try {
				int i = Integer.parseInt(value);
				value = Integer.toString(++i);//쿠키는 String으로 된 value를 가질 수 있음.
			}catch(Exception ex) {
				value = "1";
			}
		}
		
		Cookie cookie = new Cookie("count",value);//새로운 쿠키를 생성
		cookie.setMaxAge(60*60*24*365); //쿠키가 유지되는 시간을 설정함. -1을 넣는 경우 브라우저가닫힐 때 쿠키도 닫힘.
		cookie.setPath("/"); // 현재 / 경로 이하에 모두 쿠키 적용. 
		response.addCookie(cookie);
		
		
		// start로 시작하는 방명록 목록 구하기
		List<Guestbook> list = guestbookService.getGuestbooks(start);
		
		// 전체 페이지수 구하기
		int count = guestbookService.getCount();
		int pageCount = count / GuestbookService.LIMIT;
		if(count % GuestbookService.LIMIT > 0)
			pageCount++;
		
		// 페이지 수만큼 start의 값을 리스트로 저장
		// 예를 들면 페이지수가 3이면
		// 0, 5, 10 이렇게 저장된다.
		// list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
		List<Integer> pageStartList = new ArrayList<>();
		for(int i = 0; i < pageCount; i++) {
			pageStartList.add(i * GuestbookService.LIMIT);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("cookieCount",value);
		return "list";
	
	
		@PostMapping(path="/write")
		public String write(@ModelAttribute Guestbook guestbook,HttpServletRequest request) {
			String clientIp = request.getRemoteAddr();
			System.out.println("clientIp : " + clientIp);
			guestbookService.addGuestbook(guestbook, clientIp);
			return "redirect:list";
	}
}
*/