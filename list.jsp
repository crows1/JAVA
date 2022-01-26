<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList,su.mv.model.AddrDTO"%>
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
</style>
<center>
	<hr width='600' size='2' noshade>
	<h2>Simple Board MV</h2>
		&nbsp;&nbsp;&nbsp;
	<a href='input.jsp'>글쓰기</a>
		&nbsp;&nbsp;&nbsp;
	<a href='../'>인덱스</a>
	<hr width='600' size='2' noshade>
	</center>
	<table border='1' width='600' align='center' cellpadding='2'>
	<tr>
		<th align='center' width='10%'>글번호</th>
		<th align='center' width='15%'>작성자</th>
		<th align='center' width='30%'>이메일</th>
		<th align='center' width='30%'>글제목</th>
		<th align='center' width='15%'>날짜</th>
	</tr>
<%
	ArrayList<AddrDTO> list = addrDAO.list();
		if(list != null){
			int size = list.size();
			if(size !=0){
				for(AddrDTO dto : list){
%>

				<tr>
					<td align='center'><%=dto.getSeq()%></td>
					<td align='center'><%=dto.getWriter()%></td>
					<td align='center'><%=dto.getEmail()%></td>
					<td align='center'>
					<a href='cont.jsp?seq=<%=dto.getSeq()%>'><%=dto.getSubj()%></a>
					</td>
					<td align='center'><%=dto.getRdate()%></td>
				</tr>
<%
			}
		}else{
%>
			<tr>
				<td colsapn='5'style="text-align:center">데이터가 하나도 엄쪄요~</td>
			</tr>
<%		
		}
	}			
		
%>

</table>
<hr width='600' size='2' noshade>
</center>