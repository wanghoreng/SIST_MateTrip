/*=====================================
	VoteCrt.java
	- mybatis 객체 활용(Controller)
=====================================*/

package com.final1.logic;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VoteCrt {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 투표 제목 리스트 출력
	@RequestMapping(value = "/voteslist_title.action", method = RequestMethod.GET) //방 번호 수신(3번방)
    public String voteslistTitle(Model model, HttpSession session)
    {
        String result = "";

        IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
        IRoomListDAO dao1 = sqlSession.getMapper(IRoomListDAO.class);
        
        String room_num = (String)session.getAttribute("room_num");
        String id =  (String)session.getAttribute("id");
        // 제목 리스트
        ArrayList<VotesDTO> titleList = dao.voteTitle(room_num);
        
        String boss = dao.voteBoss(room_num);
        String mem_join_num = dao.mjSelect(dao1.selectIdNum(id), room_num);

        System.out.println(titleList);

        String titleL = "";
        int i = 0;

        // html 구문에서 팝업창 띄우는 데이터 넘기는 반복문
        for (VotesDTO title : titleList) {
			i++;
			titleL += "<a href='#' id='voteTitleStyle'" 
					+ "onclick='win_open(\"voteInfo_content.action?vote_num="+title.getVote_num()+"&room_num="+title.getRoom_num()+"\",\"팝업테스트\")'>" 
					+ i +". "+ title.getTitle() + "</a>";
			if (title.getVote_ing().equals("종료")) {
				titleL += "<span class='badge bg-light-subtle border border-light-subtle text-light-emphasis rounded-pill' style='color: #A4A4A4;'>"+title.getVote_ing()+"</span>";
			}
			else {
				titleL += "<span class='badge bg-light-subtle border border-light-subtle text-light-emphasis rounded-pill' style='color: #FA5858;'>"+title.getVote_ing()+"</span>";
			}
			if(boss.equals(mem_join_num))
			{
				titleL += "<button class='btn btn-outline-warning btn-sm d-flex flex-row-reverse' id='deleteBtnStyle' onclick='deleteFinally(\""+title.getRoom_num()+"\",\""+title.getVote_num()+"\")'>삭제</button>";
			}
			titleL += "<br>";
        }

        model.addAttribute("titleL",titleL);

        result = "/votesTitle.jsp";

        return result;
    }

	// 투표삭제하기 버튼 클릭시 요청되는 메서드 
	@RequestMapping(value = "/deleteVoteFinally.action", method = RequestMethod.GET)
	public String deleteFinally(VotesDTO dto, Model model)
	{
		String result = "";
		
		IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
		
		System.out.println(dto.getRoom_num()+","+dto.getVote_num());
		int check = dao.voteDeleteFinally(dto.getRoom_num(), dto.getVote_num());
		
		model.addAttribute("check",check);
		
		System.out.println("삭제됐는가 ? " + check);
		
		result = "/deleteVoteFinish.jsp";
		
		return result;
		
	}
	
	
	// 해당 투표에 대한 정보 출력 
	@RequestMapping(value = "/voteInfo_content.action", method = RequestMethod.GET)
	public String voteInfo(String room_num,String vote_num, Model model, HttpServletRequest request)
	{
		String result = "";
		
		IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
		IRoomListDAO roomDao = sqlSession.getMapper(IRoomListDAO.class);
		
		// 투표 정보
		VotesDTO voteInfo = dao.voteInfo(vote_num);
		
		// 투표 내용 
		ArrayList<VotesDTO> voteContent =  dao.voteContent(vote_num);
		
		// mem_join_num 확인
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		String id_num = roomDao.selectIdNum(id);
		String mem_join_num = dao.mjSelect(id_num, room_num);
		
		// 닉네임 조회
		ArrayList<VotesDTO> nikname = dao.nikSelect(vote_num);
		
		// 방장여부확인
		String boss =  dao.voteBoss(room_num);
		
		// 투표한 사람 여부 확인 
		int memVoteWho = dao.memVoteWho(vote_num, id_num);
		
		model.addAttribute("voteInfo",voteInfo); 
		model.addAttribute("voteContent",voteContent);
		model.addAttribute("boss",boss);
		model.addAttribute("mem_join_num",mem_join_num);
		model.addAttribute("room_num",room_num);
		model.addAttribute("vote_num",vote_num);
		model.addAttribute("memVoteWho",memVoteWho);
		model.addAttribute("nikname",nikname);
		
		result = "Vote_PopUp.jsp";
		return result;
	}
	
	
	// 투표 하기 버튼 클릭 시 - 단일 투표 일 경우 
	@RequestMapping(value = "/voteInsert.action", method = RequestMethod.GET)
	public String voteInsert(String room_num, String vote_num, String vote_category_num, String mem_join_num)
	{
		String result = "";
		IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
		
		dao.voteInsert(vote_category_num, mem_join_num);
		
		result = "redirect:voteInfo_content.action?room_num="+room_num+"&vote_num="+vote_num; 
		
		return result;
	}
	
	// 투표 하기 버튼 클릭 시 - 복수 투표 일 경우
	@RequestMapping(value = "/voteInsertMany.action", method = RequestMethod.GET)
	public String voteInsertMany(String room_num, String vote_num, String mem_join_num, String vote_categoryBox)
	{
		String result = "";
		
		IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
		
		String vote_category_num = "";
		
		// html 측에서 array 로 받아, url 로 넘긴 array는 string 으로되어있기 때문에, split 으로 나눠준다. 
		String[] vote_categoryBox1 = vote_categoryBox.split(",");
		
		// 투표 항목들 반복문 돌려서 insert 하기 
		for (int i = 0; i < vote_categoryBox1.length; i++) {
			vote_category_num =  vote_categoryBox1[i];
			dao.voteInsert(vote_category_num, mem_join_num);
		}
		
		result = "redirect:voteInfo_content.action?room_num="+room_num+"&vote_num="+vote_num;
		
		return result; 
		
		
	}
	
	// 투표 다시하기 버튼 클릭 시 
	@RequestMapping(value = "/voteDelete.action", method = RequestMethod.GET)
	public String voteDelete(String room_num,String mem_join_num, String vote_num)
	{
		String result = "";
		
		IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
		
		dao.voteDelete(mem_join_num, vote_num);
		
		result = "redirect:voteInfo_content.action?room_num="+room_num+"&vote_num="+vote_num;
		
		return result;
	}
	
	
	// 투표 종료하기 버튼 클릭 시 
	@RequestMapping(value = "/voteFinish.action", method = RequestMethod.GET)
	public String voteFinish(String room_num, String vote_num, Model model)
	{
		String result = "";
		
		IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
		
		dao.voteFinish(vote_num);
		
		result = "redirect:voteInfo_content.action?room_num="+room_num+"&vote_num="+vote_num;
		
		return result; 
	}
	
	
	// 투표 만들기 버튼 클릭 시 -> 투표 작성하는 모달창 이동~!! 
	@RequestMapping(value = "/voteMake.action", method = RequestMethod.GET)
	public String voteMake(String room_num, Model model)
	{
		String result = "";
		
		IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
		
		VotesDTO voteDate =  dao.voteDate_possible(room_num);
		
		model.addAttribute("voteDate",voteDate);
		
		result = "/VoteMake.jsp";
		
		return result;
	}
	
	
	// 투표 작성모달창 내에서 투표 만들기 버튼 클릭 시 
	@RequestMapping(value = "/makeVote.action", method = RequestMethod.GET)
	public String makeVote(VotesDTO dto, String contentBox ,Model model)
	{
		String result = "";
		
		IVoteListDAO dao = sqlSession.getMapper(IVoteListDAO.class);
		
		int titleCheck = dao.voteInsert_Title(dto);
		
		String vote_num = dao.lastVoteNum(dto);
		
		// 투표 번호 set 해주기 
		dto.setVote_num(vote_num);
		
		
		String[] contentBox1 = contentBox.split(",");
		int contentCheck = 0;
		
		// 투표 항목들 반복문 돌려서 insert 하기 
		for (int i = 0; i < contentBox1.length; i++) {
			dto.setContent(contentBox1[i]);
			contentCheck = dao.voteInsert_Content(dto);
		}
		
		result = "/makeVoteCheck.jsp";
		
		return result;
		//result = "redirect:planner.action?room_num="+dto.getRoom_num()+"&page=-1";
		
	}
	
}
