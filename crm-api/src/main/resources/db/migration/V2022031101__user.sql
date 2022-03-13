create table users (
    uuid varchar(255) not null,
    username varchar(255),
    createdBy_uuid varchar(255),
    primary key (uuid),
    foreign key (createdBy_uuid) references users (uuid));