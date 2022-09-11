package com.sist.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sist.mapper.GoodsMapper;
import java.util.*;

@Repository
public class GoodsDAO {

	@Autowired
	private GoodsMapper mapper;
	
	//============= 기본
	
	
	// 상품 목록
	public List<GoodsVO> goodListData(Map map)
	{
		return mapper.goodsListData(map);
	}
	
	
	
	// 상품 총 페이지
	public int goodsTotalPage(Map map)
	{
		return mapper.goodsTotalPage(map);
	}
	
	
	
	// 상품 상세 보기
	public GoodsVO goodsDetailData(Map map)
	{
		return mapper.goodsDetailData(map);
	}
	
	
	
	
	// 로그인
	public MemberVO memberLogin(String id, String pwd)
	{
		MemberVO vo = new MemberVO();
		int count = mapper.idCount(id); // 아이디 체크
		
		if(count == 0) // DB에 아이디가 없으면
		{
			vo.setMsg("NOID");
		}
		else
		{
			MemberVO dbVO = mapper.memberGetPassword(id); // 비밀번호 체크
			if(pwd.equals(dbVO.getPwd())) // 로그인 된 상태
			{
				vo.setId(id);
				vo.setName(dbVO.getName());
				vo.setMsg("OK");
			}
			else
			{
				vo.setMsg("NOPWD");
			}
		}
		return vo;
	}
	
	
	
	
	// 검색
	public List<GoodsVO> goodsFindData(Map map)
	{
		return mapper.goodsFindData(map);
	}
}

