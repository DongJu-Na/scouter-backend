ALTER TABLE reply ADD COLUMN IF NOT EXISTS REPLY_BOARD BIGINT(20) default true comment '댓글 게시판 ID';
