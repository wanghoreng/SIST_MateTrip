package com.final1.logic;

public class VotesDTO
{
	//투표 번호, 방번호 투표 제목, 투표시작일, 투표종료일, 투표 카테고리, 복수단수, 익명실명
	private String id_num, nikname, vote_num, room_num;
	private int vote_count_check;
	
	// 투표 가능한 첫 날짜 ~ 마지막 날짜 
	private String v_start_date, v_end_date;
	
	// 투표진행중인지 확인하는 변수 
	private String vote_ing;
	
	
	// 추가 7
	public String getVote_ing() {
		return vote_ing;
	}

	public void setVote_ing(String vote_ing) {
		this.vote_ing = vote_ing;
	}

	
	// 추가 6
	public String getV_start_date() {
		return v_start_date;
	}

	public void setV_start_date(String v_start_date) {
		this.v_start_date = v_start_date;
	}

	public String getV_end_date() {
		return v_end_date;
	}

	public void setV_end_date(String v_end_date) {
		this.v_end_date = v_end_date;
	}

	public int getVote_count_check() {
		return vote_count_check;
	}

	public void setVote_count_check(int vote_count_check) {
		this.vote_count_check = vote_count_check;
	}

	// 추가 5
	public String getNikname() {
		return nikname;
	}

	public void setNikname(String nikname) {
		this.nikname = nikname;
	}

	// 추가 4 
	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	private String title, start_date, end_date,day, vote_select_num, vote_name_num;
	
	// 추가3
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	//투표 항목 번호, 투표한 사람, 투표한 날짜, 투표 항목
	private String vote_category_num;
    private String mem_join_num;
    private String vote_date;
    private String content;

    // 추가 2 
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCate_people(int cate_people) {
		this.cate_people = cate_people;
	}

	// 투표한 사람의 수 
	private int cate_people;
	
	public int getCate_people() {
		return cate_people;
	}

	// 추가 1
	public String getVote_category_num() {
		return vote_category_num;
	}

	public void setVote_category_num(String vote_category_num) {
		this.vote_category_num = vote_category_num;
	}

	public String getMem_join_num() {
		return mem_join_num;
	}

	public void setMem_join_num(String mem_join_num) {
		this.mem_join_num = mem_join_num;
	}

	public String getVote_date() {
		return vote_date;
	}

	public void setVote_date(String vote_date) {
		this.vote_date = vote_date;
	}


	// getter / setter 구성  -- 기존에 있던 변수 
	public String getVote_num() {
		return vote_num;
	}
	
	public void setVote_num(String vote_num) {
		this.vote_num = vote_num;
	}
	
	public String getRoom_num() {
		return room_num;
	}
	
	public void setRoom_num(String room_num) {
		this.room_num = room_num;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStart_date() {
		return start_date;
	}
	
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	public String getEnd_date() {
		return end_date;
	}
	
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public String getVote_select_num() {
		return vote_select_num;
	}
	
	public void setVote_select_num(String vote_select_num) {
		this.vote_select_num = vote_select_num;
	}
	
	public String getVote_name_num() {
		return vote_name_num;
	}
	
	public void setVote_name_num(String vote_name_num) {
		this.vote_name_num = vote_name_num;
	}
	
	
	/*
	private List<VoteCateDTO> voteItems;

	public String getVote_num()
	{
		return vote_num;
	}

	public void setVote_num(String vote_num)
	{
		this.vote_num = vote_num;
	}

	public List<VoteCateDTO> getVoteItems()
	{
		return voteItems;
	}

	public void setVoteItems(List<VoteCateDTO> voteItems)
	{
		this.voteItems = voteItems;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getStart_date()
	{
		return start_date;
	}

	public void setStart_date(String start_date)
	{
		this.start_date = start_date;
	}

	public String getEnd_date()
	{
		return end_date;
	}

	public void setEnd_date(String end_date)
	{
		this.end_date = end_date;
	}

	public String getVote_select_num()
	{
		return vote_select_num;
	}

	public void setVote_select_num(String vote_select_num)
	{
		this.vote_select_num = vote_select_num;
	}

	public String getVote_name_num()
	{
		return vote_name_num;
	}

	public void setVote_name_num(String vote_name_num)
	{
		this.vote_name_num = vote_name_num;
	}
	*/ 
	
	
}
