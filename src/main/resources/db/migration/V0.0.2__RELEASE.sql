ALTER TABLE board ADD COLUMN IF NOT EXISTS ENABLE BIT(1) default true comment '사용여부';
ALTER TABLE reply ADD COLUMN IF NOT EXISTS ENABLE BIT(1) default true comment '사용여부';

