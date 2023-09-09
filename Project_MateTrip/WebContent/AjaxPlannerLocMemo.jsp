<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<!-- <link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"
	integrity="sha384-fbbOQedDUMZZ5KreZpsbe1LCZPVmfTnH7ois6mU1QK+m14rQ1l2bGBq41eYeM/fS"
	crossorigin="anonymous"></script>
	
<link rel="canonical" href="https://getbootstrap.kr/docs/5.2/examples/dashboard/">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/j
s/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css"> -->
<script type="text/javascript">

	//위 버튼 클릭시 추가★
	$(".up").click(function() 
	{
		var num =  $(this).attr("id").split("/")[0];
		var day = $(this).attr("name");
		
		var params = "num="+num+"&p=u&day="+day;
		
		$.ajax(
		{
			type:"GET"								
			, url:"ordermove.action"				
			, data:params
			, success:function(args)
			{
				$(".plannerlocMemo").html(args);
				
			}											
			, error:function(e)
			{
				alert(e.responseText);
			}
		});
		
	});
	
	// 아래 버튼 클릭시 추가★
	$(".down").click(function() 
	{
		var num =  $(this).attr("id").split("/")[0];
		var day = $(this).attr("name");
		
		var params = "num="+num+"&p=d&day="+day;
			
			$.ajax(
			{
				type:"GET"								
				, url:"ordermove.action"				
				, data:params
				, success:function(args)
				{
					$(".plannerlocMemo").html(args);
				}											
				, error:function(e)
				{
					alert(e.responseText);
				}
			});
		
	});

