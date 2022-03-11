create table customer (uuid varchar(255) not null,
    name varchar(255),
    surname varchar(255),
    photo_uuid varchar(255),
    createdBy_uuid varchar(255),
    updatedBy_uuid varchar(255),
    primary key (uuid),
    foreign key (photo_uuid) references photo (uuid));