--ログインテスト用ユーザ（パスワードはtest）
--insertしたユーザでログインエラーになる（暗号文が異なる）場合は
--プログラムから作成する（AuditConfigとSecurityConfigとnav.htmlの調整要）
insert into local_user
(name, email, password, enabled, ver, created_by, created_date, modified_by, modified_date)
select 'テスト用', 'test@example.com'
, '$2a$10$1DAAW4tK/qoTgt5TBlcwjebnjtJvXKvDVGeJ6k35gRg/yXm6lB9YW'
, 1
, 0
, 1, current_timestamp, 1, current_timestamp
from dual
where not exists (select * from local_user);