</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

				<c:forEach var="m" begin="1" end="${planner.days }" step="1">
        			<ul class="p-2 bg-body rounded list-group list-group-horizontal rounded-0 bg-transparent" style="padding-left: 0;">
        				<li class="list-group-item py-1 d-flex align-items-center flex-grow-1 border-0 bg-transparent" style="padding-left: 0;">
            				<p class="lead fw-normal mb-0">
            					<a name="${m }n" class="dateBtn"><!-- 책갈피 -->
              				<button type="button" class="btn btn-light me-2 shadow noClick">
 							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar-check" viewBox="0 0 16 16" style="color: orange;">
							  <path d="M10.854 7.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7.5 9.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
							  <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
							</svg>
             						<span style="font-weight: bold;">
             							&nbsp;${m }일차
             						</span>
							</button>
            					</a>
            				</p>
          				</li>
        			</ul>
            				<c:set var="sequence" value="0"></c:set>
	            			<c:forEach var="oneplan" items="${plannerLocMemo }"  varStatus="plannerStatus">
	            				<c:if test="${oneplan.day == m }">
	            					<c:if test="${fn:substring(oneplan.loc_memo_num, 5, 6)=='L'}">
	            						<c:set var="sequence" value="${sequence + 1 }"></c:set>
			            			</c:if>
			            			<!-- 일정 불러와서 for문 돌리기 --------------------------------------------- -->	            
			            			<!-- 일정 한줄 -->
			            			
			            			<!-- flag 변수 설정 -->
									<c:set var="flag" value="false"/>
									
									
									<c:forEach var="reportList" items="${reportReceipt }">
										<!-- 신고 접수된 피드일 때 -->
										<c:if test="${reportList == oneplan.loc_memo_num }">
											<!-- flag -> true -->
											<c:set var="flag" value="true"/>
										</c:if>
									</c:forEach>	
			            			
		            				<ul class="shadow p-2 mb-3 bg-body rounded list-group list-group-horizontal rounded-0 bg-white">
				            			<!-- 공백 -->
				             			<!-- 동그라미 한줄 -->
			              				<li class="list-group-item ps-3 pe-0 py-1 rounded-0 border-0 bg-transparent"> <!-- ps: padding-left pe:padding-right py:padding-right -->
							      			<span class="d-inline-flex align-items-center justify-content-center  rounded-circle m-1 me-2 my-4" 
							      			style="background-color:${(fn:substring(oneplan.loc_memo_num, 5, 6)=='L') ? 'orange' : 'green' }; width: 20px; height: 20px;">
											<c:if test="${fn:substring(oneplan.loc_memo_num, 5, 6)=='L'}">
												${sequence }
												<input type="hidden" id="order" name="order" value="${oneplan.sequence }">
			            					</c:if>
							     			</span>
				              			</li>
				              			<!-- 장소 한줄 -->
				              			<li class="list-group-item px-3 py-1 d-flex align-items-center flex-grow-1 border-0 bg-transparent position-relative" id="${oneplan.loc_memo_num }" style="width: 60%;">
				                			<p class="lead fw-normal mb-0" >${not flag ? oneplan.loc_content : "신고접수"}</p>
				                			
				              			</li>
				              			<!-- 버튼 한줄 -->
				              				<li class="list-group-item ps-3 pe-10 py-2 px-0 rounded-0 border-0 bg-transparent float-end justify-content-center" id="btn${oneplan.loc_memo_num }" >
				              				
				              				<!--  fix 된 일자는 수정 버튼 disabled -->
									    	<c:if test="${oneplan.checkfix =='1'}">
					              				<button type="submit" class="btn btn-primary modifyBtn" id="${oneplan.loc_content }" style="background-color: 	#32a852; border:none;
					              				display: ${planner.id_num == myId ? 'inline' : 'none'};" disabled value="${oneplan.loc_memo_num }">수정</button>
				              				</c:if>
				              			 	<c:if test="${oneplan.checkfix =='0'}">
					              				<c:if test="${fn:substring(oneplan.loc_memo_num, 5, 6)=='L'}">
					              				<button type="submit" class="btn btn-primary modifyBtn" id="${oneplan.loc_content }/${planner.region}" style="background-color: #32a852; border:none;
					              				display: ${planner.id_num == myId ? 'inline' : 'none'};" onclick="placemodify(this)" value="${oneplan.loc_memo_num }">수정</button>
						              			</c:if>
						              			<c:if test="${fn:substring(oneplan.loc_memo_num, 5, 6)!='L'}">
						              			<button type="submit" class="btn btn-primary modifyBtn" id="${oneplan.loc_content }" style="background-color: 	#32a852; border:none;
						              			display: ${planner.id_num == myId ? 'inline' : 'none'};" onclick="memomodify(this)" ${flag ? "disabled='disabled'" : '' } value="${oneplan.loc_memo_num }">수정</button>
						              			</c:if>
				              				</c:if>
				              				</li>
				              				<li class="list-group-item ps-3 pe-10  px-0 py-2 rounded-0 border-0"  id="btn2${oneplan.loc_memo_num }">
				              				<!-- fix된 일자는 삭제 불가  disabled 추가 ★--> 
					              				<c:if test="${oneplan.checkfix =='1'}">
							            			<button type="submit" class="btn btn-danger" 
							            			style="background-color: 	#FF8C00; border:none; display: ${planner.id_num == myId ? 'inline' : 'none'};" 
							            			value="${oneplan.loc_memo_num }" disabled onclick="removePlan(this.value)">삭제</button>
							            		</c:if>
							            		<c:if test="${oneplan.checkfix =='0'}">
							            			<button type="submit" class="btn btn-danger" 
							            			style="background-color: 	#FF8C00; border:none; display: ${planner.id_num == myId ? 'inline' : 'none'};" 
							            			value="${oneplan.loc_memo_num }" ${flag ? "disabled='disabled'" : '' } onclick="removePlan(this.value)">삭제</button>
							            		</c:if>
						            		</li>
						            		<li class="list-group-item ps-3 pe-10 px-0 py-2 rounded-0 border-0 ">
						            			<div style="text-decoration: none; border: 0px">
				                					<button type="button" class="btnorder up shadow-sm" id="${oneplan.loc_memo_num }/${sequence }/up" name="${m }" style="text-decoration: none; border: 0px; color: #48AFB4; 
				                					display: ${planner.id_num == myId ? 'inline' : 'none'};" >▲</button><br>
				                					<button type="button"  class="btnorder down shadow-sm" id="${oneplan.loc_memo_num }/${sequence }/down" name="${m }" style="text-decoration: none; border: 0px; color: #48AFB4;
				                					display: ${planner.id_num == myId ? 'inline' : 'none'};">▼</button>
				                				</div>
				                			</li>
				                			
				                			
			            			</ul> <!-- 일정 1개 끝 -->
			            		</c:if>
		            		</c:forEach>
		            	</c:forEach>
		            	<br>


</body>
</html>