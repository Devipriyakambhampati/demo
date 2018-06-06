create table STATION

(

   StationId varchar not null,

   name varchar(255) not null,

   hdEnabled BOOLEAN,

   callSign varchar(255) not null,

   primary key(StationId)

);