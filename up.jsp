<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList,su.mv.model.AddrDTO" %>
<jsp:useBean id="addrDAO" class="su.mv.model.AddrDAO" scope="application"/>

<meta charset='utf-8'>
<style>
	table, th, td {
	   border: 1px solid black;
	   border-collapse: collapse;
	}
	th, td {
	   padding: 5px;
	}
	a { text-decoration:none }


<%			
		int seq=-1;
        String seqStr = request.getParameter("seq");
		if(seqStr != null) {
			seqStr = seqStr.trim();
			seq = Integer.parseInt(seqStr);
		}
		AddrDTO dto = addrDAO.content(seq);	
%>
				</style>
				<body onload='javascript:document.f.email.focus();'>
				<center>
				<hr width='600' size='2' noshade>
				<h2>이클립스 update</h2>
				<a href='list.jsp'>글목록</a>
				<hr width='600' size='2' noshade>
					
				<form name='f' method='post' action='update2.jsp?seq=<%=seq%>'>
				<input type='hidden' name='seq' value='<%=seq%>'>
				<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>
				<td width='30%' align='center'>글쓴이</td>
					<td align='center'><input type='text' name='name' size='60' value=<%=dto.getWriter()%> disabled></td>
					</tr>
					 <tr>
					 <td width='30%' align='center'>이메일</td>
					 <td align='center'><input type='text' name='email' size='60' value=<%=dto.getEmail()%>></td>
					 </tr>
					 <tr>
					 <td width='30%' align='center'>글제목</td>
					 <td align='center'><input type='text' name='subject' size='60' value=<%=dto.getSubj()%>></td>
					 </tr>
					 <tr>
					 <td width='30%' align='center'>글내용</td>
					 <td align='center'><textarea name='content' rows='5' cols='53'><%=dto.getCont()%></textarea></td>
					 </tr> 

			<tr>
					 <td colspan='2' align='center'>
					 <input type='submit' value='수정'>
					 </td>
			 </tr>


</table>
</form>
</body>
