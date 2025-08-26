DELETE FROM  PUBLIC.T_CHARGE;
DELETE FROM  PUBLIC.T_MEMBER;

INSERT INTO PUBLIC.T_CHARGE (CHARGE_ID,NAME,AMOUNT,START_DATE,END_DATE) VALUES
	 (3,'割引きキャンペーンo',0        ,'2025-07-01','2025-09-19'),
	 (4,'基本料金o'          ,999999999,'2025-07-01',        NULL),
	 (5,'追加料金～0801o'    ,400      ,'2025-01-01','2025-08-01'),
	 (6,'追加料金0831～o'    ,300      ,'2025-08-31','2025-09-30'),
	 (7,'9月以降料金0901～x' ,4000     ,'2025-09-01',        NULL),
	 (8,'8月以前料金～0731x' ,5000     ,'2025-01-01','2025-07-31');

INSERT INTO PUBLIC.T_MEMBER (MEMBER_ID,MAIL,NAME,ADDRESS,START_DATE,END_DATE,PAYMENT_METHOD) VALUES
	 (1, 'example1@s-giken.com','1山田　太郎o','1東京都小平市','2025-01-01',        NULL,1),
	 (4, 'example2@s-giken.com','2山田　太郎o','2東京都小平市','2024-08-15','2024-12-31',1),
	 (5, 'example3@s-giken.com','3山田　太郎x','3東京都小平市','2025-01-01','2025-07-31',1),
	 (6, 'example4@s-giken.com','4山田　太郎o','4東京都小平市','2025-01-01','2025-08-01',1),
	 (7, 'example5@s-giken.com','5山田　太郎o','5東京都小平市','2025-08-01','2025-08-31',1),
	 (8, 'example6@s-giken.com','6山田　太郎o','6東京都小平市','2025-08-31','2025-09-30',1),
	 (9, 'example7@s-giken.com','7山田　太郎x','7東京都小平市','2025-09-01','2025-09-30',1);
