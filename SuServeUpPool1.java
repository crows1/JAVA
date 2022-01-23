package su.sv.addr.pool;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import su.db.ConnectionPoolBean;

public class SuServeUpPool1 extends HttpServlet{
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
	public void service(HttpServletRequest req, HttpServletResponse res)//doPost를 서비스&두겟으로바꿔야한다 
		throws ServletException, IOException { 
		int seq = -1;
		//req.setCharacterEncoding("utf-8");
        String seqStr = req.getParameter("seq");
		String s1 = req.getParameter("email");
		String s2 = req.getParameter("subject");
		String s3 = req.getParameter("content");
		//String addr = req.getParameter("addr");
		if(seqStr !=null ){
			seqStr = seqStr.trim();
			seq = Integer.parseInt(seqStr);
		}
        res.setContentType("text/html;charset=utf-8"); 
		PrintWriter pw = res.getWriter();
		pw.println("<script>");

		ConnectionPoolBean pool = null;
		Connection con = null; 
		Statement stmt = null;
		String sql = "update BOARD set email = '"+s1+"', subject = '"+s2+"', content = '"+s3+"' where SEQ = '"+SuServeUpPool.seq+"'";
			try{
				pool = getPool();
				con = pool.getConnection();
				stmt = con.createStatement();				

				int i = stmt.executeUpdate(sql);
				if(i>0){
					pw.println("alert('수정성공 with Pool')");
				}else{
					pw.println("alert('수정실패 with Pool')");
					System.out.println("sql:"+sql);
				}
			}catch(SQLException se){
				pw.println("alert('수정실패 with Pool')");
			}finally{
				try{
					if(stmt != null) stmt.close();
					if(con != null) pool.returnConnection(con);
				}catch(SQLException se){}
			}
			pw.println("location.href='ssl.do'");
			pw.println("</script>");
    }
}
