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
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Feed.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/list-groups.css">
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/feed.css">

<!-- jQuery  -->
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
 
<!-- bootstrap 소스 넣기 (bootstrap v5.0 버전)  -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

<!-- icon 부트스트랩 신버전 소스  -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">

<script type="text/javascript">

	$(function()
	{
		/* $('.peed_content').scrollTop($('.peed_content')[0].scrollHeight); */
		$(".peed_content").animate(
		{
			scrollTop: $(".peed_content")[0].scrollHeight
		}, 800);

		$(".btnDel").click(function()
		{
			if (confirm("삭제하시겠습니까?"))
			{
				$(location).attr("href", "deletefeed.action?feedNum=" + $(this).val()+"&room_num="+$("#room_num").val());
			}
		});
		
		$("#modalReport").click(function()
		{
			if (confirm("신고하시겠습니까?"))
			{
				$("#myForm").submit();
			}
		});
		
		$(".btnReport").click(function()
		{
			var str = " 님을 신고하시겠습니까?";
			$("#test").html($(this).val() + str);
			
			$("#hiddenId").val($(this).attr("id"));
			$("#hiddenFeedNum").val($(this).attr("name"));
			//alert($("#hiddenFeedNum").val());
		});
	});

</script>
<script type="text/javascript">

	function contentAjax()
	{
		var params = "content=" + $("#content").val()+"&room_num="+$("#room_num").val();
	      
		$.ajax(
		{
			type:"GET"                        
			, url:"insertcontent.action"            
			, data:params
			, success:function(args)
			{
		      	$(".peed_content").html(args);
		      	$("#content").val("");
		      	$("#content").focus();
		      	
		      	$('.peed_content').scrollTop($('.peed_content')[0].scrollHeight);
		      	
		      	$(".btnDel").click(function()
      			{
      				if (confirm("삭제하시겠습니까?"))
      				{
      					$(location).attr("href", "deletefeed.action?feedNum=" + $(this).val()+"&room_num="+$("#room_num").val());
      				}
      			});
		      	
		      	$(".btnReport").click(function()
				{
		      		var str = " 님을 신고하시겠습니까?";
					$("#test").html($(this).val() + str);
					
					$("#hiddenId").val($(this).attr("id"));
					$("#hiddenFeedNum").val($(this).attr("name"));
		      		
					$("#exampleModal").modal("show");
					
				});
		      	
			}                                 
			, error:function(e)
			{
		      	alert(e.responseText);
			}
		});
	
	}

</script>


