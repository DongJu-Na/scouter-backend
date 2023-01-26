package com.kite.scouter.domain.reply.service;

import com.kite.scouter.domain.reply.dto.ReplyDto.CreateReply;
import com.kite.scouter.domain.reply.dto.ReplyDto.ResponseReplysByBoardId;
import com.kite.scouter.domain.reply.dto.ReplyDto.UpdateReply;
import com.kite.scouter.domain.reply.model.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyService {

  Reply getReplyById(Long replyId);

  Page<ResponseReplysByBoardId> getReplysByBoardId (Pageable pageable,Long boardId);

  Long createReply(CreateReply createReply);

  void updateReply(Long replyId, UpdateReply updateReply);

  void deleteReply(Long reply);
}
