package memberMVC.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
   private Connection conn;
   private PreparedStatement pstmt;
   private DataSource dataFactory;

   public MemberDAO() {
      try {
//         JNDI(Java Naming and Directory Interface)에 적븐하기 위해 기본 경로(javaL/comp/env)를 지정
//         (자바 애플리케이션을 외부 디렉터리 서비스에 연결
         Context ctx = new InitialContext();
         Context envContext = (Context)ctx.lookup("java:/comp/env");
         dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
         
      } catch (Exception e) {
         System.out.println("오라클 연결 오류");
         e.printStackTrace();
      }
   }
//   회원 조회
   public List<MemberVO> listMembers(){
      List<MemberVO> memberList = new ArrayList();
      try {
         conn = dataFactory.getConnection();
         String query = "select * from membertbl order by joinDate desc";
         pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
            String id = rs.getString("id");
            String pwd = rs.getString("pwd");
            String name = rs.getString("name");
            String email = rs.getString("email");
            Date joinDate = rs.getDate("joinDate");
            MemberVO memberVO = new MemberVO(id, pwd, name, email, joinDate);
            memberList.add(memberVO);
         }
         rs.close();
         pstmt.close();
         conn.close();
      } catch (Exception e) {
         System.out.println("DB 조회 중 에러!");
         e.printStackTrace();
      }
      return memberList;
   }
//   회원 등록
   public void addMember(MemberVO memberVO) {
      try {
         conn = dataFactory.getConnection();
         String id = memberVO.getId();
         String pwd = memberVO.getPwd();
         String name = memberVO.getName();
         String email = memberVO.getEmail();
         String query = "insert into membertbl (id, pwd, name, email) values(?,?,?,?)";
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, id);
         pstmt.setString(2, pwd);
         pstmt.setString(3, name);
         pstmt.setString(4, email);
         pstmt.executeUpdate();
         pstmt.close();
         conn.close();
      } catch (Exception e) {
         System.out.println("DB 등록 중 에러!!");
         e.printStackTrace();
      }
   }
//   수정할 회원정보 찾기
   public MemberVO findMember(String _id) {
      MemberVO memFindInfo = null;
      try {
         conn = dataFactory.getConnection();
         String query = "select * from membertbl where id=?";
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, _id);
         ResultSet rs = pstmt.executeQuery();
         rs.next();
         String id = rs.getString("id");
         String pwd = rs.getString("pwd");
         String name = rs.getString("name");
         String email = rs.getString("email");
         Date joinDate = rs.getDate("joinDate");
         memFindInfo = new MemberVO(id, pwd, name, email, joinDate);
         pstmt.close();
         conn.close();
         rs.close();
      } catch (Exception e) {
         System.out.println("수정할 자료 찾는 중 에러!!");
         e.printStackTrace();
      }
      return memFindInfo;
   }
//   회원정보 수정
   public void modMember(MemberVO memberVO) {
	   	String id=memberVO.getId();
	   	String pwd=memberVO.getPwd();
	   	String name=memberVO.getName();
	   	String email=memberVO.getEmail();
	   	try {
	   			conn=dataFactory.getConnection();
	   			String query="update membertbl set pwd=?, name=?, email=? where id=?";
	   			pstmt=conn.prepareStatement(query);
	   			pstmt.setString(1, pwd);
	   			pstmt.setString(2, name);
	   			pstmt.setString(3, email);
	   			pstmt.setString(4, id);
	   			pstmt.execute();
	   			pstmt.close();
	   			conn.close();
		} catch (Exception e) {
			System.out.println("회원정보 수정중 에러 발생");
			e.printStackTrace();
		}
   }
   //회원정보 삭제
   public void delMember(String id) {
	   try {
		   conn=dataFactory.getConnection();
		   String query = "delete from membertbl where id=?";
		   pstmt=conn.prepareStatement(query);
		   pstmt.setString(1, id);
		   pstmt.execute();
		   pstmt.close();
		   conn.close();
	} catch (Exception e) {
		System.out.println("회원 정보중 삭제 에러");
		e.printStackTrace();
	}
   }
}