</head>
<body>
<div class="container-fluid" style="width: 80%">
	<div>
		<jsp:include page="/WEB-INF/view/Room_Ongoing_Header.jsp"/>
	</div>
	
	<div class="p-lg-5 mx-auto my-auto peed_position_relative">
		<div class="peed_content">
			
			<c:forEach var="list" items="${feedList }">
			
			<div class="mooon">
			<c:set var="flag2" value="false"/>
				<c:forEach var="reportList" items="${reportReceipt }">
					<!-- 신고 접수된 피드일 때 -->
					<c:if test="${reportList == list.idNum }">
						<!-- flag -> true -->
						<c:set var="flag2" value="true"/>
					</c:if>
				</c:forEach>
				<div class="peed_profile" style="margin-top: 1vh; margin-bottom: 0px;">
					<a href="personalpage.action?etiId=${list.id}&room_num=${list.roomNum }" style="color: black; text-decoration: none">
						<img alt="" src="${not flag2 ? list.photo : 'images/feedCaution.png' }" style="width: 70px; height: 70px; border-radius: 50%; object-fit : cover;">
						<p style="color: white; text-align: center;">${not flag2?list.nickName: "신고접수"}</p>
					</a>
				</div>
				<div class="mooon2">
					<div class="peed_content_content">
					
						<!-- flag 변수 설정 -->
						<c:set var="flag" value="false"/>
						
						
						<c:forEach var="reportList" items="${reportReceipt }">
							<!-- 신고 접수된 피드일 때 -->
							<c:if test="${reportList == list.feedNum }">
								<!-- flag -> true -->
								<c:set var="flag" value="true"/>
							</c:if>
						</c:forEach>
						
						
						<c:if test="${flag && rank =='member'}">
							<p style="color: white; width: 800px; text-align: left; background-color: #87BDC7; border-radius: 5px;">
							본 피드 내용은 신고가 접수되어 블라인드 처리되었습니다.</p>
						</c:if>
						<c:if test="${flag && rank =='admin'}">
							<p style="color: red; width: 800px; text-align: left;">${list.content }</p>
							<p style="color: red; text-align: left; font-size: small;">신고 접수되어 블라인드 처리된 게시글입니다.</p>
						</c:if>
						<c:if test="${not flag}">
							<p style="color: white; width: 800px; text-align: left;">${list.content }</p>
						</c:if>
						
					</div>
					<div class="peed_time">
						<p style="color: white;">${list.writeTime }</p>
					</div>
					<div class="contentDelete">
						<!-- 입장퇴장 피드가 아니면서 로그인한 계정이 참여중인 멤버일 때 -->
						<c:if test="${list.nickName == feedDto.nickName && list.count== '0' && feedCount == 1}">
							<button type="button" class="btn btn-success btnDel" id="btnDel" name="btnDel" value="${list.feedNum }">삭제</button>
						</c:if>
						
						<!-- 입장퇴장 피드가 아니면서 로그인한 계정이 참여중이 아닌 멤버일 때 -->
						<c:if test="${list.nickName == feedDto.nickName && list.count== '0' && feedCount == 0}">
							<button type="button" disabled="disabled" class="btn btn-success btnDel" id="btnDel" name="btnDel" value="${list.feedNum }">삭제</button>
						</c:if>
						
						<!-- 입장퇴장 피드이면서 참여중이 아닌 멤버일 때 -->
						<c:if test="${list.nickName == feedDto.nickName && list.count== '1'}">
							<button type="button" disabled="disabled" class="btn btn-success btnDel" id="btnDel" name="btnDel" value="${list.feedNum }">삭제</button>
						</c:if>
						
						<!-- 신고 버튼 -->			
						<c:if test="${list.nickName != feedDto.nickName && list.count == '0'}">
							<button type="button" class="btn btn-danger btnReport" value="${list.nickName }" id="${list.id}" name="${list.feedNum }" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">신고</button>
						</c:if>
						
						<c:if test="${list.nickName != feedDto.nickName && list.count == '1'}">
							<button type="button" class="btn btn-danger btnReport" disabled="disabled">신고</button>
						</c:if>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>
		<div class="peed_insert">
			<div class="insert_form">
				<div style="margin-top: 2.5px;">
					<!-- 참여 중일 때 -->
					<c:if test="${feedCount == 1 }">
						<img alt="" src="${not flag2 ? feedDto.photo : 'images/feedCaution.png' }" style="width: 70px; height: 70px; border-radius: 50%; object-fit : cover;">
						<p style="color: white; text-align: center;">${not flag2?feedDto.nickName: "신고접수"}</p>
					</c:if>
					
					<!-- 참여 중이 아닐 때 -->
					<c:if test="${feedCount == 0 }">
						<img alt="" src="images/feedCaution.png" style="width: 70px; height: 70px; border-radius: 50%; object-fit : cover;">
						<p style="color: white; text-align: center;">작성 불가</p>					
					</c:if>
				</div>
				<div style="margin-top: 2vh;">
					<!-- 참여 중일 때 -->
					<c:if test="${feedCount == 1 }">
						<input type="text" class="form-control" name="content" id="content" style="text-align: left;">
					</c:if>
					
					<!-- 참여 중이 아닐 때 -->
					<c:if test="${feedCount == 0 }">
						<input type="text" class="form-control" name="content" id="content" placeholder="작성 권한이 없습니다."
						disabled="disabled" style="text-align: left;">
					</c:if>
				</div>
				<div>
					<!-- 참여 중일 때 -->
					<c:if test="${feedCount == 1 }">
						<button type="button" class="btn btn-success" id="btnSendContent" name="btnSendContent" onclick="contentAjax()">Send</button>
						<input type="hidden" id="room_num" name="room_num" value="${feedDto.roomNum }">
					</c:if>
					<!-- 참여 중이 아닐 때 -->
					<c:if test="${feedCount == 0 }">
						<button type="button" class="btn btn-success" id="btnSendContent" name="btnSendContent" disabled="disabled">Send</button>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal-backdrop show" id="backdrop" style="display: none;"></div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
      		<div class="modal-header">
        		<h1 class="modal-title fs-5" id="exampleModalLabel"></h1>
        		<span id="test"></span>
        		<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      		</div>
      		<div class="modal-body">
      			<div>
      				<br><span style="color: red; font-size: small;">허위 신고의 경우 신고자에게 제재가 가해질 수 있습니다.</span>
      			</div>
        		<form action="feedreport.action" method="post" id="myForm">
        			<div class="row">
						<div class="col-md-3">
							<br><span class="sinGoTxt align-middle">신고사유</span>
						</div>
						<div class="col-md-9">
							<br><select class="form-select" aria-label="Default select example" style="width: 350px" id="report_reason_num" name="report_reason_num">
								<option selected>===========선택===========</option>
								<option value="1">스팸, 홍보, 도배</option>
								<option value="2">욕설 및 선정적인 내용</option>
								<option value="3">도용, 명예훼손 및 비방, 초상권 및 저작권 침해</option>
								<option value="4">범죄</option>
							</select>
						</div>
					</div>
					<div class="mb-3">
						<br><span style="float: left">신고상세내용</span>
						<br><textarea class="form-control" rows="3" cols="10" id="reason" name="reason"></textarea>
					</div>
					<input type="hidden" id="hiddenId" name="id">
					<input type="hidden" id="hiddenFeedNum" name="feedNum">
					<input type="hidden" name="roomNum" value="${feedDto.roomNum }">
        		</form>
      		</div>
      		<div class="modal-footer">
        		<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
        		<button type="button" class="btn btn-danger" id="modalReport" name="modalReport">신고</button>
      		</div>
    	</div>
  	</div>
</div>

</body>
</html>