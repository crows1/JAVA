<%@ page contentType="text/html;charset=utf-8" import="javax.sql.DataSource, java.sql.*"%>
<jsp:useBean id="dbcp" class="su.dbcp.DbcpBean" scope="application"/>

<meta charset='utf-8'>
<style>
	table, th, td {border: 1px solid black;border-collapse: collapse;}
	th, td {padding: 5px;}
	a { text-decoration:none }
</style>
<center>
<hr width='600' size='2' noshade>
<h2>Simple Board with JSP DBCP</h2>
&nbsp;&nbsp;&nbsp;
<a href='input.jsp'>글쓰기</a>
<hr width='600' size='2' noshade>
<table border='1' width='600' align='center' cellpadding='3'>

<%	
	String seqStr = request.getParameter("seq");
		int seq = -1;
		if(seqStr == null){
			response.sendRedirect("list.jsp");
			return;
		}
		seqStr = seqStr.trim();
		if(seqStr.length() == 0){
			response.sendRedirect("list.jsp");
			return;
		}else{
			try{
				seq = Integer.parseInt(seqStr);
			}catch(NumberFormatException nfe){
				response.sendRedirect("list.jsp");
				return;
			}
		}
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int seq1 = 0;
		String sql = "select * from BOARD where SEQ="+seq+"";
		try{
			ds = dbcp.getDs();
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				seq1 = rs.getInt(1);
				String writer = rs.getString(2);
				String email = rs.getString(3);
				String subj = rs.getString(4);
				String content = rs.getString(5);
				Date rdate = rs.getDate(6);
%>
	<tr>
		<td width='100' align='center'>글번호</td>
		<td><%=seq1%></td>
	</tr>
	<tr>
		<td align='center'>글쓴이</td>
		<td><%=writer%></td>
	</tr>
	<tr>
		<td align='center'>이메일</td>
		<td><%=email%></td>
	</tr>
	<tr>
		<td align='center'>글제목</td>
		<td><%=subj%></td>
	</tr>
	<tr>
		<td align='center'>글내용</td>
		<td><%=content%></td>
	</tr>
	<tr>
		<th width='100' align='center'>날짜</th>
		<td><%=rdate%></td>
		</tr>
	
<%

			}			
		}catch(SQLException se){
			System.out.println("ResultSet()"+seq+se);
		}finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se){
			System.out.println("finally()");
			}		
		}
%>
</table>
<hr width='600' size='2' noshade>
<b>
<a  href='up.jsp?seq=<%=seq%>'>수정</a>
 | 
<a href='del.jsp?seq=<%=seq%>'>삭제</a>
 | 
<a href='list.jsp'>목록</a>
</b>
<hr width='600' size='2' noshade>
</center>
