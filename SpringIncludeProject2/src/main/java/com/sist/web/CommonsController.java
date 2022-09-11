package com.sist.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.sist.dao.*;


// 공통 모듈 => 재 사용 위해 => 가장 먼저 작성
public class CommonsController {

	public static void commonsData(String table_name, String page, Model model, GoodsDAO dao)
	{
		
		
		// 페이징 ==> 모든 페이지에 공통으로 들어 감
		if(page == null)
			page = "1";
		
		int curpage = Integer.parseInt(page);
		
		Map map = new HashMap();
		
		int rowSize = 12; // 페이지 당 상품 개수
		int start = (rowSize*curpage)-(rowSize-1);
		int end = (rowSize*curpage);
		
		map.put("start", start);
		map.put("end", end);
		map.put("table_name", table_name); // goods_all, goods_best, goods_new...
		
		
		
		
		// 글자 수 조절
		List<GoodsVO> list = dao.goodListData(map);
		
		for(GoodsVO vo:list)
		{
			String name = vo.getGoods_name();
			if(name.length() > 25)
			{
				name = name.substring(0, 25) + "...";
				vo.setGoods_name(name);
			}
			vo.setGoods_name(name);
		}
		int totalpage = dao.goodsTotalPage(map);
		
		
		
		
		
		// home.jsp로 출력할 데이터를 전송
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("list", list);
		
	}
	
											
	public static void goodDetailData(String table_name, int no, Model model, GoodsDAO dao) 
	{
		Map map = new HashMap();
		map.put("table_name", table_name); // 데이터 담긴 테이블도
		map.put("no", no); // 상품 번호(no) 넘겨서 상품 상세보기 정보 가져온다
		
		GoodsVO vo = dao.goodsDetailData(map);
		model.addAttribute("vo", vo);
	}
}
