package com.sist.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.dao.GoodsDAO;
import com.sist.dao.GoodsVO;
import com.sist.dao.ReplyDAO;
import com.sist.dao.ReplyVO;

@Controller
public class GoodsController {

	@Autowired
	private GoodsDAO dao;
	
	@Autowired
	private ReplyDAO rdao; // 답글
	
	//=========== 기본
	
	
	//=========== 상품 목록
	@GetMapping("goods/best.do")
	public String goods_best(String page, Model model) // 상품 목록에 page 넘긴다
	{
		CommonsController.commonsData("goods_best", page, model, dao);
		model.addAttribute("main_jsp", "../goods/best.jsp");
		
		return "main/main";
	}
	
	@GetMapping("goods/special.do")
	public String goods_special(String page, Model model)
	{
		CommonsController.commonsData("goods_special", page, model, dao);
		model.addAttribute("main_jsp", "../goods/special.jsp");
		
		return "main/main";
	}
	
	@GetMapping("goods/new.do")
	public String goods_new(String page, Model model)
	{
		CommonsController.commonsData("goods_new", page, model, dao);
		model.addAttribute("main_jsp", "../goods/new.jsp");
		
		return "main/main";
	}
	
	
	
	
	//============== 상세 보기
	
	@GetMapping("goods/all_detail.do")
	public String all_detail(int no, Model model)
	{
		CommonsController.goodDetailData("goods_all", no, model, dao);
		model.addAttribute("main_jsp", "../goods/all_detail.jsp"); // all_detail.jsp로 main_jsp 화면 변경
		
		
		//========== 댓글 목록 보기
		ReplyVO vo = new ReplyVO();
		vo.setBno(no); // 댓글 번호
		vo.setType(1); // 1 = all, 2 = new, 3 = special, 4 = best
		
		List<ReplyVO> rList = rdao.replyListData(vo); // 댓글 목록
		System.out.println("size: " + rList.size());
		
		model.addAttribute("rList", rList);
		
		
		
		return "main/main";
	}
	
	@GetMapping("goods/best_detail.do")
	public String best_detail(int no, Model model)
	{
		CommonsController.goodDetailData("goods_best", no, model, dao);
		model.addAttribute("main_jsp", "../goods/best_detail.jsp"); // all_detail.jsp로 main_jsp 화면 변경
		
		
		//========== 댓글 목록 보기
		ReplyVO vo = new ReplyVO();
		vo.setBno(no); // 댓글 번호
		vo.setType(4); // 1 = all, 2 = new, 3 = special, 4 = best
		
		List<ReplyVO> rList = rdao.replyListData(vo);
		model.addAttribute("rList", rList);
		model.addAttribute("rvo", vo);
		
		
		
		
		return "main/main";
	}
	
	@GetMapping("goods/special_detail.do")
	public String special_detail(int no, Model model)
	{
		CommonsController.goodDetailData("goods_special", no, model, dao);
		model.addAttribute("main_jsp", "../goods/special_detail.jsp"); // all_detail.jsp로 main_jsp 화면 변경
		
		
		//========== 댓글 목록 보기
		ReplyVO vo = new ReplyVO();
		vo.setBno(no); // 댓글 번호
		vo.setType(3); // 1 = all, 2 = new, 3 = special, 4 = best
		
		List<ReplyVO> rList = rdao.replyListData(vo);
		model.addAttribute("rList", rList);
		
		
		
		
		return "main/main";
	}
	
	@GetMapping("goods/new_detail.do")
	public String new_detail(int no, Model model)
	{
		CommonsController.goodDetailData("goods_new", no, model, dao);
		model.addAttribute("main_jsp", "../goods/new_detail.jsp"); // all_detail.jsp로 main_jsp 화면 변경
		
		
		//========== 댓글 목록 보기
		ReplyVO vo = new ReplyVO();
		vo.setBno(no); // 상품 번호 갖다 놓음
		vo.setType(2); // 1 = all, 2 = new, 3 = special, 4 = best
		
		List<ReplyVO> rList = rdao.replyListData(vo);
		model.addAttribute("rList", rList);
		
		
		
		
		return "main/main";
	}
	
	
	
	//============ 검색
	
	@RequestMapping("goods/find.do")
	public String goods_find(String fs, String ss, Model model)
	{
		System.out.println("fs=" + fs + ", ss=" + ss);
		
		if(fs!=null && ss!=null)
		{
			Map map = new HashMap();
			map.put("table_name", fs); // 카테고리
			map.put("ss", ss); // 검색어
			
			List<GoodsVO> list = dao.goodsFindData(map);
			System.out.println(list.size()); // 결과 개수
			
			model.addAttribute("list", list);
			
		}
		
		model.addAttribute("main_jsp", "../goods/find.jsp");
		
		return "main/main";
	}
	
	
}
