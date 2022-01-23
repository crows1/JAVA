package su.sv.addr.pool;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import su.db.ConnectionPoolBean;

public class SuServeUpPool extends HttpServlet{
	static int seq = -1;
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
		
        String seqStr = req.getParameter("seq");
		if(seqStr !=null ){
			seqStr = seqStr.trim();
			seq = Integer.parseInt(seqStr);
		}
		PrintWriter pw = res.getWriter();
		pw.println("<meta charset='utf-8'>");
		pw.println("<style>");
			pw.println("table, th, td {border: 1px solid black;border-collapse: collapse;}");
			pw.println("th, td { padding: 5px;}");
			pw.println("a { text-decoration:none }");
		pw.println("</style>");
		pw.println("<body onload='javascript:document.f.email.focus();'>");

		pw.println("<center>");
			pw.println("<hr width='600' size='2' noshade>");
				pw.println("<h2>Simple Board with Servlet</h2>");
					pw.println("<a href='con.do'>글목록</a>");
			pw.println("<hr width='600' size='2' noshade>");
		pw.println("</center>");
		pw.println("<form name='f' method='post' action='up1.do'>");
		
		ConnectionPoolBean pool= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="select writer,email,subject,content from board where seq ="+seq+"";
	
		try{
			pool = getPool();
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);	//준비된애
			rs = pstmt.executeQuery();
			while (rs.next()){
					String name = rs.getString(1);
					String email = rs.getString(2);
					String subj = rs.getString(3);
					String content = rs.getString(4);
			pw.println("<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>");
				pw.println("<td width='30%' align='center'>글쓴이</td>");
				pw.println("<td align='center'><input type='text' name='name' size='60' value='"+name+"' disabled></td>");
			pw.println("</tr>");
			pw.println("<tr>");
				pw.println("<td width='30%' align='center'>이메일</td>");
				pw.println("<td align='center'><input type='text' name='email' size='60' value='"+email+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
				pw.println("<td width='30%' align='center'>글제목</td>");
				pw.println("<td align='center'><input type='text' name='subject' size='60' value='"+subj+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
				pw.println("<td width='30%' align='center'>글내용</td>");
				pw.println("<td align='center'><textarea name='content' rows='5' cols='53'>"+content+"</textarea></td>");
			pw.println("</tr>");
			
			}
		}catch(SQLException se){
			System.out.println("ResultSet()");
		}finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) pool.returnConnection(con);
			}catch(SQLException se){
			System.out.println("finally()");
			}
		}

		pw.println("<tr>");
			pw.println("<td colspan='2' align='center'>");
			pw.println("<input type='submit' value='수정' onclick='check()>'");
		pw.println("</td>");
		pw.println("</tr>");
		pw.println("</table>");
		pw.println("</form>");
		pw.println("</body>");
	
	}
	/*public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("text/html;charset=utf-8");
		//String sql ="select writer,email,subject,content from board where seq ="+seq+"";
		String sql = "update board set where";
		*/
		
		
}