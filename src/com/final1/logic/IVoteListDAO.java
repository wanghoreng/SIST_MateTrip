/*====================
   IVoteListDAO.java
   - 인터페이스
====================*/

package com.final1.logic;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

public interface IVoteListDAO
{	
	// 닉네임 조회
	public ArrayList<VotesDTO> nikSelect(@Param("vote_num")String vote_num);
	
	// ID_NUM -> MEM_JOIN_NUM 조회 
	public String mjSelect(@Param("id_num")String id_num, @Param("room_num")String room_num);
	
	// 해당 방의 방장 조회 
	public String voteBoss(String room_num);
	
	// 해당 방의 투표 제목 리스트 조회
	public ArrayList<VotesDTO> voteTitle(String room_num);
	
	// 해당 방의 번호와 투표 제목 번호를 통해 투표 내용들을 조회
	public VotesDTO votelist(String room_num, String vote_num);
	
	// 해당 투표에 대한 정보(복수선택, 익명투표, 투표 기한 남은 일수, 투표 제목)
	public VotesDTO voteInfo(String vote_num);
	
	// 해당 투표에 대한 항목들 (투표 항목, 투표한 사람, 투표한 날짜, 항목에 투표한 사람 수)
	public ArrayList<VotesDTO> voteContent(String vote_num);
	
	// 해당 투표에 투표한 사람 조회
	public int memVoteWho(@Param("vote_num")String vote_num, @Param("id_num")String id_num);
	
	// 해당 투표의 투표항목에 투표하기 
	public int voteInsert(@Param("vote_category_num")String vote_category_num, @Param("mem_join_num")String mem_join_num);
	
	// 해당 투표 내역 삭제하기
	public int voteDelete(@Param("mem_join_num")String mem_join_num, @Param("vote_num")String vote_num);
	
	// 해당 투표 종료일 업데이트하기 
	public int voteFinish(String vote_num);
	
	// 투표 가능한 날짜 조회 
	public VotesDTO voteDate_possible(String room_num);
	
	// 투표 제목 데이터 삽입 
	public int voteInsert_Title(VotesDTO dto);
	
	// 투표 항목 데이터 삽입 
	public int voteInsert_Content(VotesDTO dto);
	
	// 투표 마지막 번호 조회 
	public String lastVoteNum(VotesDTO dto);
	
	// 투표 자체를 삭제하는 기능
	public int voteDeleteFinally(@Param("room_num")String room_num,@Param("vote_num")String vote_num);
}
