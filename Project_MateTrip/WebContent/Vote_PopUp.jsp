<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vote_PopUp.jsp</title>

<!-- jquery 소스 넣기 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- bootstrap 소스 넣기 (bootstrap v5.0 버전)  -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


<script type="text/javascript">
	
  	// 단일투표 일 때 버튼 클릭 시 
	function voteInsert(mem_join_num, room_num, vote_num) {
		
		var vote_category_num = "";
		// 단일 투표 일 때 체크된 투표항목번호 가져오기
		$('input:checkbox[name=oneCheck]').each(function() {
				if($(this).is(":checked") == true){
				 	vote_category_num = $(this).val();
				}
		 });
		
		$(location).attr('href',"voteInsert.action?room_num="+room_num +"&vote_num="+vote_num+"&vote_category_num="+vote_category_num+"&mem_join_num="+mem_join_num);
		
	}
	
	
	// 복수 선택일 때 투표하기 버튼 클릭시 
	function voteInsertMany(mem_join_num, room_num, vote_num) {
		
		// 다수의 투표항목번호를 담을 배열 생성 
		var vote_categoryBox = [];
		var vote_category_num = "";
		
		// 복수 투표일 때 체크된 투표항목들 가져오기
		$('input:checkbox[name=manyCheck]').each(function() {
			if ($(this).is(":checked") == true) {
				vote_category_num = $(this).val();
				vote_categoryBox.push(vote_category_num);
			}
			console.log(vote_categoryBox);
		});
		
		$(location).attr('href',"voteInsertMany.action?room_num="+room_num +"&vote_num="+vote_num+"&vote_categoryBox="+vote_categoryBox+"&mem_join_num="+mem_join_num);
	}
	
	// 투표 다시하기 클릭했을 
	function voteDelete(mem_join_num, room_num, vote_num) {
		
		$(location).attr('href',"voteDelete.action?mem_join_num="+mem_join_num+"&room_num="+room_num+"&vote_num="+vote_num);
		
	}
	
	// 투표 종료하기 버튼 클릭 시
	function voteFinish(room_num, vote_num) {
		
		alert("투표가 종료되었습니다.");
		
		$(location).attr('href',"voteFinish.action?room_num="+room_num+"&vote_num="+vote_num);
		
	}
	
	$(function() {
		
		// 단일 선택일 경우, 하나만 체크되게 설정 
		$('input[type="checkbox"][name="oneCheck"]').click(function() {
			if ($(this).prop('checked')) {
				$('input[type="checkbox"][name="oneCheck"]').prop('checked',false);
				$(this).prop('checked',true);
			}
		});
		
		// 투표 남은 날짜 값 가져오기 
		var voteDay = $(".voteDay").attr("id");
		var voteDayArray = voteDay.split('/');
		
		if (Number(voteDayArray[0]) == 1 && voteDayArray[1] == 0) {
			alert("투표날짜가 1일 남았습니다.");
		}
		else if (Number(voteDayArray[0]) == 0 && voteDayArray[1] == 0) {
			alert("투표날짜가 오늘 23시 59분까지 입니다.");
		}
		else if(Number(voteDayArray[0]) < 0) {
			voteFinish(room_num, vote_num);
		}
	});

</script>

<style type="text/css">
	
	.bg-primary-subtle {
    background-color: #F7D358;
	}
	
	.text-primary-emphasis {
    color: white;
	}
	
	.border-primary-subtle {
	border-color: white;
	}
	
	.bg-primary-subtle1 {
    background-color: #FAAC58;
	}
	
	#voteBtn {
		background-color: #819FF7;
		color: white;
		border-color: white;
	}
	
	#voteBtn:hover {
		background-color: #2E64FE;
	}
	
	h1 {
		font-weight: 600px;
		padding-top: 5px;
		padding-bottom : 0px;
		margin-left: 8px;
		display: inline;
	}

</style>

