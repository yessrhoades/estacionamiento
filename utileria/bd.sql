
create table auditoria
(
  id_servicio integer not null AUTO_INCREMENT,
  tipo_servicio text not null,
  fecha1 timestamp not null,
  fecha2 timestamp not null,
  costo decimal(10,2) not null,
  fecha timestamp not null,
  transaccion boolean default true,
  primary key (id_servicio)
) ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


create table pensiones
(
  id_pension varchar(20) primary key not null,
  tipo_pension varchar(50) unique not null,
  periodo integer not null,
  costo decimal(10,2) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into pensiones values ('pen24men','PENSION 24h MENSUAL',30,700.00);
insert into pensiones values ('pen24qui','PENSION 24h QUINCENAL',15,350.00);
insert into pensiones values ('pen12men','PENSION 12h MENSUAL',30,600.00);
insert into pensiones values ('pen12qui','PENSION 12h QUINCENAL',15,300.00);
insert into pensiones values ('pennomen','PENSION NOCHE MENSUAL',30,500.00);
insert into pensiones values ('pennoqui','PENSION NOCHE QUINCENAL',15,250.00);
insert into pensiones values ('pnsem24','PENSION SEMANAL',7,400.00);

create table clientes 
(
  id_cliente varchar(20) primary key not null,
  nombre text not null,
  alias text ,
  fecha_alta timestamp not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table coches 
(
  id_coche varchar(20) primary key not null,
  id_cliente varchar(20) not null,
  modelo text not null,
  placas varchar(20) unique,
  fecha_alta timestamp not null,
  foreign key (id_cliente) references clientes(id_cliente) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

  
create table servicio_pension 
(
  id integer not null AUTO_INCREMENT,
  id_servicio integer not null,
  id_pension varchar(20) not null,
  id_coche varchar(20) not null,
  activa boolean default true not null,
  primary key (id),
  foreign key (id_servicio) references auditoria(id_servicio) on delete cascade on update cascade,
  foreign key (id_pension) references pensiones(id_pension) on delete cascade on update cascade,
  foreign key (id_coche) references coches(id_coche) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


create table banios
(
	id_servbanio varchar(20) primary key not null,
	nomservbanio varchar(20) unique not null,
	costo decimal(10,2) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into banios values('servSan','SANITARIOS',5.00);

create table his_serv_banios 
(
  id integer not null AUTO_INCREMENT,
  tipo_servicio varchar(20) NOT NULL,
  costo decimal(10,2) NOT NULL,
  fecha timestamp NOT NULL,
  primary key (id),
  foreign key (tipo_servicio) references banios(id_servbanio) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table servicios_lote
(
	id_servicio_l varchar(20) primary key not null,
	nombre_serv varchar(20) unique not null,
	costo decimal(10,2) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into servicios_lote values('1lotnorm','NORMAL',10.00);
insert into servicios_lote values('2lotpendia','PENSION POR 1 DIA',120.00);
insert into servicios_lote values('3lotpendiacam','PENSION 1 DIA CAMION',150.00);
insert into servicios_lote values('4lotpennch','PENSION POR 1 NOCHE',70.00);
insert into servicios_lote values('5lotpennchcam','PENSION NOCHE CAMION',90.00);
insert into servicios_lote values('6lot8hrs','PENSION POR 8 HORAS',60.00);

create table lotes
(
	id_lote integer not null AUTO_INCREMENT,
	estado varchar(20) not null,
  servicio varchar(20) default null,
  inicio timestamp default null,
  primary key (id_lote),
  foreign key (servicio) references servicios_lote(id_servicio_l) on delete set null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');
insert into lotes(estado) values('DISPONIBLE');

create table his_serv_lote
(
	cns integer not null AUTO_INCREMENT,
	id_servicio integer not null,
	id_lote integer not null,
	id_servicio_l varchar(20) not null,
  tiempo varchar(20) default 'N/A',
  primary key (cns),
  foreign key (id_servicio) references auditoria(id_servicio) on delete cascade on update cascade,
	foreign key (id_lote) references lotes(id_lote) on delete cascade on update cascade,
	foreign key (id_servicio_l) references servicios_lote(id_servicio_l) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


create table datos_estacionamiento
(
  id integer not null AUTO_INCREMENT,
  nombre text DEFAULT NULL,
  direccion text DEFAULT NULL,
  colonia text DEFAULT NULL,
  cp int DEFAULT NULL,
  municipio text DEFAULT NULL,
  estado text DEFAULT NULL,
  regimen text DEFAULT NULL,  
  rfc text DEFAULT NULL,
  ticket_serie varchar(5) DEFAULT NULL,
  horario_lunes_viernes_apertura time DEFAULT NULL,
  horario_lunes_viernes_cierre time DEFAULT NULL,
  horario_sabado_apertura time DEFAULT NULL,
  horario_sabado_cierre time DEFAULT NULL,
  horario_domingo_apertura time DEFAULT NULL,
  horario_domingo_cierre time DEFAULT NULL,
  monto_perdida_ticket decimal(10,2) DEFAULT NULL,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO datos_estacionamiento VALUES (1,'ESTACIONAMIENTO LA CENTRAL','DIRECCION','COL. CENTRO',30710,'TAPACHULA','CHIAPAS','REGIMEN DE INCORPORACION FISCAL','RFC1234','B','08:00:00','20:00:00','08:00:00','20:00:00','08:00:00','20:00:00',30.00);

create table usuarios
(
	id integer not null AUTO_INCREMENT,
	nombre varchar(30) not null,
	contraseña text not null,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO usuarios VALUES (1,'ADMINISTRADOR','bb0d3d6fa6a964a87d73cad9d1056e71');





/*
DELIMITER $$
CREATE PROCEDURE ACTUALIZAR()
BEGIN
	declare max int unsigned default 1001;
	declare inicio int unsigned default 1000;
    declare id int unsigned default 100;
  while inicio <= max do
    update auditoria set id_servicio = inicio where id_servicio = id;
    set inicio = inicio + 1;
    set id = id + 1;
  end while;
END $$
DELIMITER ;

call ACTUALIZAR();


DELIMITER $$
CREATE PROCEDURE nuevoServicioPension(idPension varchar(20), tipoPension varchar(50), periodo integer, idCoche varchar(20),fecha date,precio decimal(10,2))
BEGIN
	DECLARE fechaFin date;
	IF periodo = 30 THEN
		SET fechaFin = (SELECT (fecha + INTERVAL 1 MONTH) - INTERVAL 1 DAY);
	END IF;
  IF periodo = 15 THEN
		SET fechaFin = (SELECT fecha + INTERVAL 14 DAY);
	END IF;
  IF periodo = 7 THEN
    SET fechaFin = (SELECT (fecha + INTERVAL 1 WEEK) - INTERVAL 1 DAY);
  END IF;
	START TRANSACTION;
		INSERT INTO servicio_pension values (idPension,idCoche,fecha,fechaFin,precio,current_timestamp);
		INSERT INTO auditoria values (default,tipoPension,fecha,fechaFin,precio,current_timestamp);
	COMMIT;
END $$
DELIMITER ;
--DROP PROCEDURE nuevoServicioPension

DELIMITER $$
CREATE PROCEDURE calcularTiempoCostoPC(lote integer, idServicio varchar(25), servicio varchar(25), costo decimal(10,2))
BEGIN
  DECLARE fechaFin timestamp;
  IF servicio = 'PENSION POR 1 DIA' OR servicio = 'PENSION DIA CAMION' THEN
    SET fechaFin = (SELECT current_timestamp + INTERVAL 1 DAY);
  END IF;
  IF servicio = 'PENSION POR 8 HORAS' THEN
    SET fechaFin = (SELECT current_timestamp + INTERVAL 8 HOUR);
  END IF;
  IF servicio = 'PENSION POR 1 NOCHE' OR servicio = 'PENSION NOCHE CAMION' THEN
    SET fechaFin = (SELECT STR_TO_DATE(concat(CURRENT_DATE + INTERVAL 1 DAY,' ','09',':','30',':','00'),'%Y-%m-%d %H:%i:%s'));
  END IF;
  START TRANSACTION;
    INSERT INTO his_serv_lote VALUES(default, lote, idServicio, current_timestamp, fechaFin, null, costo);
    INSERT INTO auditoria VALUES(default, servicio, current_timestamp, fechaFin, costo, current_timestamp);
  COMMIT;
END $$
DELIMITER ;
--drop PROCEDURE calcularTiempoCostoPC

DELIMITER $$
CREATE PROCEDURE calcularTiempoCosto(lote integer, idServicio varchar(25), servicio varchar(25), costo decimal(10,2))
BEGIN
  DECLARE fechaIn timestamp;
  DECLARE diferencia time;
  DECLARE horas integer;
  DECLARE minutos integer;
  DECLARE total decimal;
  SET fechaIn = (SELECT inicio FROM lotes WHERE id_lote = lote);
  SET diferencia = (select timediff(current_timestamp, fechaIn));
  SET horas = (select (hour(diferencia)));
  SET minutos = (select (minute(diferencia)));
  IF horas < 1 THEN
    SET total = costo;
  ELSE
    IF minutos = 0 THEN
      SET total = costo * horas;
    ELSE IF (minutos >= 1 AND minutos <= 15) THEN
      SET total = (costo * horas) + (costo * .5);
    END IF;
  END IF;
  START TRANSACTION;
    INSERT INTO his_serv_lote VALUES(default, lote, idServicio, fechaIn, current_timestamp, diferencia, total);
    INSERT INTO auditoria VALUES(default, servicio, fechaIn, current_timestamp, total, current_timestamp);
  COMMIT;
END $$
DELIMITER ;
--drop PROCEDURE calcularTiempoCosto

*/
