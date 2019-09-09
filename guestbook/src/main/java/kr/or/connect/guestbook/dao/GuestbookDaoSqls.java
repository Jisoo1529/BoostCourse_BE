package kr.or.connect.guestbook.dao;

public class GuestbookDaoSqls {
	public static final String SELECT_PAGING = "SELECT id, name, content, regdate FROM guestbook ORDER BY id DESC limit :start, :limit";	
	public static final String DELETE_BY_ID = "DELETE FROM guestbook WHERE id = :id";
	public static final String SELECT_COUNT = "SELECT count(*) FROM guestbook";
}
/*
 * limit 사용
 * sql query중 limit을 사용하는 경우 시작 값, 끝나는 값을 설정하여  특정부분만 select해오는 부분 수행 가능함.*/
