<%@ page contentType="text/html;charset=utf-8"%>
<jsp:useBean id="addrDAO" class="su.mv.model.AddrDAO" scope="application"/>
<jsp:useBean id="dto" class="su.mv.model.AddrDTO"/>
<jsp:setProperty name="dto" property="*"/>

<%
	boolean flag = addrDAO.insert(dto);
%>
    <script>
        if(<%=flag%>){
            alert("입력 성공(mv)");
        }else {
        	alert("입력 실패(mv)");
        }
        location.href='list.jsp';
	</script>