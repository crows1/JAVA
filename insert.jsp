<%@ page contentType="text/html;charset=utf-8" import="javax.sql.DataSource, java.sql.*"%>
<jsp:useBean id="dbcp" class="su.dbcp.DbcpBean" scope="application"/>

<%!
	DataSource ds = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = "insert into board values(BOARD_SEQ.nextval,?,?,?,?,SYSDATE)";
	public void jspInit(){
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
		System.out.println("jspInit()");
		}
	}
	public void jspDestroy(){
		try{
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}catch(SQLException se){
		System.out.println("jspD estroy()");
		}
	}
%>
<%
		String name =  request.getParameter("name");
		String email =  request.getParameter("email");
		String subj =  request.getParameter("subject");
		String content =  request.getParameter("content");
		out.println("<script>");

		try{
			ds = dbcp.getDs();
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,name);
			pstmt.setString(2,email);
			pstmt.setString(3,subj);
			pstmt.setString(4,content);
			pstmt.executeUpdate();
			out.println("location.href='list.jsp'");
		}catch(SQLException se){
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se){}
		}
		out.println("</script>");
%>