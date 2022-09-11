package com.sist.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Select;

import com.sist.dao.*;

public interface GoodsMapper {

	// 상품 목록
	@Select("SELECT no, goods_name, goods_price, goods_poster, num "
			+ "FROM(SELECT no, goods_name, goods_price, goods_poster, rownum as num "
			+ "FROM(SELECT no, goods_name, goods_price, goods_poster "
			+ "FROM ${table_name} ORDER BY no ASC )) "
			+ "WHERE num BETWEEN #{start} AND #{end}")
	public List<GoodsVO> goodsListData(Map map);
	
	
	// 상품 목록 총 페이지
	@Select("SELECT CEIL(COUNT(*)/5.0) FROM ${table_name}")
	public int goodsTotalPage(Map map);
	
	
	
	
	// 상품 상세 보기
	@Select("SELECT * FROM ${table_name} WHERE no=#{no}")
	public GoodsVO goodsDetailData(Map map);
	
	
	
	//=============== 로그인 처리
	// 1. ID 체크 ==> DB에 ID 있는지
	@Select("SELECT COUNT(*) FROM project_member WHERE id=#{id}")
	public int idCount(String id);
	
	// 2. 비밀번호 비교
	@Select("SELECT pwd, name FROM project_member WHERE id=#{id}")
	public MemberVO memberGetPassword(String id);
	
	
	
	
	//============== 검색
	@Select("SELECT * FROM ${table_name} WHERE REGEXP_LIKE(goods_name, #{ss})")
	public List<GoodsVO> goodsFindData(Map map);
	
	
}
