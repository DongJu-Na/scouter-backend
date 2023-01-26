package com.kite.scouter.domain.reply.repository;

import com.kite.scouter.domain.reply.dto.ReplyDto.ResponseReplysByBoardId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyCustomRepo {

  Page<ResponseReplysByBoardId> getReplysByBoardId(Pageable pageable, Long boardId);

}
