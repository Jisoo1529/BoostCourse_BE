package kr.or.connect.guestbook.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
	@GetMapping(path="/uploadform")
	public String uploadform() {
		return "uploadform";
	}
	
	@PostMapping(path="/upload")
	public String upload(@RequestParam("file")MultipartFile file) {//upload한 파일이 들어오는 경로.사용자가 선택한 파일이 multipartfile 객체로 들어옴.
		
		System.out.println("파일 이름: " + file.getOriginalFilename());//getOriginalFilename으로 파일이름 구함.
		System.out.println("파일 크기: " + file.getSize());//파일 사이즈 구함.
		
		try(
				FileOutputStream fos = new FileOutputStream("c:/tmp/" + file.getOriginalFilename());
				InputStream is = file.getInputStream();//파일 입력받고 업로드 할 수 있는 통로
			){
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while((readCount = is.read(buffer))!=-1) {
				fos.write(buffer,0,readCount);
			}
			
		}catch(Exception ex) {
			throw new RuntimeException("file Save Error");
		}
		return "uploadok";
	}
	
	@GetMapping(path="/download")
	public void download(HttpServletResponse response) {
		String fileName = "connect.png";
		String saveFileName = "c:/tmp/connect.png"; 
		String contentType = "image/png";
		int fileLength = 1116303;//파일명, 위치 저장
		
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");//브라우저가 캐시를 읽지 못하도록 no cache
        response.setHeader("Expires", "-1;");
        
        try(
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream out = response.getOutputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
            		out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
	}
}
