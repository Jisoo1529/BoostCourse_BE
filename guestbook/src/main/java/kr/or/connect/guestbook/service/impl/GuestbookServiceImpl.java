package kr.or.connect.guestbook.service.impl;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.guestbook.dao.GuestbookDao;
import kr.or.connect.guestbook.dao.LogDao;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.dto.Log;
import kr.or.connect.guestbook.service.GuestbookService;

@Service //Service layer
public class GuestbookServiceImpl implements GuestbookService {
	@Autowired //알아서 생성해서 주입함.
	GuestbookDao guestbookDao;
	
	@Autowired
	LogDao logDao;
	
//	@Override //method수행시 guestbook목록 가져옴. 리스트를 읽고 리턴해줌.
//	@Transactional//읽기만 하는 method의 경우에는 이 어노테이션으로 readOnly라는 connection사용.
//	public List<Guestbook> getGuestbooks(Integer start){ 
//		List<Guestbook> list = guestbookDao.selectAll(start, GuestbookService.LAST); //limit은 service인터페이스에서 5로 지정해둠. 보여주고 싶은 만큼 설정해면 됨.
//		return list;
//	}
	
	@Override
	@Transactional
	public List<Guestbook> getGuestbooks(Integer start) {
		List<Guestbook> list = guestbookDao.selectAll(start, GuestbookService.LIMIT);
		return list;
	}
	
	@Override
	@Transactional(readOnly = false)//readOnly면 적용되지 않으므로 false로 변경해야함. 
	public int deleteGuestbook(Long id, String ip) {
		int deleteCount = guestbookDao.deleteById(id);
		Log log = new Log(); //삭제된 기록을 남기기 위한 로그.
		log.setIp(ip);
		log.setMethod("delete");
		log.setRegdate(new Date());
		logDao.insert(log);//logDao에 삭제로그 삽입.
		return deleteCount;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Guestbook addGuestbook(Guestbook guestbook, String ip) {
		guestbook.setRegdate(new Date());
		Long id = guestbookDao.insert(guestbook);
		guestbook.setId(id);
		
//		if(1 == 1)
//			throw new RuntimeException("test exception");
//			
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("insert");
		log.setRegdate(new Date());
		logDao.insert(log);
		
		
		return guestbook;
	}
	
	@Override
	public int getCount() {
		return guestbookDao.selectCount();
	}
}
