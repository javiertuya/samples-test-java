--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
delete from carreras;
insert into carreras(id,inicio,fin,fecha,descr) values 
	(100,'2016-10-05','2016-10-25','2016-11-09','finalizada'),
	(101,'2016-10-05','2016-10-25','2016-11-10','en fase 3'),
	(102,'2016-11-05','2016-11-09','2016-11-20','en fase 2'),
	(103,'2016-11-10','2016-11-15','2016-11-21','en fase 1'),
	(104,'2016-11-11','2016-11-15','2016-11-22','antes inscripcion');
--BEGINREDUCE
--para giis.demo.descuento:
delete from clientes;
insert into clientes(id,edad,nuevo,cupon,tarjeta)  values
	(1,18,'S','N','N'),
	(2,38,'S','S','N'),
	(3,21,'S','N','S'),
	(4,25,'N','N','N'),
	(5,40,'N','S','N'),
	(6,42,'N','N','S'),
	(7,39,'N','S','S');
--ENDREDUCE
