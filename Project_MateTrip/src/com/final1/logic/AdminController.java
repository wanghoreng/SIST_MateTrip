package com.final1.logic;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.final1.util.MyUtil;


@Controller
public class AdminController
{
	@Autowired
	private SqlSession sqlSession;
	
	// 회원 리스트 출력
	@RequestMapping(value = "memberlist.action")
	public String memberList(Model model, HttpServletRequest request,@Param("pageNum") String pageNum
						   , @Param("searchKey")String searchKey, @Param("searchValue")String searchValue) throws UnsupportedEncodingException
	{
		String result = "";
		
		IAdminDAO dao = sqlSession.getMapper(IAdminDAO.class);
		
		int dataCount = 0;
		// 이전 페이지(?)로부터 넘어온 페이지 번호 수신
		int currentPage = 1;
		if(pageNum != null)
			currentPage = Integer.parseInt(pageNum);
		
		
		if(searchValue != null)			
		{
			if(request.getMethod().equalsIgnoreCase("GET"))  
			{
				searchValue = URLDecoder.decode(searchValue, "UTF-8");		
			}
			dataCount = dao.searchMemberCount(searchKey, searchValue);			
		}
		else
		{
			dataCount = dao.count();
		}
		model.addAttribute("count", dataCount);
		
		MyUtil myUtil = new MyUtil();
		
		int numPerPage = 10;				
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage > totalPage)
			currentPage = totalPage;
		
		int start = (currentPage-1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		
		String param = "";
		
		if(searchValue == null)			
		{
			model.addAttribute("list", dao.memberList(start, end));
		}
		else
		{
			model.addAttribute("list", dao.searchMemberList(searchKey, searchValue, start, end));
			param += "?searchKey=" + searchKey;
			param += "&searchValue=" + searchValue;
		}
		
		String listUrl = "memberlist.action" + param;
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		model.addAttribute("pageIndexList", pageIndexList);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);
		
		
		result = "/WEB-INF/view/MemberList.jsp";
		
