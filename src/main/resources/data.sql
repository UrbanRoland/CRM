insert into roles(role) values('Vezető');
insert into roles(role) values('Ügyintéző');
insert into roles(role) values('Szerelő');
insert into users(email,password,username,enabled) values('urolir@gmail.comm','{bcrypt}$2a$10$pWx4lWz5WyUeomT63EYVDO9iB63EKK/nq00I8oF7n/DxfpRNBLJMS','Roland','TRUE');
insert into users_roles(user_id,role_id) values(1,1);
insert into users(email,password,username,enabled) values('urolir@gmail2.comm','{bcrypt}$2a$10$pWx4lWz5WyUeomT63EYVDO9iB63EKK/nq00I8oF7n/DxfpRNBLJMS','Roland Nagy','TRUE');
insert into users_roles(user_id,role_id) values(2,3);
insert into users(email,password,username,enabled) values('Rey.Padberg@karina.biz','12345','Isaac Gilmour','TRUE');
insert into users_roles(user_id,role_id) values(3,2);
insert into users(email,password,username,enabled) values('6nasr_loveegy@ginsarin.ga','12345','Siyana Leal','TRUE');
insert into users_roles(user_id,role_id) values(4,3);
insert into users(email,password,username,enabled) values('amuhamed_shamai@sceenic.com','12345','Siyana Leal','TRUE');
insert into users_roles(user_id,role_id) values(5,1);
insert into users(email,password,username,enabled) values('lmdoucoure1@xvnc.netz','12345','Chloe-Ann Driscoll','TRUE');
insert into users_roles(user_id,role_id) values(6,2);
insert into users(email,password,username,enabled) values('phritik.raj.9022o@discovino.com','12345','Wyatt Yates','TRUE');
insert into users_roles(user_id,role_id) values(7,3);
insert into users(email,password,username,enabled) values('3rafael@gmailup.com','12345','Danyl Mahoney','TRUE');
insert into users_roles(user_id,role_id) values(8,2);
insert into users(email,password,username,enabled) values('5sami-hms@jembott.com','12345','Aliyah Rayner','TRUE');
insert into users_roles(user_id,role_id) values(9,2);
insert into users(email,password,username,enabled) values('3walid.barca.180r@lolibox.ml','12345','Wyatt Yates','TRUE');
insert into users_roles(user_id,role_id) values(10,2);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Deckow-Crist','Sincere@april.biz','1-770-736-8031 x56442',392534892,'92998-3874','Gwenborough','Kulas Light Apt. 556','Leanne Graham',1400);


insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Romaguera-Jacobson','Sincere@april.biz','1-770-736-8031 x56442',392534892,'92998-3874','Gwenborough','Kulas Light Apt. 556','Ervin Howell',850);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Robel-Corkery','Julianne.OConner@kory.org','1-770-736-8031 x56442',392534892,'53919-4257','South Elvis','Hoeger Mall Apt 692','Clementine Bauch',3400);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Keebler LLC','Lucio_Hettinger@annie.ca','(254)954-12892',392534892,'92998-3874','Roscoeview','Kulas Light Apt. 556','Patricia Lebsack',5500);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Considine-Lockman','Karley_Dach@jasper.info','1-477-935-8478 x6430',1313112,'33263','Roscoeview','Skiles Walks Suite 351','Chelsey Dietrich',9567);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Johns Group','Telly.Hoeger@full.biz','210.067.4652',22475892,'58804-1039','Howemouth','Kulas Light Apt. 556','Mrs. Dennis Schulist',6456);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Abernathy Group','Sherwood@rosamond.me','586.493.6943 x140',1343234423,'45169','Aliyaview','Ellsworth Summit Suite 675.','Kurtis Weissnat',801);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Kurtis Weissnat','Telly.Hoeger@billy.biz','210.067.6132',682357872,'58804-1099','Howemouth','Rex Trail Suite 34.','Nicholas Runolfsdottir V',9598);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Yost and Sons','Chaim_McDermott@dana.io','(775)976-6794 x41206',131492,'76495-3109','Bartholomebury','Dayna Park Suite 449','Glenna Reichert',10034);

insert into client(NAME,EMAIL,PHONE,TAXNUMBER,ZIP_CODE,CITY,ADDRESS,CONTACT_PERSON,MONTHLY_FEE)
values('Hoeger LLC','Rey.Padberg@karina.biz','024-648-3804',23425892,'31428-2261','Lebsackbury','Kattie Turnpike Suite 198','Clementina DuBuque',15678);


insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-12-20 10:23:19.321','2020-08-03','Nagy Ádám','Nincs internet','Normál','Nyitott','Internet hiba', 4,1);


insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-03-20 10:23:19.321','2020-08-03','Nagy Ádám','Nem működik az egér','Normál','Nyitott','Egér hiba', 5,1);

insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-04-06 10:23:19.321','2020-11-03','Kiss Ádám','Nem működik a monitor','Normál','Nyitott','Monitor hiba', 7,2);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-11-19 10:23:19.321','2020-11-03','Nagy Adrián','Nem működik a monitor','Extra fontos','Zárt','Monitor hiba', 6,2);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-10-20 10:23:19.321','2020-11-03','Nagy Adrián','Nem működik a monitor','Fontos','Nyitott','Monitor hiba', 4,2);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-09-20 10:23:19.321','2020-11-03','Nagy Adrián','Nem működik a monitor','Normál','Felfügesztett','Monitor hiba', 3,1);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-08-20 10:23:19.321','2020-11-03','Nagy Adrián','Nem működik a monitor','Normál','Nyitott','Monitor hiba', 2,2);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-07-20 10:23:19.321','2020-11-03','Nagy Adrián','Nem működik a monitor','Normál','Nyitott','Monitor hiba', 1,1);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-06-20 10:23:19.321','2020-11-03','Nagy Adrián','Nem működik a monitor','Normál','Nyitott','Monitor hiba', 1,1);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-05-20 10:23:19.321','2020-11-03','Nagy Adrián','Nem működik a monitor','Extra fontos','Nyitott','Monitor hiba', 1,1);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-04-20 10:23:19.321','2010-11-03','Nagy Adrián','Nem működik a monitor','Kritikus hiba','Felfügesztett','Monitor hiba', 1,1);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-03-20 10:23:19.321','2029-11-03','Nagy Adrián','Nem működik a monitor','Fontos','Folyamatban','Monitor hiba', 1,1);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-02-20 10:23:19.321','2020-11-03','Kiss Ádám','Nem működik a monitor','Extra fontos','Zárt','HIBA', 7,2);
insert into ticket(CREATION_DATE ,DEADLINE,NOTIFIER ,DESCRIPTION,PRIORITY,STATUS,TITLE ,CLIENT_ID  ,USER_ID) 
values('2020-01-20 10:23:19.321','2020-11-03','Kiss Ádám','Nem működik a monitor','Extra fontos','Zárt','HIBA!!!!', 2,2);