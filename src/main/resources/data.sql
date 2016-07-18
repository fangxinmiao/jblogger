delete from pageviews;
delete from post_tag;
delete from tags;
delete from comments;
delete from posts;
delete from user_role;
delete from roles;
delete from users;
delete from blog;

insert into blog(id,name,description, welcome_note) values(1,'JBlogger','My Experiment on Technology','Hi, This is Siva. I started this blog just to note down some points to refer later if required, just for Myself. This blog may contain very simple, stupid things which matters to me. Accidentally this blog may help the viewers, but no guarantee :-)');

insert into tags(id, label) values(1,'Java');
insert into tags(id, label) values(2,'JavaEE');
insert into tags(id, label) values(3,'Spring');
insert into tags(id, label) values(4,'Hibernate');
insert into tags(id, label) values(5,'JavaScript');

insert into roles(id, name, description) values(1,'ROLE_ADMIN','ROLE Administrator');
insert into roles(id, name, description) values(2,'ROLE_USER','ROLE User');

insert into users(id, email, password, name) values(1,'admin@gmail.com','$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS','Administrator');
insert into users(id, email, password, name) values(2,'siva@gmail.com','$2a$10$UFEPYW7Rx1qZqdHajzOnB.VBR3rvm7OI7uSix4RadfQiNhkZOi2fi','Siva Prasad');

insert into user_role(user_id, role_id) values(1,1);
insert into user_role(user_id, role_id) values(1,2);
insert into user_role(user_id, role_id) values(2,1);
insert into user_role(user_id, role_id) values(2,2);

insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(1,'Installing LinuxMint','installing-linuxmint','Installing LinuxMint 17', 'Installing LinuxMint 17', 1, '2014-01-20', now());
insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(2,'SpringBoot: Introducing SpringBoot', 'introducing-springBoot','SpringBoot is a new spring portfolio project which takes opinionated view of building production-ready Spring applications by drastically reducing the amount of configuration required.','SpringBoot is a new spring portfolio project which takes opinionated view of building production-ready Spring applications by drastically reducing the amount of configuration required. Spring Boot is taking the convention over configuration style to the next level by registering the default configurations automatically based on the classpath libraries available at runtime.', 2, '2014-06-20', null);
insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(3,'Exporting Spring Data JPA Repositories as REST Services using Spring Data REST','exporting-spring-data-jpa-repositories-as-rest-services-using-spring-data-rest','Spring Data modules provides various modules to work with various types of datasources like RDBMS, NOSQL stores etc in unified way.','Spring Data modules provides various modules to work with various types of datasources like RDBMS, NOSQL stores etc in unified way.  In my previous article  SpringMVC4 + Spring Data JPA + SpringSecurity configuration using JavaConfig I have explained how to configure Spring Data JPA using JavaConfig.',2, '2014-06-25', null);
insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(4,'SpringMVC4 + Spring Data JPA + SpringSecurity configuration using JavaConfig','springmvc4-springdatajpa-springsecurity-configuration-using-javaconfig','In this article we will see how to configure and integrate SpringMVC4, Spring Data JPA with Hibernate and SpringSecurity using JavaConfig.','In this article we will see how to configure and integrate SpringMVC4, Spring Data JPA with Hibernate and SpringSecurity using JavaConfig.', 2, '2014-04-20', now());
insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(5,'Book Review: Enterprise Application Development with Ext JS and Spring','book-review-enterprise-application-development-with-extjs-and-spring','I was asked to review "Enterprise Application Development with Ext JS and Spring" book by Packtpub guys and here is my review on the book.','I was asked to review "Enterprise Application Development with Ext JS and Spring" book by Packtpub guys and here is my review on the book. Actually now I am working on a project which is being developed using ExtJS and I thought of buying this book. But surprisingly on the very next day I was asked to review this book and gave me the ebook :-).',2, '2014-05-20', null);
insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(6,'Clean Code: Dont mix different levels of abstractions','cleancode-dont-mix-different-levels-of-abstractions','We spend more time on reading code than writing. So if the code is more readable then obviously it will increase the developer productivity. Many people associate readability of code with coding conventions like following standard naming conventions, closing file, DB resources etc etc.','We spend more time on reading code than writing. So if the code is more readable then obviously it will increase the developer productivity. Many people associate readability of code with coding conventions like following standard naming conventions, closing file, DB resources etc etc.  When it comes to code reviews most of the people focus on these trivial things only, like checking for naming convention violations, properly releasing resources in finally block or not.',2, '2014-03-20', null);
insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(7,'PrimeFaces Beginners Guide book published','primefaces-beginners-guide-book-published','I am glad to announce that my second book PrimeFaces Beginners Guide is published. As many of us know PrimeFaces is leading JSF component library for JSF based web applications.','I am glad to announce that my second book PrimeFaces Beginners Guide is published. As many of us know PrimeFaces is leading JSF component library for JSF based web applications. This PrimeFaces Beginners Guide book targets the Java developers with basic knowledge on JSF and jQuery and covers most of the commonly used PrimeFaces components.',2, '2014-03-20', now());
insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(8,'Packt Publishing "Java Persistence With MyBatis3" published','packt-publishing-java-persistence-with-mybatis3-published','Hurray...My first book "Java Persistence with MyBatis3" is published. I would like to thank Packt Publishers for giving me this opportunity to write on my favorite framework MyBatis.','Hurray...My first book "Java Persistence with MyBatis3" is published. I would like to thank Packt Publishers for giving me this opportunity to write on my favorite framework MyBatis.',2, '2014-02-20', null);
insert into posts(id, title, url, short_desc,content, created_by, created_on, updated_on) values(9,'Deploying BroadleafCommerce 2.0 on JBoss AS 7','deploying-broadleafcommerce-20-on-jbossas7','First 2 steps are not really related to Broadleaf specific, but mentioned to make it easy to follow(copy/paste) the steps.','First 2 steps are not really related to Broadleaf specific, but mentioned to make it easy to follow(copy/paste) the steps. Step#1: Configure DataSources in JBoss AS.',2, '2014-01-20', now());

insert into post_tag(post_id,tag_id) values(1,1);
insert into post_tag(post_id,tag_id) values(1,2);
insert into post_tag(post_id,tag_id) values(2,2);
insert into post_tag(post_id,tag_id) values(3,4);
insert into post_tag(post_id,tag_id) values(3,5);

insert into comments(id, post_id, email, name, content, created_on, updated_on) values(1, 1, 'guest@gmail.com', 'Guest','sample comment 1', now(), null);
insert into comments(id, post_id, email, name, content, created_on, updated_on) values(2, 2, 'test@gmail.com', 'Test','sample comment 2', now(), null);
insert into comments(id, post_id, email, name, content, created_on, updated_on) values(3, 2, 'test@gmail.com', 'Test','sample comment 3', now(), now());
