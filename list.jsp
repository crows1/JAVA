<%@ page contentType="text/html;charset=utf-8" import="javax.sql.DataSource, java.sql.*"%>
<jsp:useBean id="dbcp" class="su.dbcp.DbcpBean" scope="application"/>

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
   <h1>
      Address List JSP with DBCP
   </h1>
   <a href="../">인덱스</a>
      &nbsp;&nbsp;&nbsp;&nbsp; 
   <a href="input.jsp">입력폼</a>
   <table border='1' cellpadding='7' cellspacing='2' width='50%'>
       <tr>
         <th>번호</th>
         <th>이름</th>
         <th>주소</th>
         <th>날짜</th>
         <th>삭제</th>
      </tr>
<%
	  DataSource ds = null;
	  Connection con = null;
	  Statement stmt = null;
	  ResultSet rs = null;
      String sql = "select * from address order by seq desc";
      try{
    	 ds = dbcp.getDs();
  		 con = ds.getConnection();
         stmt = con.createStatement();
         rs = stmt.executeQuery(sql);
         while(rs.next()){
            int seq = rs.getInt(1);
            String name = rs.getString(2);
            String addr = rs.getString(3);
            Date rdate = rs.getDate(4);
%>
                    <tr>
                  <td align='center'><%=seq%></td>
                  <td><%=name%></td>
                  <td><%=addr%></td>
                  <td><%=rdate%></td>
                  <td align='center'><a href='del.jsp?seq=<%=seq%>'>삭제</a></td>
               </tr>
<%
          }
       }catch(SQLException se){
          System.out.println("#service() se: " + se);
       }finally{
          try{
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(con != null) con.close();
          }catch(SQLException se){}
       }
%> 
   </table>
</center>