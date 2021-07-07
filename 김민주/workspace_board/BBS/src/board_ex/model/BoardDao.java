package board_ex.model;



import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class BoardDao
{
	
	// Single Pattern 
	private static BoardDao instance;
	
	// DB 연결시  관한 변수  
	private static final String 	dbDriver	=	"com.mysql.cj.jdbc.Driver";
	private static final String		dbUrl		=	"jdbc:mysql://kic.cj7mov3fe2u4.ap-northeast-2.rds.amazonaws.com/";
	private static final String		dbUser		=	"admin";
	private static final String		dbPass		=	"rladlscjf0!";
	private static final String		dbName		=	"PRAC05";
	
	
	private Connection	 		con;
	
	//--------------------------------------------
	//#####	 객체 생성하는 메소드 
	public static BoardDao	getInstance() throws BoardException
	{
		if( instance == null )
		{
			instance = new BoardDao();
		}
		return instance;
	}
	
	private BoardDao() throws BoardException
	{
	
		try{
			
			/********************************************
			1. 오라클 드라이버를 로딩
				( DBCP 연결하면 삭제할 부분 )
		*/
			Class.forName( dbDriver );	
		}catch( Exception ex ){
			throw new BoardException("DB 연결시 오류  : " + ex.toString() );	
		}
		
	}
	
	/************************************************
	 * 함수명 : insert
	 * 역할 :	게시판에 글을 입력시 DB에 저장하는 메소드 
	 * 인자 :	BoardVO
	 * 리턴값 : 입력한 행수를 받아서 리턴
	*/
	public int insert( BoardVO rec ) throws BoardException
	{

		ResultSet rs = null;
		Statement stmt	= null;
		PreparedStatement ps = null;
		try{

			con	= DriverManager.getConnection( dbUrl+dbName, dbUser, dbPass );
			
			//* sql 문장 만들기
			String SQL		= "INSERT INTO board_ex(writer,title,content,regdate,pass,cnt) VALUES(?,?,?,?,?,?)";  
			ps		= con.prepareStatement( SQL );
			//* sql 문장의 ? 지정하기
			ps.setString(1, rec.getWriter());
			ps.setString(2, rec.getTitle());
			ps.setString(3, rec.getContent());
			ps.setString(4, getDate());
			ps.setString(5, rec.getPass());
			ps.setInt(6, rec.getCnt());
			int insertedCount = ps.executeUpdate();
			// 게시글번호 초기화 작업 
			SQL = "ALTER TABLE board_ex AUTO_INCREMENT=1";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter1번 완료");
	         
	         SQL = "SET @COUNT = 0";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter2번 완료");
	         
	         SQL = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter3번 완료");

			return insertedCount;
		
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 입력시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}
		
	}


	/************************************************
	 * 함수명 : selectList
	 * 역할 :	전체 레코드를 검색하는 함수
	 * 인자 :	없음
	 * 리턴값 : 테이블의 한 레코드를 BoardVO 지정하고 그것을 ArrayList에 추가한 값
	*/

	public List<BoardVO> selectList() throws BoardException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		List<BoardVO> mList = new ArrayList<BoardVO>();
		boolean isEmpty = true;
		
		try{

			con	= DriverManager.getConnection( dbUrl+dbName, dbUser, dbPass );
			
			// * sql 문장만들기
			String SQL = "select * from board_ex";
			// * 전송객체 얻어오기
			ps = con.prepareStatement(SQL);
			// * 전송하기
			rs = ps.executeQuery();
			// * 결과 받아 List<BoardVO> 변수 mList에 지정하기
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setSeq(rs.getInt("seq"));
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getString("regdate"));
				vo.setCnt(rs.getInt("cnt")); // 조회수
				vo.setPass(rs.getString("pass"));
				mList.add(vo);
				isEmpty = false;
			}
			// 게시글번호 초기화 작업 
			SQL = "ALTER TABLE board_ex AUTO_INCREMENT=1";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter1번 완료");
		        
		    SQL = "SET @COUNT = 0";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter2번 완료");
			         
		    SQL = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter3번 완료");
			if( isEmpty ) {
				return Collections.emptyList();
			}
			
			return mList;
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 목록 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
	}
	
	
	
	// 현재 페이지에 보여줄 메세지 목록 얻어오는 메서드
	public List<BoardVO> selectList(int startRow,int countViewPage) throws BoardException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		List<BoardVO> mList = new ArrayList<BoardVO>();
		boolean isEmpty = true;
		
		try{

			con	= DriverManager.getConnection( dbUrl+dbName, dbUser, dbPass );
			
			// * sql 문장만들기
			String SQL = "select * from board_ex limit ?,?";
			// * 전송객체 얻어오기
			ps = con.prepareStatement(SQL);
			ps.setInt(1, startRow);
			ps.setInt(2, countViewPage);
			// * 전송하기
			rs = ps.executeQuery();
			// * 결과 받아 List<BoardVO> 변수 mList에 지정하기
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setSeq(rs.getInt("seq"));
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getString("regdate"));
				vo.setCnt(rs.getInt("cnt")); // 조회수
				vo.setPass(rs.getString("pass"));
				mList.add(vo);
				isEmpty = false;
			}
			// 게시글번호 초기화 작업 
			SQL = "ALTER TABLE board_ex AUTO_INCREMENT=1";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter1번 완료");
		        
		    SQL = "SET @COUNT = 0";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter2번 완료");
			         
		    SQL = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter3번 완료");
			
			if( isEmpty ) {
				return Collections.emptyList();
			}
			
			return mList;
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 목록 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
	}
	
	// 레코드 총 개수
	public int getTotalCount() throws BoardException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try{

			con	= DriverManager.getConnection( dbUrl+dbName, dbUser, dbPass );
			
			// * sql 문장만들기
			String SQL = "select count(*) as CNT from board_ex";
			// * 전송객체 얻어오기
			ps = con.prepareStatement(SQL);
			// * 전송하기
			rs = ps.executeQuery();
			// * 결과 받아 List<BoardVO> 변수 mList에 지정하기
			if(rs.next()) {
				count = rs.getInt("CNT");
			}
		
			
			return count;
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 목록 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
	}
	
	//--------------------------------------------
	//#####	 게시글번호에 의한 레코드 검색하는 함수
	public BoardVO selectById(int seq) throws BoardException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		BoardVO rec = new BoardVO();
		
		try{
			con	= DriverManager.getConnection( dbUrl+dbName, dbUser, dbPass );
			// * sql 문장만들기
			String SQL = "SELECT * FROM board_ex where seq=?";
			// * 전송객체 얻어오기
			ps = con.prepareStatement(SQL);
			ps.setInt(1, seq);
			// * 전송하기
			rs = ps.executeQuery();
			// * 결과 받아 BoardVO변수 rec에 지정하기
			if(rs.next()) {
			rec.setTitle(rs.getString("title"));
			rec.setWriter(rs.getString("writer"));
			rec.setContent(rs.getString("content"));
			rec.setRegdate(rs.getString("regdate"));
			rec.setCnt(rs.getInt("cnt"));
			rec.setPass(rs.getString("pass"));
//			increaseReadCount(seq);	
			
			// 게시글번호 초기화 작업 
			SQL = "ALTER TABLE board_ex AUTO_INCREMENT=1";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter1번 완료");
		        
		    SQL = "SET @COUNT = 0";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter2번 완료");
			         
		    SQL = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
		    stmt = con.createStatement();
		    stmt.execute(SQL);
//		    System.out.println("alter3번 완료");
			
			return rec;
			}
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 글번호에 의한 레코드 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}
			return null;
	}

	//--------------------------------------------
	//#####	 게시글 보여줄 때 조회수 1 증가
	public void increaseReadCount( int seq ) throws BoardException
	{

		PreparedStatement ps = null;
		try{

			con	= DriverManager.getConnection( dbUrl+dbName, dbUser, dbPass );
			// * sql 문장만들기
			String SQL = "UPDATE board_ex set cnt = cnt+1 where seq = ? ";
			// * 전송객체 얻어오기
			ps = con.prepareStatement(SQL);
			ps.setInt(1, seq);
			// * 전송하기
			ps.executeUpdate();
			
		}catch( Exception ex ){
			throw new BoardException("게시판 ) 게시글 볼 때 조회수 증가시 오류  : " + ex.toString() );	
		} finally{
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}
		
	}
	//--------------------------------------------
	//#####	 게시글 수정할 때
	public int update( BoardVO rec ) throws BoardException
	{

		PreparedStatement ps = null;
		Statement stmt = null;
		try{

			con	= DriverManager.getConnection( dbUrl+dbName, dbUser, dbPass );
			// * sql 문장만들기
			String SQL = "UPDATE board_ex set title=?, content=? where seq = ? and pass= ?";
			// * 담기
			ps = con.prepareStatement(SQL);
			// * 전송객체 얻어오기
			ps.setString(1, rec.getTitle());
			ps.setString(2, rec.getContent());
			ps.setInt(3, rec.getSeq());
			ps.setString(4, rec.getPass());
			
			int rs = ps.executeUpdate();
//			System.out.println("성공시 결과값" + rs);
//			System.out.println("seq :" + rec.getSeq());
//			System.out.println("pass :" + rec.getPass());
			 SQL = "ALTER TABLE board_ex AUTO_INCREMENT=1";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter1번 완료");
	         
	         SQL = "SET @COUNT = 0";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter2번 완료");
	         
	         SQL = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter3번 완료");
	         return rs;
		
		}catch( Exception ex ){
			throw new BoardException("게시판 ) 게시글 수정시 오류  : " + ex.toString() );	
		} finally{
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}
		
	}
	
	
	//--------------------------------------------
	//#####	 게시글 삭제할 때
	public int delete( int seq, String pass ) throws BoardException
	{

		PreparedStatement ps = null;
		Statement stmt = null;
		try{

			con	= DriverManager.getConnection( dbUrl+dbName, dbUser, dbPass );
		
			// * sql 문장만들기
			String SQL = "delete from board_ex where pass = ? and seq = ?";
			// * 전송객체 얻어오기
			ps = con.prepareStatement(SQL);
			ps.setString(1, pass);
			ps.setInt(2, seq);
			int rs = ps.executeUpdate();	// 성공하면 0을 반환한다.
//			System.out.println(rs);
			
			// 게시글 초기화
			 SQL = "ALTER TABLE board_ex AUTO_INCREMENT=1";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter1번 완료");
	         
	         SQL = "SET @COUNT = 0";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter2번 완료");
	         
	         SQL = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
	         stmt = con.createStatement();
	         stmt.execute(SQL);
//	         System.out.println("alter3번 완료");
			return rs;
			
			
		}catch( Exception ex ){
			throw new BoardException("게시판 ) 게시글 수정시 오류  : " + ex.toString() );	
		} finally{
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}
		
	}
//	게시글에 작성시간을 가져오는 함수
	public String getDate() {
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				
		Calendar time = Calendar.getInstance();

		String times = format2.format(time.getTime());
		        
		return times;
	}

	
	
}