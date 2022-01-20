package su.sv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;

public class SuServeUp extends HttpServlet{
	Connection con;
	PreparedStatement pstmt;
	public void init(){
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
		String usr = "servlet";
		String pwd = "java";
		//String sql = "select subject,content,email from BOARD where SEQ=?";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,usr,pwd);
			//pstmt = con.prepareStatement(sql);
		}catch(ClassNotFoundException cnfe){
			System.out.println("#Oracle driver loading failed");
		}catch(SQLException se){
		System.out.println("init()");
		}
	}
	public void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		
		pw.println("<meta charset='utf-8'>");
		pw.println("<style>");
			pw.println("table, th, td {border: 1px solid black;border-collapse: collapse;}");
			pw.println("th, td {padding: 5px;}");
			pw.println("a { text-decoration:none }");
		pw.println("</style>");
		pw.println("<body onload='javascript:document.f.email.focus();'>");
			pw.println("<center>");
				pw.println("<hr width='600' size='2' noshade>");
				pw.println("<h2>Simple Board with Servlet</h2>");
				pw.println("<a href='ssl.do'>글목록</a>");
				pw.println("<hr width='600' size='2' noshade>");
			pw.println("</center>");
		pw.println("<form name='f' method='post' action='up.do'>");
		pw.println("<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>");
		
		ResultSet rs = null;
		String email =  req.getParameter("email");
		String subj =  req.getParameter("subject");
		String content =  req.getParameter("content");
		String sql = "select subject,content,email from BOARD where SEQ=?";
		String sql1 = "update board set subj = '"+subj+"' , content ='"+content+"' , email='"+email+"' where seq = sql";
		try{
			pstmt = con.prepareStatement(sql);
		}catch(SQLException se){}
		try{
			rs = pstmt.executeQuery();
				while(rs.next()){
					String subj1 = rs.getString(1);
					String content1 = rs.getString(2);
					String email1 = rs.getString(3);
					pw.println("<td width='30%' align='center'>글쓴이</td>");
						//pw.println("td align='center'><input type='text' name='name' size='60' value='강감찬' disabled></td>");
					pw.println("</tr>");
					pw.println("<tr>");
						pw.println("<td width='30%' align='center'>이메일</td>");
						pw.println("<td align='center'><input type='text' name='email' size='60' value='"+email1+"'></td>");
					pw.println("</tr>");
					pw.println("<tr>");
						pw.println("<td width='30%' align='center'>글제목</td>");
						pw.println("<td align='center'><input type='text' name='subject' size='60' value='"+subj1+"'></td>");
					pw.println("</tr>");
					pw.println("<tr>");
						pw.println("<td width='30%' align='center'>글내용</td>");
						pw.println("<td align='center'><textarea name='content' rows='5' cols='53'>"+content1+"</textarea></td>");
					pw.println("</tr>");
				}
		}catch(SQLException se){
		pw.println(""+se);
		System.out.println("ResultSet()"+se);
		}finally{
			try{
				if(rs !=null) rs.close();
			}catch(SQLException se){
			System.out.println("finally()");
			}		
		}		
				pw.println("<tr>");
					pw.println("<td colspan='2' align='center'>");
						pw.println("<input type='submit' value='수정'>");
					pw.println("</td>");
				pw.println("</tr>");
				pw.println("</table>");
			pw.println("</form>");
		pw.println("</body>");
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