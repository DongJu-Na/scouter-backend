package com.kite.scouter.domain.reply.service.impl;

import com.kite.scouter.domain.board.dto.BoardDto.CreateBoard;
import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardById;
import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardsByCategory;
import com.kite.scouter.domain.board.dto.BoardDto.UpdateBoard;
import com.kite.scouter.domain.board.model.Board;
import com.kite.scouter.domain.board.repository.BoardRepository;
import com.kite.scouter.domain.board.service.BoardService;
import com.kite.scouter.domain.category.model.Category;
import com.kite.scouter.domain.category.service.CategoryService;
import com.kite.scouter.domain.reply.dto.ReplyDto.CreateReply;
import com.kite.scouter.domain.reply.dto.ReplyDto.ResponseReplysByBoardId;
import com.kite.scouter.domain.reply.dto.ReplyDto.UpdateReply;
import com.kite.scouter.domain.reply.model.Reply;
import com.kite.scouter.domain.reply.repository.ReplyRepository;
import com.kite.scouter.domain.reply.service.ReplyService;
import com.kite.scouter.domain.user.model.User;
import com.kite.scouter.domain.user.service.UserService;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

  private final ReplyRepository replyRepo;

  private final UserService userService;

  private final BoardService boardService;


  @Override
  public Reply getReplyById(Long replyId) {
    return replyRepo.findByReplyIdAndEnableTrue(replyId)
        .orElseThrow(() -> BadRequestException.of(ResponseCode.RY0001,"reply.id.not.find"));
  }

  @Override
  public Page<ResponseReplysByBoardId> getReplysByBoardId(final Pageable pageable, final Long boardId) {
    return replyRepo.getReplysByBoardId(pageable, boardId);
  }

  @Override
  public Long createReply(final CreateReply createReply) {
    User user = userService.getUserBySelf();
    Board board = boardService.getBoardById(createReply.getBoardId());
    Reply reply = Reply.builder()
        .replyContent(createReply.getReplyContent())
        .user(user)
        .board(board)
        .build();
    return replyRepo.save(reply).getReplyId();
  }

  @Override
  public void updateReply(final Long replyId, final UpdateReply updateReply) {
    Reply reply = getReplyById(replyId);
    reply.changeContent(updateReply.getReplyContent());
    replyRepo.save(reply);
  }

  @Override
  public void deleteReply(Long replyId) {
    Reply reply = getReplyById(replyId);
    reply.changeEnable(false);
    replyRepo.save(reply);
  }

}
