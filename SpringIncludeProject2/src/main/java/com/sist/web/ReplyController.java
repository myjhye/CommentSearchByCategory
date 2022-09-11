package com.sist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.sist.dao.*;
import com.sist.mapper.ReplyMapper;

@Controller
public class ReplyController {

	@Autowired
	private ReplyDAO dao;
	
	
	//================= 기본
	
	
	
	
	// 댓글 작성
	@PostMapping("reply/reply_insert.do")
	public String reply_insert(ReplyVO vo, HttpSession session)
	{
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
		
		vo.setId(id);
		vo.setName(name);
		dao.replyInsert(vo);
		
		String uri = "";
		if(vo.getType() == 1)
		{
			uri = "../goods/all_detail.do?no=" + vo.getBno();
		}
		else if(vo.getType() == 2)
		{
			uri = "../goods/new_detail.do?no=" + vo.getBno();
		}
		else if(vo.getType() == 3)
		{
			uri = "../goods/special_detail.do?no=" + vo.getBno();
		}
		else if(vo.getType() == 4)
		{
			uri = "../goods/best_detail.do?no=" + vo.getBno();
		}
		
		return "redirect: " + uri;
	}
	
	
	
	//======= 댓글 목록 보기는 GoodsController.java의 상세보기에서 작성 ==> 댓글은 상세 보기에서 작성 되고 보여지므로
	
	
	
	// 댓글 수정 
	@PostMapping("reply/reply_update.do") //==> post로 결과 값 보낸다
	public String reply_update(ReplyVO vo)
	{
		// 데이터 베이스 연동
		dao.replyUpdate(vo);
		
		
		// 화면 이동
		String uri = "";
		
		if(vo.getType() == 1)
		{
			uri = "../goods/all_detail.do?no=" + vo.getBno(); // 특정 그룹에 있는 댓글 찾아서 수정
		}
		
		if(vo.getType() == 2)
		{
			uri = "../goods/new_detail.do?no=" + vo.getBno(); // 특정 그룹에 있는 댓글 찾아서 수정
		}
		
		if(vo.getType() == 3)
		{
			uri = "../goods/special_detail.do?no=" + vo.getBno(); // 특정 그룹에 있는 댓글 찾아서 수정
		}
		
		if(vo.getType() == 4)
		{
			uri = "../goods/best_detail.do?no=" + vo.getBno(); // 특정 그룹에 있는 댓글 찾아서 수정
		}
		
		return "redirect: " + uri;
		
	}
	
	
	
	
	// 댓글 삭제
	@GetMapping("reply/reply_delete.do")
	public String reply_delete(ReplyVO vo)
	{
		// 데이터 베이스 연동
		dao.replyDelete(vo.getNo());
		
		
		// 화면 이동
		String uri = "";
		
		if(vo.getType() == 1)
		{
			uri = "../goods/all_detail.do?no=" + vo.getBno();
		}
		else if(vo.getType() == 2)
		{
			uri = "../goods/new_detail.do?no=" + vo.getBno();
		}
		else if(vo.getType() == 3)
		{
			uri = "../goods/special_detail.do?no=" + vo.getBno();
		}
		else if(vo.getType() == 4)
		{
			uri = "../goods/best_detail.do?no=" + vo.getBno();
		}
		
		
		return "redirect: " + uri;
	}
	
	
	
	
	// 답글 추가
	@PostMapping("reply/reply_reply_insert.do")
	public String reply_reply_insert(int pno, ReplyVO vo, HttpSession session)
	{
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
		vo.setId(id);
		vo.setName(name);
		dao.replyReplyInsert(pno, vo);
		
		
		// 화면 이동
		String uri = "";
		
		if(vo.getType() == 1)
		{
			uri = "../goods/all_detail.do?no=" + vo.getBno();
		}
		if(vo.getType() == 2)
		{
			uri = "../goods/new_detail.do?no=" + vo.getBno();
		}
		if(vo.getType() == 3)
		{
			uri = "../goods/special_detail.do?no=" + vo.getBno();
		}
		if(vo.getType() == 4)
		{
			uri = "../goods/best_detail.do?no=" + vo.getBno();
		}
		
		return "redirect: " + uri;
		
	}
	
	
}






