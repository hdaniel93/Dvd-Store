insert into role (name) values ('ROLE_USER');

-- Insert two users (passwords are both 'password')
insert into user (username,enabled,password,role_id) values ('comic-book-guy',true,'password',1);
insert into user (username,enabled,password,role_id) values ('hdaniel93',true,'password',1);
insert into product (date, description, price, quantityinstock, title) VALUES ('2012-10-11 13:24:30', 'the best movie of all time', 10.5,5, 'toy story');
insert into product (date, description, price, quantityinstock, title) VALUES ('2012-10-11 13:24:30', 'the 2nd best movie of all time', 9,5, 'toy story 2');
insert into product (date, description, price, quantityinstock, title) VALUES ('2012-10-11 13:24:30', 'the 3rd best movie of all time', 10.5,5, 'toy story 3');
insert into product (date, description, price, quantityinstock, title) VALUES ('2012-10-11 13:24:30', 'a pretty good movie', 2,5, 'lion king');
insert into product (date, description, price, quantityinstock, title) VALUES ('2012-10-11 13:24:30', 'sort of okay I guess', 10.5,5, 'lion king 2');



insert into comment (product_id,user_id,text) values (1,1,'worst movie EVER');
insert into orders (address,date,price,user_id) values('faa side drawda','2017-04-04 13:24:30',19.5,1);
insert into orderline(quantity,orders_id,product_id) values(1,1,1);
insert into orderline(quantity,orders_id,product_id) values(1,1,2);