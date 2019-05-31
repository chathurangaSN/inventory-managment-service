drop table if exists currentStock;
drop table if exists DraftDetails;
drop table if exists DraftLog;
drop table if exists Stock;
drop table if exists StockDetails;
drop table if exists transaction_details;
drop table if exists transaction_log;
create table currentStock (
       id integer not null auto_increment,
        brandId integer,
        itemId integer,
        quantity double precision,
        uomId integer,
        primary key (id)
    ); 
create table DraftDetails (
       id integer not null auto_increment,
        brandId integer not null,
        itemId integer not null,
        quantity double precision not null,
        uomId integer not null,
        OSid integer,
        primary key (id)
    ); 
create table DraftLog (
       id integer not null auto_increment,
        date datetime,
        reason varchar(255) not null,
        userId integer not null,
        primary key (id)
    );
create table Stock (
       id integer not null auto_increment,
        date datetime,
        reason varchar(255) not null,
        userId integer not null,
        primary key (id)
    ); 
create table StockDetails (
       id integer not null auto_increment,
        brandId integer not null,
        itemId integer not null,
        quantity double precision not null,
        uomId integer not null,
        OSid integer,
        primary key (id)
    );
create table transaction_details (
       id integer not null auto_increment,
        brand_id integer,
        item_id integer,
        quantity double precision,
        uom_id integer,
        transaction_log_id integer,
        primary key (id)
    ); 
create table transaction_log (
       id integer not null auto_increment,
        date datetime,
        type varchar(255),
        user_id varchar(255),
        primary key (id)
    );  

