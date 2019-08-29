package kr.or.connect.mvcexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //method를 post형식으로 받도록 함. 
public class PlusController {
	@GetMapping(path="/plusform")
	public String plusform() {
		return "plusForm";  
	}
}
