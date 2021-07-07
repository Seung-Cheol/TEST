package board_ex.model;



import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class BoardDao
{
	
	String url="jdbc:mysql://kic.cj7mov3fe2u4.ap-northeast-2.rds.amazonaws.com/";
	String userName = "admin";
	String password = "rladlscjf0!";
	String dbName = "PRAC05";


	// 데이터베이스 할당 싱글톤 패턴 ----------------------------------------------
	private BoardDao() throws Exception {
		// 1. 드라이버 로딩 코드
		Class.forName("com.mysql.jdbc.Driver");
	}
	static BoardDao dao=null;
	public static BoardDao getInstance() throws Exception {
		if(dao==null) dao=new BoardDao();
		return dao;
	}
	//---------------------------------------------------------------------

	// DB Connect 함수	---------------------------------------------------
	private Connection driveConnect() throws SQLException {
		String url ="jdbc:mysql://kic.cj7mov3fe2u4.ap-northeast-2.rds.amazonaws.com/";
		String userName = "admin";
		String password = "rladlscjf0!";
		String dbName = "PRAC05";

		Connection con = DriverManager.getConnection(url+dbName,userName,password);
		return con;
	}
	
	
	/************************************************
	 * 함수명 : getDate
	 * 역할 :	지정된 포맷으로 date를 가져오는 함수
	 * 인자 :	
	 * 리턴값 : times
	*/
	public String getDate() {
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				
		Calendar time = Calendar.getInstance();

		String times = format2.format(time.getTime());
		        
		return times;
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
		Connection con = null;
		try{

			con	= driveConnect();
			//* sql 문장 만들기
			String putQuery		= "INSERT INTO board_ex(title,writer,content,regdate,cnt,pass) VALUES (?,?,?,?,?,?) ";  

			ps = con.prepareStatement( putQuery );
			//* sql 문장의 ? 지정하기
			ps.setString(1, rec.getTitle());
			ps.setString(2, rec.getWriter());
			ps.setString(3, rec.getContent());
			ps.setString(4, getDate());
			ps.setInt(5, 0);
			ps.setString(6, rec.getPass());
			int insertedCount = ps.executeUpdate();			
			
			

			return insertedCount;
			
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 입력시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( stmt != null ) { try{ stmt.close(); } catch(SQLException ex){} }
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
		List<BoardVO> mList = new ArrayList<BoardVO>();
		boolean isEmpty = true;
		Connection con = null;
		try{
			con	= driveConnect();
			// * sql 문장만들기
			String sql = "SELECT * FROM board_ex";
			
			// * 전송객체 얻어오기
			ps = con.prepareStatement( sql );
			// * 전송하기
			rs = ps.executeQuery();
			// * 결과 받아 List<BoardVO> 변수 mList에 지정하기
			while(rs.next()) {
				isEmpty = false;
				BoardVO vo = new BoardVO();
				vo.setSeq(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setWriter(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegdate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				vo.setPass(rs.getString(7));
				mList.add(vo);
			}
			
			if( isEmpty ) return Collections.emptyList();
			
			System.out.println("리스트는 정상출력");
			
			sql = "ALTER TABLE board_ex AUTO_INCREMENT=1";
			Statement stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter1번 완료");
			
			sql = "SET @COUNT = 0";
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter2번 완료");
			
			sql = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter3번 완료");
			return mList;
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
		
		BoardVO rec = new BoardVO();
		Connection con = null;
		try{

			con	= driveConnect();
			String sql = "SELECT * FROM board_ex WHERE seq=?";
			
			// * 전송객체 얻어오기
			ps = con.prepareStatement( sql );
			ps.setInt(1, seq);
			rs = ps.executeQuery();

			if(rs.next()) {
				rec.setSeq(rs.getInt(1));
				rec.setTitle(rs.getString(2));
				rec.setWriter(rs.getString(3));
				rec.setContent(rs.getString(4));
				rec.setRegdate(rs.getString(5));
				rec.setCnt(rs.getInt(6));
				rec.setPass(rs.getString(7));
			}
			
			
			
			return rec;
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 글번호에 의한 레코드 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
	}

	//--------------------------------------------
	//#####	 게시글 보여줄 때 조회수 1 증가
	public void increaseReadCount( int seq ) throws BoardException
	{

		PreparedStatement ps = null;
		Connection con = null;
		int rs;
		try{

			con	= driveConnect();
			String sql = "UPDATE board_ex SET cnt = cnt+1 WHERE seq=?";
			
			// * 전송객체 얻어오기
			
			ps = con.prepareStatement( sql );
			ps.setInt(1, seq);
			rs = ps.executeUpdate();
			
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
		Connection con = null;
		try{

			con	= driveConnect();
			// * sql 문장만들기
			String sql = "UPDATE board_ex SET title=?, content=?,pass=? WHERE seq=?";
			// * 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(1, rec.getTitle());
			ps.setString(2, rec.getContent());
			ps.setString(3, rec.getPass());
			ps.setInt(4, rec.getSeq());
			
			sql = "ALTER TABLE board_ex AUTO_INCREMENT=1";
			Statement stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter1번 완료");
			
			sql = "SET @COUNT = 0";
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter2번 완료");
			
			sql = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter3번 완료");

			
			return ps.executeUpdate();
		
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
		Connection con = null;
		try{

			con	= driveConnect();
			// * sql 문장만들기
			String sql = "DELETE FROM board_ex WHERE seq=? AND pass=?";
			// * 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setInt(1, seq);
			ps.setString(2, pass);
			
			sql = "ALTER TABLE board_ex AUTO_INCREMENT=1";
			Statement stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter1번 완료");
			
			sql = "SET @COUNT = 0";
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter2번 완료");
			
			sql = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter3번 완료");
			
			
			return ps.executeUpdate();
			
		}catch( Exception ex ){
			throw new BoardException("게시판 ) 게시글 수정시 오류  : " + ex.toString() );	
		} finally{
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}
		
	}
	
	public int pageCount() throws BoardException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try{
			con	= driveConnect();
			String sql = "SELECT count(*) from board_ex";
			
			// * 전송객체 얻어오기
			ps = con.prepareStatement( sql );
			rs = ps.executeQuery();
			int count = 0;
			if(rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 글번호에 의한 레코드 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
		
	}
	
	public ArrayList<BoardVO> getPageList(int start, int length) throws BoardException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try{
			con	= driveConnect();
			String sql = "SELECT * FROM board_ex ORDER BY SEQ DESC LIMIT ?,? ";
			
			// * 전송객체 얻어오기
			ps = con.prepareStatement( sql );
			ps.setInt(1, start);
			ps.setInt(2, length);
			rs = ps.executeQuery();
			
			ArrayList<BoardVO> list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setSeq(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setWriter(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegdate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				vo.setPass(rs.getString(7));
				list.add(vo);
			}
			sql = "ALTER TABLE board_ex AUTO_INCREMENT=1";
			Statement stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter1번 완료");
			
			sql = "SET @COUNT = 0";
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter2번 완료");
			
			sql = "UPDATE board_ex SET seq = @COUNT:=@COUNT+1";
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("alter3번 완료");
			
			return list;
		}catch( Exception ex ){
			throw new BoardException("게시판 ) DB에 글번호에 의한 레코드 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
	}
	
}