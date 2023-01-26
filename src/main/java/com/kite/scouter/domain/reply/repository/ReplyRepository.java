package com.kite.scouter.domain.reply.repository;

import com.kite.scouter.domain.reply.model.Reply;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long>, ReplyCustomRepo {

  //Optional<Reply> findBy(Long replyId);
  Optional<Reply> findByReplyIdAndEnableTrue(Long replyId);

  Optional<Reply> findByReplyContent(String replyContent);

}