</head>
<body>
	
	<div class="d-grid d-flex justify-content-center">
		<h1>${voteInfo.title }</h1>
	</div>
	
	<div class="d-grid gap-2 d-flex ps-1 justify-content-center">
		<span class="badge bg-primary-subtle border border-primary-subtle text-primary-emphasis rounded-pill">
		 ${voteInfo.vote_select_num }
		</span>
		<span class="badge bg-primary-subtle border border-primary-subtle text-primary-emphasis rounded-pill">
		 ${voteInfo.vote_name_num }
		</span>
		<c:if test="${voteInfo.day+0 >= 0}">
			<span class="badge bg-primary-subtle border border-primary-subtle text-primary-emphasis rounded-pill voteDay" id="${voteInfo.day }/${memVoteWho}">
			 ${voteInfo.day } 일 남음 
			</span>
		</c:if>
	</div>
	
	<hr style="margin-bottom: 1px; margin-top: 10px;">
	
	
	<!-- 투표 항목  -->
	<div class="d-flex flex-column flex-md-row p-4 gap-4 py-md-5 align-items-center justify-content-center">
		<div class="list-group">
		<c:forEach var="content" items="${voteContent }">
		    <label class="list-group-item d-flex gap-2">
		    <!-- 투표 안한 사람일 경우에만 체크박스 보이게 설정 -->
		    <c:if test="${memVoteWho == 0 && voteInfo.day+0 >= 0}">
		      <input class="form-check-input flex-shrink-0" type="checkbox" ${voteInfo.vote_select_num == '단일 선택'?'name="oneCheck"':'name="manyCheck"'} value="${content.vote_category_num }">
		    </c:if>
		      <span>
		        ${content.content }
		      </span>
		      <span class="badge bg-primary-subtle1 border border-primary-subtle text-primary-emphasis rounded-pill">
		 		${content.cate_people } 표 
			  </span>
			  <!-- 실명일 경우 -->
			 <c:if test="${voteInfo.vote_name_num != '익명 투표'}">
			 	<c:forEach  var="nikname" items="${nikname }">
			 		<span style="font-size: 12px; color: #FF8000;">
			 		${content.vote_category_num == nikname.vote_category_num? nikname.nikname : ''}
			 		</span>
			 	</c:forEach>
			 </c:if>
		    </label>
	    </c:forEach>
	  </div>
	</div>	
	
	
	<!-- 여행 참여자 입장  -->
	<c:if test="${mem_join_num != boss}">
		<div class="d-grid gap-2 d-flex justify-content-center">
		<c:if test="${memVoteWho == 0 && voteInfo.day+0 >= 0}">
		 	<!-- 단일 투표  -->
			 <c:if test="${voteInfo.vote_select_num == '단일 선택'}">
			  <button class="btn btn-primary" id="voteBtn" type="button" onclick="voteInsert('${mem_join_num}','${room_num}', '${vote_num }')">투표하기</button>
			 </c:if>
			 <!-- 복수 투표 -->
			 <c:if test="${voteInfo.vote_select_num == '복수 선택'}">
			  <button class="btn btn-primary" id="voteBtn" type="button" onclick="voteInsertMany('${mem_join_num}','${room_num}', '${vote_num }')">투표하기</button>
			 </c:if>
		</c:if>
		<c:if test="${memVoteWho != 0 && voteInfo.day+0 >= 0}">
		  <button class="btn btn-primary" id="voteBtn" type="button" onclick="voteDelete('${mem_join_num}','${room_num}','${vote_num }')">투표 다시하기</button>
		</c:if>
		  <button class="btn btn-primary" id="voteBtn" type="button" onclick="javascript:window.close();">닫기</button>
		</div>
	</c:if>

	<!-- 방장 입장  -->
	<c:if test="${mem_join_num == boss}">
		<div class="d-grid gap-2 d-flex justify-content-center">
		<c:if test="${memVoteWho == 0 && voteInfo.day+0 >= 0}">
			 <!-- 단일 투표  -->
			 <c:if test="${voteInfo.vote_select_num == '단일 선택'}">
			  <button class="btn btn-primary" id="voteBtn" type="button" onclick="voteInsert('${mem_join_num}','${room_num}', '${vote_num }')">투표하기</button>
			 </c:if>
			 <!-- 복수 투표 -->
			 <c:if test="${voteInfo.vote_select_num == '복수 선택'}">
			  <button class="btn btn-primary" id="voteBtn" type="button" onclick="voteInsertMany('${mem_join_num}','${room_num}', '${vote_num }')">투표하기</button>
			 </c:if>
		</c:if>
		<c:if test="${memVoteWho != 0 && voteInfo.day+0 >= 0}">
		  <button class="btn btn-primary" id="voteBtn" type="button" onclick="voteDelete('${mem_join_num}','${room_num}','${vote_num }')">투표 다시하기</button>
		</c:if>
		  <button class="btn btn-primary" id="voteBtn" type="button" onclick="javascript:window.close();">닫기</button>
		  <c:if test="${voteInfo.day+0 >= 0 }">
			  <button class="btn btn-primary" id="voteBtn" type="button" onclick="voteFinish('${room_num}','${vote_num }')">투표종료</button>
		  </c:if>
		</div>
	</c:if>
</body>
</html>