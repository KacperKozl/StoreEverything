DELETE FROM NOTES;
DELETE FROM CATEGORIES;
INSERT INTO CATEGORIES(category_id,category_name)
VALUES (0,'gotowanie'),(1,'ważne'),(2,'przypominajki');
INSERT INTO NOTES(title,content,link,add_date,reminder_date,category_id)
VALUES('przepis','Żabie udka w sosie','www.wp.pl','2023-06-05','2023-06-07',(select CATEGORY_ID FROM CATEGORIES WHERE CATEGORIES.CATEGORY_NAME='gotowanie')),
('abrakdabra','czarna magia','www.onet.pl','2023-06-04','2023-06-06',(select CATEGORY_ID FROM CATEGORIES WHERE CATEGORIES.CATEGORY_NAME='ważne'));