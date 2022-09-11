package com.sist.mapper;
import java.util.*;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
/*
 *    private int no,bno,group_id,group_step,group_tab,root,depth,type;
	  private String id,name,msg,dbday;
	  private Date regdate;
 */
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sist.dao.ReplyVO;

public interface ReplyMapper {
	
	//===== 댓글 목록
	@Select("SELECT no, bno, name, msg, TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS') as dbday, group_tab, type "
			+ "FROM spring_reply "
			+ "WHERE bno=#{bno} AND type=#{type} "
			+ "ORDER BY group_id DESC, group_step ASC") // 입력한 순서대로, 같은 그룹끼리 출력
	public List<ReplyVO> replyListData(ReplyVO vo);
	
	
	
	
	// 댓글 작성 시 자동 증가 번호 => no 
	@SelectKey(keyProperty = "no", resultType = int.class, before = true,
			statement = "SELECT NVL(MAX(no)+1, 1) as no FROM spring_reply")
	//===== 댓글 작성
	@Insert("INSERT INTO spring_reply(no, bno, id, name, msg, group_id, type) "
			+ "VALUES(#{no}, #{bno}, #{id}, #{name}, #{msg}, (SELECT NVL(MAX(group_id)+1, 1) as no FROM spring_reply), #{type})")
	public void replyInsert(ReplyVO vo);
	
	
	
	
	//===== 댓글 수정
	@Update("UPDATE spring_reply SET msg=#{msg} WHERE no=#{no}")
	public void replyUpdate(ReplyVO vo);
	
	
	
	
	

	//===== 댓글 삭제 => 트랜잭션
	
		// 1. depth, root 가져 옴 // depth => 답변 수 , root => 원 글의 고유 번호(no)
	@Select("SELECT depth, root FROM spring_reply WHERE no=#{no}")
	public ReplyVO replyInfoData(int no);
	
		// 2-1 depth > 0 => UPDATE , depth == 0 => DELETE
	@Update("UPDATE spring_reply SET msg = '관리자가 삭제한 댓글입니다.' WHERE no=#{no}")
	public void replyMsgUpdate(int no);
	
		// 2-2 삭제
	@Delete("DELETE FROM spring_reply WHERE no=#{no}")
	public void replyDelete(int no);
	
		// 3. depth(답변 수) 감소
	@Update("UPDATE spring_reply SET depth=depth-1 WHERE no=#{no}")
	public void replyDepthDecrement(int no);
	
	
	
	
	
	
	//====== 답변 추가 => 트랜잭션 
	@Select("SELECT group_id, group_step, group_tab FROM spring_reply WHERE no=#{no}")
	public ReplyVO replyParentInfoData(int no);
	
	
		// group_step 변경 
	@Update("UPDATE spring_reply SET group_step = group_step+1 "
			+ "WHERE group_id=#{group_id} AND group_step>#{group_step}")
	public void replyGroupStepIncrement(ReplyVO vo);
	
		
		// 답변 시 고유 번호 no 증가
	@SelectKey(keyProperty = "no", resultType = int.class, before = true, statement = "SELECT NVL(MAX(no)+1, 1) as no FROM spring_reply")
		// insert
	@Insert("INSERT INTO spring_reply(no, bno, id, name, msg, group_id, type, group_step, group_tab, root) "
			+ "VALUES(#{no}, #{bno}, #{id}, #{name}, #{msg}, #{group_id}, #{type}, #{group_step}, #{group_tab}, #{root})")
	public void replyReplyInsert(ReplyVO vo);
	
	
		// depth 변경
	@Update("UPDATE spring_reply SET depth = depth+1 WHERE no=#{no}")
	public void replyDepthIncrement(int no);

	
	
	
}













