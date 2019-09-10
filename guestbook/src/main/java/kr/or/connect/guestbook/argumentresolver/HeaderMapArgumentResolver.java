package kr.or.connect.guestbook.argumentresolver;

import java.util.Iterator;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class HeaderMapArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType()==HeaderInfo.class;//supportsParameter가 true값을 가져야 resolveArgument메소드가 호출.
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		HeaderInfo headerInfo = new HeaderInfo();//
		
		Iterator<String> headerNames = webRequest.getHeaderNames();//Native web의 형태를 Iterator형태로 꺼냄.
		while(headerNames.hasNext()) {
			String headerName = headerNames.next();
			String headerValue = webRequest.getHeader(headerName);
			System.out.println(headerName+","+headerValue);
			headerInfo.put(headerName, headerValue);//header의 이름고 값을 headerinfo에 넣고 있음. 
		}
		
		return headerInfo;
	}

}
