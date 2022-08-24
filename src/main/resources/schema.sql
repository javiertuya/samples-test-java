--Primero se deben borrar todas las tablas (de detalle a maestro) y lugo anyadirlas (de maestro a detalle)
--(en este caso en cada aplicacion se usa solo una tabla, por lo que no hace falta)

--Para giis.demo.tkrun:
drop table Carreras;
create table Carreras (id int primary key not null, inicio date not null, fin date not null, fecha date not null, descr varchar(32), check(inicio<=fin), check(fin<fecha));

--BEGINREDUCE
--para giis.demo.descuento:
drop table Clientes;
create table Clientes (id int primary key not null, edad int not null, nuevo char(1) not null, cupon char(1) not null, tarjeta char(1) not null, check(nuevo='S' or nuevo='N'), check(cupon='S' or cupon='N'), check(tarjeta='S' or tarjeta='N') );
--ENDREDUCE
