package com.kite.scouter.domain.reply.controlle;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.kite.scouter.domain.reply.dto.ReplyDto;
import com.kite.scouter.domain.reply.service.ReplyService;
import com.kite.scouter.global.core.CommonResponse;
import com.kite.scouter.global.core.PageWrapper;
import com.kite.scouter.global.core.SuccessData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/replys")
@RequiredArgsConstructor
public class ReplyController {

  private final ReplyService replyService;

  @GetMapping
  @ResponseStatus(OK)
  public CommonResponse<PageWrapper> getReplysByCategory(
      @RequestParam(value = "boardId") final Long boardId,
      @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
      @RequestParam(value = "size", defaultValue = "10", required = false) final int size
  ) {
    return CommonResponse.from(PageWrapper.of(replyService.getReplysByBoardId(PageRequest.of(page, size),boardId)));
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public CommonResponse<Long> createReply(@RequestBody @Valid final ReplyDto.CreateReply createReply) {
    return CommonResponse.from(replyService.createReply(createReply));
  }

  @PutMapping("/{replyId}")
  @ResponseStatus(CREATED)
  public CommonResponse<SuccessData> updateReply(
      @PathVariable final Long replyId,
      @RequestBody @Valid final ReplyDto.UpdateReply updateReply
  ) {
    replyService.updateReply(replyId,updateReply);
    return CommonResponse.from(SuccessData.of());
  }

  @DeleteMapping("/{replyId}")
  @ResponseStatus(CREATED)
  public CommonResponse<SuccessData> deleteReply(@PathVariable final Long replyId) {
    replyService.deleteReply(replyId);
    return CommonResponse.from(SuccessData.of());
  }

}
