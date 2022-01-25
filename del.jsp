<%@ page contentType="text/html;charset=utf-8" import="javax.sql.DataSource, java.sql.*"%>
<jsp:useBean id="dbcp" class="su.dbcp.DbcpBean" scope="application"/>

<%
	DataSource ds = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = "delete from BOARD where SEQ=?";
	String seqStr = request.getParameter("seq");
		int seq = -1;
		if(seqStr == null){
			response.sendRedirect("ssl.jsp");
			return;
		}
		seqStr = seqStr.trim();
		if(seqStr.length() == 0){
			response.sendRedirect("ssl.jsp");
			return;
		}else{
			try{
				seq = Integer.parseInt(seqStr);
			}catch(NumberFormatException nfe){
				response.sendRedirect("ssl.jsp");
				return;
			}
		}
%>
		<script>
<%
		try{
			ds = dbcp.getDs();
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
			pstmt.executeUpdate();
			if(pstmt !=null) pstmt.close();
			if(con != null) con.close();
		}catch(SQLException se){
		}
		response.sendRedirect("list.jsp");
%>
