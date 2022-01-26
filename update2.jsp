<%@ page contentType="text/html;charset=utf-8"%>
<jsp:useBean id="addrDAO" class="su.mv.model.AddrDAO" scope="application"/>
<jsp:useBean id="dto" class="su.mv.model.AddrDTO"/>
<jsp:setProperty name="dto" property="*"/>



<%		
int seq = -1;
String seqStr = request.getParameter("seq");
if(seqStr != null) {
	seqStr = seqStr.trim();
	seq = Integer.parseInt(seqStr);
	}
	boolean flag = addrDAO.update2(dto);
%>
<script>
                    if(<%=flag%>){
                    	alert("수정성공(mv)");
                    }else{
                    	alert("수정실패(mv)");
                    }
                    location.href='list.jsp';

</script>
