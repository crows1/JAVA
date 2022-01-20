package su.sv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;

public class SuServeIn extends HttpServlet{
	Connection con;
	PreparedStatement pstmt;
	String sql = "insert into board values(BOARD_SEQ.nextval,?,?,?,?,SYSDATE)";
	public void init(){
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
		String usr = "servlet";
		String pwd = "java";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,usr,pwd);
			pstmt = con.prepareStatement(sql);
		}catch(ClassNotFoundException cnfe){
			System.out.println("#Oracle driver loading failed");
		}catch(SQLException se){
		System.out.println("init()");
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		String name =  req.getParameter("name");
		String email =  req.getParameter("email");
		String subj =  req.getParameter("subject");
		String content =  req.getParameter("content");
		PrintWriter pw = res.getWriter();
		pw.println("<script>");

		try{
			pstmt.setString(1,name);
			pstmt.setString(2,email);
			pstmt.setString(3,subj);
			pstmt.setString(4,content);
			pstmt.executeUpdate();
			pw.println("location.href='ssl.do'");
		}catch(SQLException se){}
		pw.println("</script>");
		//String sql = "insert into board values(BOARD_SEQ.nextval,?,?,?,?,SYSDATE)";
		
		//res.setContentType("text/html;charset=utf-8");
		//PrintWriter pw = res.getWriter();
	}
	public void destroy(){
		try{
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}catch(SQLException se){
		System.out.println("destroy()");
		}
	}
}