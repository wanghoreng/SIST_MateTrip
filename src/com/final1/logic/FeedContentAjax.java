package com.final1.logic;

import java.util.ArrayList;

public class FeedContentAjax
{
	public static String addContent(ArrayList<FeedDTO> arrayList, String id, ArrayList<String> list)
	{
		StringBuilder sb = new StringBuilder();
		
		boolean flag = false;
		boolean flag2 = false;
		
		for (FeedDTO dto : arrayList)
		{
			flag2 = false;
			for (String str : list)
			{
				if (str.equals(dto.getIdNum()))
				{
					flag2 = true;
				}
			}
			
			sb.append("<div class='mooon'>");
			sb.append("		<div class='peed_profile' style='margin-top: 1vh; margin-bottom: 0px;'>");
			
			if (flag2)
			{
				sb.append("			<img alt='' src='images/feedCaution.png' style='width: 70px; height: 70px; border-radius: 50%;'>");
				sb.append("			<p style='color: white; text-align: center;'>신고접수</p>");
			}
			
			if (!flag2)
			{
				sb.append("			<img alt='' src='" + dto.getPhoto() + " ' style='width: 70px; height: 70px; border-radius: 50%;'>");
				sb.append("			<p style='color: white; text-align: center;'>" + dto.getNickName() + "</p>");
			}
			
			sb.append("		</div>");
			sb.append("		<div class='mooon2'>");
			sb.append("			<div class='peed_content_content'>");
			
			flag = false;
			for (String str : list)
			{
				if (str.equals(dto.getFeedNum()))
				{
					flag = true;
				}
			}
			
			if (flag)
			{
				sb.append("<p style=\"color: white; width: 800px; text-align: left; background-color: #87BDC7; border-radius: 5px;\">\r\n" + 
						"							본 피드 내용은 신고가 접수되어 블라인드 처리되었습니다.</p>");
			}
			
			if (!flag)
			{
				sb.append("				<p style='color: white; width: 800px; text-align: left;'>" + dto.getContent() +"</p>");
			}
			
			sb.append("			</div>");
			sb.append("			<div class='peed_time'>");
			sb.append("				<p style='color: white;'>" + dto.getWriteTime() + "</p>");
			sb.append("			</div>");
			sb.append("			<div class='contentDelete'>");
			
			if (dto.getId().equals(id) && dto.getCount().equals("0") && !flag) // listCount 가 없는 이유 : 참여중이 아니면 피드 작성이 불가하기 때문에 ajax가 넘어갈 일이 없음
			{
				sb.append("<button type='button' class='btn btn-success btnDel' id='btnDel' name='btnDel' value='" + dto.getFeedNum() + "'>삭제</button>");
			}
			else if (dto.getId().equals(id) && !flag)
			{
				sb.append("<button type='button' class='btn btn-success btnDel' id='btnDel' disabled=\"disabled\" name='btnDel' value='" + dto.getFeedNum() + "'>삭제</button>");
			}
			else if (dto.getId().equals(id) && flag)
			{
				sb.append("<button type=\"button\" disabled=\"disabled\" class=\"btn btn-success btnDel\" id=\"btnDel\" name=\"btnDel\">삭제</button>");
			}
			else if (!dto.getId().equals(id) && dto.getCount().contentEquals("0"))
			{
				sb.append("<button type='button' class='btn btn-danger btnReport' value='" + dto.getNickName() + "' id='" + dto.getId() + "' name='" + dto.getFeedNum() + "'>신고</button>");
			}
			else
			{
				sb.append("<button type='button' class='btn btn-danger btnReport' disabled='disabled'>신고</button>");
			}

			sb.append("			</div>");
			sb.append("		</div>");
			sb.append("</div>");
			
		}
		
		return sb.toString();
		
	}
}
