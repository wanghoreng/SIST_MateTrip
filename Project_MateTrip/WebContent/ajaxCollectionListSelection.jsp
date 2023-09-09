<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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

	var loc_memo_num = "";
	
	$(function()
	{
		
		$(".clickedModal").click(function()
		{
			loc_memo_num = this.value;
		});
		
		$(".report").click(function()
		{
			//alert("신고테스트");
			
			$.ajax(
			{				
				type: "GET"
				,data:
				{
					"report_reason_num" : $("#report_reason_num").val()
					, "content" : $("#content").val()
					, "reporter" : $("#reporter").val()
					, "loc_memo_num" : loc_memo_num
				}
				,url: "collectionreport.action"
				,success: function(args)
				{
					$(".reportCancle").click();
					alert("신고 접수가 완료되었습니다.");
				}
				,error: function(e)
				{
					alert(e.responseText);
				}
			});
		});
	});



</script>

</head>
<body>
<c:forEach var="oneplan" items="${param.selection == 0 ? myCollectionList : collectionList }">
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
	
	<ul class="shadow p-2 mb-3 bg-body rounded list-group list-group-horizontal rounded-0 bg-white" >
		<!-- 공백 -->
		<!-- 동그라미 한줄 -->
		<li class="list-group-item ps-3 pe-0 py-1 rounded-0 border-0 bg-transparent"> <!-- ps: padding-left pe:padding-right py:padding-right -->
			<span class="d-inline-flex align-items-center justify-content-center  rounded-circle m-1 me-2" 
			style="background-color:${(fn:substring(oneplan.loc_memo_num, 8, 9)=='L') ? 'orange' : 'green' }; width: 20px; height: 20px;">
			</span>
		</li>
		<!-- 장소 한줄 -->
		<li class="list-group-item px-3 py-1 d-flex align-items-center flex-grow-1 border-0 bg-transparent" id="${oneplan.loc_memo_num }">
			<p class="lead fw-normal mb-0">${not flag ? oneplan.loc_content : "신고접수"}</p>
		</li>
		<!-- 버튼 한줄 -->		
		<li class="list-group-item ps-3 pe-10 py-1 rounded-0 border-0 bg-transparent" id="btn${oneplan.loc_memo_num }">
			<c:if test="${fn:substring(oneplan.loc_memo_num, 8, 9)=='L'}">
				<c:if test="${planner.id_num == myId }">
					<select class="form-select btn-outline-success" role= "button"
					id="${oneplan.loc_memo_num }" onchange="insertIntoPlanner(this)">
						<option value="" disabled="disabled" style="display: none;" selected="selected">플래너로</option>
						<c:forEach var="plannerday" begin="1" end="${planner.days }" step="1">
							<option value="${plannerday }">${plannerday }일차</option>
						</c:forEach>
					</select>	
				</c:if>
				<button type="submit" class="btn btn-primary" id="${oneplan.loc_content }" style="background-color: 	#32a852; border:none;
				display: ${oneplan.mem_join_num == my_join_num ? 'inline' : 'none'};" onclick="placemodify(this)" value="${oneplan.loc_memo_num }">수정</button>
			</c:if>
			<c:if test="${fn:substring(oneplan.loc_memo_num, 8, 9)!='L'}">
				<button type="submit" class="btn btn-primary modifyBtn" id="${oneplan.loc_content }" style="background-color: 	#32a852; border:none;
				display: ${oneplan.mem_join_num == my_join_num ? 'inline' : 'none'};" ${flag ? "disabled='disabled'" : '' } onclick="memomodify(this)" value="${oneplan.loc_memo_num }">수정</button>
			</c:if>
			<button type="submit" class="btn btn-danger" 
			style="background-color: 	#FF8C00; border:none; display: ${oneplan.mem_join_num == my_join_num ? 'inline' : 'none'};" 
			value="${oneplan.loc_memo_num }" ${flag ? "disabled='disabled'" : '' } onclick="removePlan(this.value)">삭제</button>
			<c:if test="${fn:substring(oneplan.loc_memo_num, 8, 9)!='L'}">
				<button type="button" class="btn btn-danger clickedModal" data-toggle="modal" data-target="#reportModal"
				style="border:none; display: ${oneplan.mem_join_num != my_join_num ? 'inline' : 'none'};" 
				value="${oneplan.loc_memo_num }">신고</button>
			</c:if>
		</li>
	</ul> <!-- 일정 1개 끝 -->
</c:forEach>
	<!-- 신고하기 모달창 -->
	<div class="modal fade" id="reportModal" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
			<form action="collectionreport.action">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">해당 게시글을 신고하시겠습니까?</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<span style="color: red; font-size: 14px">허위 신고의 경우
							신고자에게 제재가 가해질 수 있습니다.</span><br>
						<br>
						<div class="row">
	
							<div class="col-md-3">
								<span class="sinGoTxt align-middle">신고사유</span>
							</div>
							<div class="col-md-9">
								<select class="form-select"
									aria-label="Default select example" style="width: 350px"
									id="report_reason_num" name="report_reason_num">
									<option selected>===========선택===========</option>
									<option value="1">스팸, 홍보, 도배</option>
									<option value="2">욕설 및 선정적인 내용</option>
									<option value="3">도용, 명예훼손 및 비방, 초상권 및 저작권 침해</option>
									<option value="4">범죄</option>
								</select>
							</div>
						</div>
						<br> <span style="float: left">신고상세내용</span><br>
						<textarea rows="3" cols="59" id="content" name="content"></textarea>
						<input type="hidden" id="reporter" value="${myId }">			
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary report">신고하기</button>
						<button type="reset" class="btn btn-secondary reportCancle"
							data-dismiss="modal">취소하기</button>
					</div>
				</div>
			</form>
		</div>
	</div>

<!-- 뒷 배경을 가리는 투명한 레이어 -->
<div class="modal-backdrop show" id="backdrop" style="display: none;"></div>

</body>
</html>