ALTER TABLE BOARD ADD COLUMN IF NOT EXISTS ENABLE BIT(1) default true comment '사용여부';
ALTER TABLE REPLY ADD COLUMN IF NOT EXISTS ENABLE BIT(1) default true comment '사용여부';

