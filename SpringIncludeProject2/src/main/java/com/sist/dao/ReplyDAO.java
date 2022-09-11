package com.sist.dao;
import java.util.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sist.mapper.*;

@Repository
public class ReplyDAO {
  
	@Autowired
	private ReplyMapper mapper;
  
	//============== 기본
	
	
	// 댓글 목록
	public List<ReplyVO> replyListData(ReplyVO vo)
	{
		return mapper.replyListData(vo);
	}
	
	
	
	// 댓글 작성
	public void replyInsert(ReplyVO vo)
	{
		mapper.replyInsert(vo);
	}
	
	
	
	
	// 댓글 수정
	public void replyUpdate(ReplyVO vo)
	{
		mapper.replyUpdate(vo);
	}
	
	
	
	// 댓글 삭제
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void replyDelete(int no)
	{
		// 1. depth, root 정보 가져오기
		ReplyVO vo = mapper.replyInfoData(no);
		
		if(vo.getDepth() == 0) // 답변이 없는 댓글이면
		{
			mapper.replyDelete(no); // 댓글을 삭제하고
		}
		else
		{
			mapper.replyMsgUpdate(no); // 답변이 있는 댓글이면 '관리자가 삭제한 댓글입니다' 문구 출력
		}
		mapper.replyDepthDecrement(vo.getRoot()); // 답변 수 감소
	}
	
	
	
	
   // 답변 추가 => 트랜잭션   
	   // pno => group_id, group_step, group_tab 
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
   public void replyReplyInsert(int pno, ReplyVO vo)
   {
	   ReplyVO pvo = mapper.replyParentInfoData(pno);
	   mapper.replyGroupStepIncrement(pvo);
	   
	   vo.setGroup_id(pvo.getGroup_id());
	   vo.setGroup_step(pvo.getGroup_step()+1);
	   vo.setGroup_tab(pvo.getGroup_tab()+1);
	   vo.setRoot(pvo.getRoot());
	   
	   mapper.replyReplyInsert(vo);
	   mapper.replyDepthIncrement(pno);
   }
}







