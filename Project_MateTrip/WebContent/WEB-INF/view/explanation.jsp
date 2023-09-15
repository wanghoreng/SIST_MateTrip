<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<span>PASS</span> : 리더가 일자별 여행 계획에 대해 동의를 요청하는 것으로<br>
    				계획이 마음에 든다면 PASS 를 선택해 계획 확정에 동의할 수 있습니다.<br><br>
    				
<span>FIX</span> : 리더의 요청에 모든 메이트가 PASS 했을 때 <br>
				   일자별 여행 계획이 자동으로 확정되는 것을 말합니다.<br><br>
				   
<span>READY</span> : 여행에 계속 참여할 의사가 있음을 표현하는 것으로<br>
    				 인원, 계획 등 방의 상태가 하나라도 바뀔 경우 자동으로 해제됩니다. 
    				 <br><br>
    	
<span>GO</span> : 리더가 계획과 인원을 최종 확정하는 것으로,<br>
				  이 인원들과 해당 계획으로 여행을 반드시 가겠다는 의사표시입니다.<br>
    			  모든 일정이 FIX 되고 동시에 모든 메이트가 READY 상태일 때 할 수 있습니다.<br>
    			  모든 방은 여행 시작예정일 하루 전까지 GO 해야 하며<br>
    			  이 기간을 넘길 경우 여행이 취소된 것으로 보아<br> 
    			  여행 시작예정일 이후 방이 자동 삭제됩니다.
    			  <br><br>
* 방의 상태 <br>
① 방에 참여하거나, 참여를 취소한 사용자가 있을 경우<br>
② 방장이 일정을 수정했을 경우 <br>
③ 일정이 자동으로 FIX 됐을 경우 <br>
④ 방장이 여행 스타일 등 방의 기본 정보를 바꾸었을 경우<br>


</body>
</html>