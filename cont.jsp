<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList,su.mv.model.AddrDTO" %>
<jsp:useBean id="addrDAO" class="su.mv.model.AddrDAO" scope="application"/>


<meta charset='utf-8'>
<style>
	table, th, td {border: 1px solid black;border-collapse: collapse;}
	th, td {padding: 5px;}
	a { text-decoration:none }
</style>
<center>
<hr width='600' size='2' noshade>
<h2>Simple Board MV</h2>
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
		AddrDTO dto1 = addrDAO.content(seq);
%>
	<tr>
		<td width='100' align='center'>글번호</td>
		<td><%=seq%></td>
	</tr>
	<tr>
		<td align='center'>글쓴이</td>
		<td><%=dto1.getWriter()%></td>
	</tr>
	<tr>
		<td align='center'>이메일</td>
		<td><%=dto1.getEmail()%></td>
	</tr>
	<tr>
		<td align='center'>글제목</td>
		<td><%=dto1.getSubj()%></td>
	</tr>
	<tr>
		<td align='center'>글내용</td>
		<td><%=dto1.getCont()%></td>
	</tr>
	<tr>
		<th width='100' align='center'>날짜</th>
		<td><%=dto1.getRdate()%></td>
		</tr>
	
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
