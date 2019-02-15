
-- 创建用户活动关系视图
DROP TABLE IF EXISTS `v_account_activity`;
DROP VIEW IF EXISTS `v_account_activity`;
CREATE VIEW `v_account_activity` AS
  SELECT b.dataid,b.accountid,b.activityid,a.title,a.content,a.`start`,a.`end`,a.image
  FROM t_activity a,t_account_activity b WHERE a.activityid = b.activityid;

-- 创建粉丝（关注）视图
DROP TABLE IF EXISTS v_fans;
DROP VIEW IF EXISTS v_fans;
CREATE VIEW v_fans AS
  select a.fansid,a.fromuser,a.touser,c.head fromhead,c.`name` fromname,b.head tohead,b.`name` toname
  from t_fans a ,t_account b ,t_account c WHERE a.touser = b.id AND a.fromuser = c.id;

-- 创建推荐信息关系视图
DROP TABLE IF EXISTS `v_recommend`;
DROP VIEW IF EXISTS `v_recommend`;
CREATE VIEW `v_recommend` AS
  SELECT a.dataid,a.touser,a.fromuser,b.name fromname,b.head fromhead,c.name toname,c.head tohead
  FROM t_recommend a,t_account b,t_account c
  WHERE a.fromuser = b.id and a.touser = c.id;