		return result;
	}
	
	// 전체 패널티 현황 출력
	@RequestMapping(value = "penaltylist.action")
	public String penaltyList(Model model, @Param("pageNum") String pageNum
			, @Param("searchKey")String searchKey, @Param("searchValue")String searchValue
			, HttpServletRequest request)
					throws UnsupportedEncodingException
	{
		
		IAdminDAO dao = sqlSession.getMapper(IAdminDAO.class);
		
		int dataCount = 0;
		// 이전 페이지(?)로부터 넘어온 페이지 번호 수신
		int currentPage = 1;
		if(pageNum != null)
			currentPage = Integer.parseInt(pageNum);
		
		
		if(searchValue != null)			
		{
			if(request.getMethod().equalsIgnoreCase("GET")) 
			{
				searchValue = URLDecoder.decode(searchValue, "UTF-8");		
			}
			
			dataCount = dao.searchPenaltyCount(searchKey, searchValue);		
		}
		else
		{
			dataCount = dao.penaltyCount();
		}
		model.addAttribute("count", dataCount);
		
		MyUtil myUtil = new MyUtil();
		
		int numPerPage = 10;				
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage > totalPage)
			currentPage = totalPage;
		
		int start = (currentPage-1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		
		// 페이징 처리
		String param = "";
		
		// 실제 리스트 가져오기
		if(searchValue == null)			
		{
			model.addAttribute("list", dao.penaltyList(start, end));
		}
		else
		{
			model.addAttribute("list", dao.searchPenaltyList(searchKey, searchValue, start, end));
			System.out.println( dao.searchPenaltyList(searchKey, searchValue, start, end).isEmpty());
			param += "?searchKey=" + searchKey;
			param += "&searchValue=" + searchValue;
			System.out.println(searchKey + searchValue);
			model.addAttribute("searchKey", searchKey);
			model.addAttribute("searchValue", searchValue);
		}
		
		
		String listUrl = "penaltylist.action" + param;
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		model.addAttribute("pageIndexList", pageIndexList);
		
		return "/WEB-INF/view/PenaltyList.jsp";
	}
	
	// 전체 신고 현황 출력
	@RequestMapping(value = "reportlist.action")
	public String reportList(Model model, @Param("pageNum") String pageNum
			, @Param("searchKey")String searchKey, @Param("searchValue")String searchValue
			, HttpServletRequest request)
					throws UnsupportedEncodingException
	{
		
		IAdminDAO dao = sqlSession.getMapper(IAdminDAO.class);
		
		int dataCount = 0;

		int currentPage = 1;
		if(pageNum != null)
			currentPage = Integer.parseInt(pageNum);
		
		
		if(searchValue != null)			
		{
			if(request.getMethod().equalsIgnoreCase("GET"))  
			{
				searchValue = URLDecoder.decode(searchValue, "UTF-8");	
			}
			
			dataCount = dao.searchReportCount(searchKey, searchValue);		
		}
		else
		{
			dataCount = dao.reportCount();
		}
		model.addAttribute("count", dataCount);
		
		MyUtil myUtil = new MyUtil();
		
		// 전체 데이터 갯수 구하기
		int numPerPage = 10;				
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		if(currentPage > totalPage)
			currentPage = totalPage;
		
		// 데이터베이스에서 가져올 시작과 끝 위치
		int start = (currentPage-1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		
		// 페이징 처리
		String param = "";
		
		if(searchValue == null)			
		{
			model.addAttribute("list", dao.reportList(start, end));
		}
		else
		{
			model.addAttribute("list", dao.searchReportList(searchKey, searchValue, start, end));
			param += "?searchKey=" + searchKey;
			param += "&searchValue=" + searchValue;
		}
		
		String listUrl = "reportlist.action" + param;
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		model.addAttribute("pageIndexList", pageIndexList);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);
		
		return "/WEB-INF/view/ReportList.jsp";
	}
	
	// 특정 인원의 신고 세부 현황
	@RequestMapping(value = "reportprocess.action")
	public String reportProcess(int num, Model model)
	{
		IAdminDAO dao = sqlSession.getMapper(IAdminDAO.class);
		
		ReportListDTO dto = dao.searchId(num);
		
		model.addAttribute("dto", dto);
		
		return "/WEB-INF/view/ReportProcess.jsp";
	}
	
	// 신고 처리
	@RequestMapping(value = "reportupdate.action")
	public String reportUpdate(ReportListDTO dto, HttpServletRequest request)
	{
		String result = "";
		
		IAdminDAO dao = sqlSession.getMapper(IAdminDAO.class);
		IRoomDAO dao2 = sqlSession.getMapper(IRoomDAO.class);
		
		HttpSession session = request.getSession();
		String adminId = (String)session.getAttribute("adminId");
		
		String receiptNum = dto.getReceiptNum();
		int selectPenalty =	dto.getSelectPenalty();
		 
		// 신고처리하기전에..... 해당 신고 항목에 따라 해당 계정의 계정정지 여부 확인 
		// 계정정지 되어있으면 계정정지 끝나는날짜 확인 -> 패널티 시작일 : 계정정지 끝나는날짜 +1
		// 신고 처리 insert -> 패널티 insert
		
		// if (receiptNum 이 프로필이면서 selectPenalty 가 6일때), 피신고자 계정정지확인
		// else(selectPenalty 값이 2 일때, 신고자 계정정지 확인 
		// 						   7 일때, 피신고자 계정정지 확인
		
		String blockStart = "";
		String id = "";
		String idNum = "";
		String roomNum = "";
		// receiptNum.equals("ROOM")
		// if 2			2
		// if 7			6
		
		if (receiptNum.equals("ROOM"))
		{
			if (selectPenalty == 2)		// 신고자 계정정지
			{
				id = dto.getReporterId();
				blockStart = dao.checkStopId(id);
				idNum = dao2.findIdNum(id);
				
				// 신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
			}
			else if (selectPenalty == 7)		// 피신고자 계정정지
			{
				id = dto.getReportedId(); 
				blockStart = dao.checkStopId(id);
				idNum = dao2.findIdNum(id);
				
				// 피신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 피신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 피신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 피신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
				
			}
			else if (selectPenalty == 3 || selectPenalty == 4 || selectPenalty == 5 || selectPenalty == 6)
			{
				
				id = dto.getReportedId();
				blockStart = dao.checkStopRoomMake(id);
				idNum = dao2.findIdNum(id);
				roomNum = dto.getRoomNum();
				
				dao.removeRoom(roomNum);
				
				for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(roomNum))
				{
					// 퇴장 테이블에 insert 
					idNum = dto2.getId_num();
					dao2.insertExitMember(idNum, roomNum, 4);
				}
				// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
				dao2.ongoingDel(roomNum);
				
				// bookmark 삭제
				dao2.bookmarkDel(roomNum);
				
			}
			
			dto.setBlockStart(blockStart);
			dto.setAdminId(adminId);
			
			dao.addRoom(dto);
			dao.addRoomPenalty(dto);
		}
		else if (receiptNum.equals("COLLECTION"))
		{
			
			if (selectPenalty == 2)		// 신고자 계정정지
			{
				id = dto.getReporterId();
				blockStart = dao.checkStopId(id);
				idNum = dao2.findIdNum(id);
				
				// 신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
			}
			else if (selectPenalty == 7)		// 피신고자 계정정지
			{
				id = dto.getReportedId(); 
				blockStart = dao.checkStopId(id);
				dao.removeCollectionMemo(dto.getReportNum());
				
				idNum = dao2.findIdNum(id);
				
				// 피신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 피신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 피신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 피신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
			}
			else if (selectPenalty == 3 || selectPenalty == 4 || selectPenalty == 5 || selectPenalty == 6)
			{
				id = dto.getReportedId();
				blockStart = dao.checkStopRoomMake(id);
				dao.removeCollectionMemo(dto.getReportNum());
			}
			
			
			dto.setBlockStart(blockStart);
			dto.setAdminId(adminId);
			
			dao.addCollectionMemo(dto);
			dao.addCollectionMemoPenalty(dto);
			
			
		}
		else if (receiptNum.equals("PLANMEMO"))
		{
			if (selectPenalty == 2)		// 신고자 계정정지
			{
				id = dto.getReporterId();
				blockStart = dao.checkStopId(id);
				idNum = dao2.findIdNum(id);
				
				// 신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
			}
			else if (selectPenalty == 7)	// 피신고자 계정정지
			{
				id = dto.getReportedId(); 
				blockStart = dao.checkStopId(id);
				dao.removePlannerMemo(dto.getReportNum());
				
				idNum = dao2.findIdNum(id);
				
				// 피신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 피신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 피신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 피신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
			}
			else if (selectPenalty == 3 || selectPenalty == 4 || selectPenalty == 5 || selectPenalty == 6)
			{
				id = dto.getReportedId();
				blockStart = dao.checkStopRoomMake(id);
				dao.removePlannerMemo(dto.getReportNum());
			}
			
			dto.setBlockStart(blockStart);
			dto.setAdminId(adminId);
			
			dao.addPlannerMemo(dto);
			dao.addPlannerMemoPenalty(dto);
		}
		else if (receiptNum.equals("PROFILE"))
		{
			if (selectPenalty == 2)		// 신고자 계정정지
			{
				id = dto.getReporterId();
				blockStart = dao.checkStopId(id);
				idNum = dao2.findIdNum(id);
				
				// 신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
			}
			else if (selectPenalty == 6)	// 피신고자 계정정지
			{
				id = dto.getReportedId(); 
				blockStart = dao.checkStopId(id);
				idNum = dao2.findIdNum(id);
				
				// 피신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 피신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 피신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 피신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
				
				// 부적절한 프로필로 변경하는 구문 추가 작성 필요
			}
			
			dto.setBlockStart(blockStart);
			dto.setAdminId(adminId);
			
			dao.addProfile(dto);
			dao.addProfilePenalty(dto);
		}
		else if (receiptNum.equals("FEED"))
		{
			if (selectPenalty == 2)		// 신고자 계정정지
			{
				id = dto.getReporterId();
				blockStart = dao.checkStopId(id);
				idNum = dao2.findIdNum(id);
				
				// 신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
			}
			else if (selectPenalty == 7)	// 피신고자 계정정지
			{
				id = dto.getReportedId(); 
				blockStart = dao.checkStopId(id);
				dao.removeFeed(dto.getReportNum());
				idNum = dao2.findIdNum(id);
				
				// 피신고자의 진행중 방 리스트 중 room_num 가져오기
				for(String room_num : dao.roomList(idNum))
				{
					// 피신고자가 방장이 아닌 경우
					if (dao.listCount(room_num, idNum) == 0)
					{
						dao2.insertExitMember(idNum, room_num, 4);
						
						// 해당 방에서 피신고자만 퇴장
						dao.ongoingDel(room_num, idNum);
					}
					// 피신고자가 방장인 경우
					else
					{
						dao.removeRoom(room_num);
						
						for(RoomMemberDTO dto2 : dao2.ongoingMemCheck(room_num))
						{
							// 퇴장 테이블에 insert 
							idNum = dto2.getId_num();
							dao2.insertExitMember(idNum, room_num, 4);
						}
						// 해당 방의 모든 인원 퇴장시키기 (방이 삭제되기 때문)
						dao2.ongoingDel(room_num);
						
						// bookmark 삭제
						dao2.bookmarkDel(room_num);
						
					}
				}
			}
			else if (selectPenalty == 3 || selectPenalty == 4 || selectPenalty == 5 || selectPenalty == 6)
			{
				id = dto.getReportedId();
				blockStart = dao.checkStopRoomMake(id);
				dao.removeFeed(dto.getReportNum());
			}
			
			dto.setBlockStart(blockStart);
			dto.setAdminId(adminId);
			
			dao.addFeed(dto);
			dao.addFeedPenalty(dto);
		}
		
		result = "redirect:reportlist.action";
		
		return result;
	}
}
