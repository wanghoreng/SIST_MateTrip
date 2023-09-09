package com.final1.logic;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class PhotoChange
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/photochange.action", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, Model model)
	{
		String result = "";
		
		try
		{
			
			String root = request.getSession().getServletContext().getRealPath("/") + "profile";
			
			File dir = new File(root);
			
			if (!dir.exists())
			{
				dir.mkdirs();
			}
			
			String encType = "UTF-8";
			int maxFileSize = 5*1024*1024;
			
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, root, maxFileSize, encType,
					new DefaultFileRenamePolicy());
			
			String fileName = multi.getFilesystemName("filePath");
			//fileName = root + File.separator + fileName;
			//System.out.println(fileName);
			
			if (fileName == null)
				model.addAttribute("fileName", "images/non_profile.jpg");
			else
				model.addAttribute("fileName", "profile/"+fileName);
				
			//System.out.println(multi.getOriginalFileName("upload"));
			//System.out.println(multi.getContentType("upload"));
			
			//File file = multi.getFile("upload");
			
			//if (file != null)
			//{
				//System.out.println(file.length());
			//}
			
			IMyPageDAO dao = sqlSession.getMapper(IMyPageDAO.class);
			
			String id = multi.getParameter("id");
			String id_num = dao.findMyId(id);
			String photo = "profile/" + fileName;
			
			// 미리보기 파일을 지우는 과정
			File preview = new File(root + File.separator + "preview");
			
			String[] list = preview.list();
			
			for (String file : list)
			{
				File trash = new File(root + File.separator + "preview" + File.separator + file);
				trash.delete();
			}
			
			// 기존의 프로필사진을 삭제하는 과정
			String old = dao.findMyPhoto(id_num);
			
			if (!old.equals("images/non_profile.jpg"))
			{
				File oldPhoto = new File(root + File.separator + old.split("/")[1]);
				oldPhoto.delete();				
			}
			
			// 경로를 새 프로필사진으로 바꿔서 저장하는 과정
			if (fileName == null)
				dao.changePhoto(id_num, "images/non_profile.jpg");
			else
				dao.changePhoto(id_num, photo);
		
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		result = "/photoChange.jsp";
		
		return result; 
	}
	
	// 미리보기용 저장
	@RequestMapping(value = "/photopreview.action", method = RequestMethod.POST)
	public String preview(HttpServletRequest request, Model model)
	{
		String result = "";
		
		try
		{
			
			String root = request.getSession().getServletContext().getRealPath("/") + "profile" + File.separator + "preview";
			
			File dir = new File(root);
			
			if (!dir.exists())
			{
				dir.mkdirs();
			}
			
			String encType = "UTF-8";
			int maxFileSize = 5*1024*1024;
			
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, root, maxFileSize, encType,
					new DefaultFileRenamePolicy());
			
			String fileName = multi.getFilesystemName("filePath");
			
			if (fileName == null)
				fileName = "images/non_profile.jpg";
			else
				fileName = "profile/preview/" + fileName;
			
			model.addAttribute("fileName", fileName);
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		result = "/photoChange.jsp";
		
		return result; 
	}
	
	// 현재 프로필 미리보기
	@RequestMapping(value = "/presentphoto.action", method = RequestMethod.GET)
	public String presentPhoto(String id, Model model)
	{
		String result = "";
	
		IMyPageDAO dao = sqlSession.getMapper(IMyPageDAO.class);
		
		model.addAttribute("fileName", dao.findMyPhoto(dao.findMyId(id)));

		result = "/photoChange.jsp";
		
		return result; 
	}
	
	// 현재 프로필 또는 디폴트 사진으로 저장하기
	@RequestMapping(value = "/defaultphoto.action", method = RequestMethod.GET)
	public String defaultPhoto(HttpServletRequest request, String id, String save, Model model)
	{
		String result = "";
		
		try
		{
			IMyPageDAO dao = sqlSession.getMapper(IMyPageDAO.class);
			
			String id_num = dao.findMyId(id);
			
			String photo = "";
			
			if (save.equals("now"))
			{
				photo = dao.findMyPhoto(id_num);
				dao.changePhoto(id_num, photo);
				model.addAttribute("fileName", photo);				
			}
			else
			{
				String root = request.getSession().getServletContext().getRealPath("/") + "profile";
				
				String old = dao.findMyPhoto(id_num);
				
				if (!old.equals("images/non_profile.jpg"))
				{
					File oldPhoto = new File(root + File.separator + old.split("/")[1]);
					oldPhoto.delete();				
				}
				
				photo = save;
				dao.changePhoto(id_num, photo);
				model.addAttribute("fileName", "images/non_profile.jpg");								
			}
			
			

		
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		result = "/photoChange.jsp";
		
		return result; 
	}
	
}
