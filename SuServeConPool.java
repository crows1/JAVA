package su.sv.addr.pool;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import su.db.ConnectionPoolBean;

public class SuServeConPool extends HttpServlet{
	int seq;

	private ConnectionPoolBean getPool() throws SQLException {
		ServletContext application = this.getServletContext();
		ConnectionPoolBean pool = (ConnectionPoolBean)application.getAttribute("pool");
		if(pool == null){
			try{
				pool = new ConnectionPoolBean();
				application.setAttribute("pool", pool);
			}catch(ClassNotFoundException cnfe){
				System.out.println("드라이버로딩 실패");
			}
		}
		return pool;
	}	

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("text/html;charset=utf-8");
		/*int seq1 = rs.getInt(1);
		String writer = rs.getString(2);
		String email = rs.getString(3);
		String subj = rs.getString(4);
		String content = rs.getString(5);
		Date rdate = rs.getDate(6);*/
		PrintWriter pw = res.getWriter();
		//파라미터값
		String seqStr = req.getParameter("seq");
		if(seqStr !=null ){
			seqStr = seqStr.trim();
			seq = Integer.parseInt(seqStr);
		}
		pw.println("<meta charset='utf-8'>");
		pw.println("<style>");
			pw.println("table, th, td { border: 1px solid black; border-collapse: collapse;}");
			pw.println("th, td { padding: 5px;}");
			pw.println("a { text-decoration:none }");
		pw.println("</style>");

		pw.println("<center>");
			pw.println("<hr width='600' size='2' noshade>");
				pw.println("<h2>Simple Board with Servlet</h2>");
				pw.println("&nbsp;&nbsp;&nbsp;");
				pw.println("<a href='input.html'>글쓰기</a>");
			pw.println("<hr width='600' size='2' noshade>");
		pw.println("<table border='1' width='600' align='center' cellpadding='3'>");
		
		ResultSet rs = null;
		ConnectionPoolBean pool = null;
		Statement stmt = null;
		Connection con = null; 
		String sql = "select * from BOARD where SEQ="+seq+"";
		try{

			pool = getPool();
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				int seq1 = rs.getInt(1);
				String writer = rs.getString(2);
				String email = rs.getString(3);
				String subj = rs.getString(4);
				String content = rs.getString(5);
				Date rdate = rs.getDate(6);
				pw.println("<tr>");
					pw.println("<td width='100' align='center'>글번호</td>");
					pw.println("<td>"+seq1+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
					pw.println("<td align='center'>글쓴이</td>");
					pw.println("<td>"+writer+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
					pw.println("<td align='center'>이메일</td>");
					pw.println("<td>"+email+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
					pw.println("<td align='center'>글제목</td>");
					pw.println("<td>"+subj+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");	
					pw.println("<td align='center'>글내용</td>");
					pw.println("<td>"+rdate+"</td>");			
				pw.println("</tr>");
			}
			pw.println("</table>");
				pw.println("<hr width='600' size='2' noshade>");
					pw.println("<b>");
						pw.println("<a  href='up.do?seq="+seq+"'>수정</a>");
						pw.println(" | ");
						pw.println("<a href='del.do?seq="+seq+"'>삭제</a>");
						pw.println(" | ");
						pw.println("<a href='ssl.do'>목록</a>");
					pw.println("</b>");
				pw.println("<hr width='600' size='2' noshade>");
			
		}catch(SQLException se){
			System.out.println("ResultSet()"+seq+se);
		}finally{
			try{
				if(rs !=null) rs.close();
				if(stmt != null)stmt.close();
				if(con !=null)pool.returnConnection(con);
			}catch(SQLException se){
			System.out.println("finally()");
			}
		}
		pw.println("</center>");
	}
}