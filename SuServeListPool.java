package su.sv.addr.pool;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import su.db.ConnectionPoolBean;

public class SuServeListPool extends HttpServlet{
	//sevice에 connectiopool이 있는건 최악의 프로그램 겁나 느림

	private ConnectionPoolBean getPool() throws SQLException{
		ServletContext application = this.getServletContext();
		ConnectionPoolBean pool = (ConnectionPoolBean)application.getAttribute("pool");
		if(pool==null){
			try{
				pool = new ConnectionPoolBean();
				application.setAttribute("pool",pool);				
			}catch(ClassNotFoundException cnfe){
				System.out.println("드라이버 로딩 실패");
			}
		}
		return pool;
	} 
	public void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.println("<meta charset='utf-8'>");
		pw.println("<style>");
			pw.println("table, th, td {");
				pw.println("border: 1px solid black;");
				pw.println("border-collapse: collapse;");
			
			pw.println("th, td { padding: 5px;	}");
			pw.println("a { text-decoration:none }");
		pw.println("</style>");

		pw.println("<center>");
			pw.println("<hr width='600' size='2' noshade>");
				pw.println("<h2>Simple Board with Pool</h2>");
				pw.println("&nbsp;&nbsp;&nbsp;");
				//링크
				pw.println("<a href='input.html'>글쓰기</a>");
				pw.println("&nbsp;&nbsp;&nbsp;");
				//링크
				pw.println("<a href='../'>인덱스</a>");
			pw.println("<hr width='600' size='2' noshade>");
		pw.println("</center>");

			pw.println("<table border='1' width='600' align='center' cellpadding='2'>");
			pw.println("<tr>");
				pw.println("<th align='center' width='10%'>글번호</th>");
				pw.println("<th align='center' width='15%'>작성자</th>");
				pw.println("<th align='center' width='30%'>이메일</th>");
				pw.println("<th align='center' width='30%'>글제목</th>");
				pw.println("<th align='center' width='15%'>날짜</th>");
		pw.println("</tr>");
		
		ConnectionPoolBean pool = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		String sql = "select * from board order by seq desc";
		try{
			pool = getPool();
			con = pool.getConnection();
			stmt = con.createStatement(); 
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String email = rs.getString(3);
				String subj = rs.getString(4);
				String content = rs.getString(5);
				Date rdate = rs.getDate(6);
				pw.println("<tr>");
					pw.println("<td align='center'>"+seq+"</td>");
					pw.println("<td align='center'>"+writer+"</td>");
					pw.println("<td align='center'>"+email+"</td>");
					pw.println("<td align='center'><a href='con.do?seq="+seq+"'>"+subj+"</a></td>");					
					pw.println("<td align='center'>"+rdate+"</td>");					
			
				pw.println("</tr>");
			}
		}catch(SQLException se){
			System.out.println("ResultSet()");
		}finally{
			try{
				if(rs !=null) rs.close();
				if(stmt != null)stmt.close();
				if(con !=null)pool.returnConnection(con);
			}catch(SQLException se){
			System.out.println("finally()");
			}
		}
			pw.println("</table>");
		pw.println("<hr width='600' size='2' noshade>");

	}
}