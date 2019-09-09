package kr.or.connect.guestbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuessNumberController {
	@GetMapping(path="/guess")
	public String guess(@RequestParam(name="number", required=false) Integer number,
			HttpSession session, //spring에서 세션 생성도한번에 진행함. 
			ModelMap model) {
				String message = null;
				
				if(number==null) {
					session.setAttribute("count",0);
					session.setAttribute("random",(int)(Math.random()*100)+1);
					message = "Guess the number";
				}
				else {
					int random = (Integer)session.getAttribute("random");
					int count = (Integer)session.getAttribute("count");
					
					if(random<number) {
						message = "Random number is smaller than the input number";
						session.setAttribute("count",++count);
					}else if(random> number) {
						message = "Random number is bigger than the input number";
						session.setAttribute("count",++count);
					}else {
						message = "correct!! You tried "+ ++count+" times. The number is "+number;
						session.removeAttribute("count");
						session.removeAttribute("random");
					}
				}
		model.addAttribute("message",message);
		return "guess";
	}
}

/*세션 생성 및 세션 얻기
 *HttpSession session = request.getSession();
 *세션 값 저장
 *setAttribute(String name, Object Value)
 *세션 값 조회
 *String value = (String)session.getAttribute("id");
 */