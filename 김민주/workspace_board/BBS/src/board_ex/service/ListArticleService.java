package board_ex.service;

import java.util.List;

import board_ex.model.*;

public class ListArticleService {
	
	private int totalRecCount;		// 전체 레코드 수	
	private int pageTotalCount;		// 전체 페이지 수	
	private int pageBlock=5;	// 한 화면에 표시할 페이지 개수  5
	private int startPage;	// 한 화면에 페이지 시작번호	
	private int endPage;	// 한 화면에 페이지 끝번호
//	startPage = ((pageNum-1)/pageBlock) * pageBlock + 1
//	endPage = startPage + pageBlock -1
//	
//	
//	
	private static ListArticleService instance;
	public static ListArticleService getInstance()  throws BoardException{
		if( instance == null )
		{
			instance = new ListArticleService();
		}
		return instance;
	}
	
	// 전체 페이지 숫자와 한페이지당 몇개의 레코드값이 출력될것인지 구함
	public List <BoardVO> getArticleList(int pNum) throws BoardException
	{	
		
		// 0 번부터 시작
		int startRow = (pNum*pageBlock)-(pageBlock); 
		
		 List <BoardVO> mList = BoardDao.getInstance().selectList(startRow,pageBlock);			
		return mList;
	}
		
	
	// 페이지 총 개수
	public int getTotalCount() throws BoardException{
		// 데이터베이스에서 실제 레코드 수 받아오기
		totalRecCount = BoardDao.getInstance().getTotalCount();
		// 전체 페이지 수(pageTotalCount)		
  		if(totalRecCount/pageBlock == 0){
			pageTotalCount = (totalRecCount/pageBlock);
		}else{
			pageTotalCount = (totalRecCount/pageBlock)+1;
		}
		return pageTotalCount;
		
	}
	
		
	
	
}
