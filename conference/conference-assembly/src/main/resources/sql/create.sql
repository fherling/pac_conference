CREATE DATABASE `conference` /*!40100 DEFAULT CHARACTER SET latin1 */;

create table ConferenceTalk (
    id bigint not null,
    conference_id bigint,
    talk_id bigint,
    primary key (id),
    unique (conference_id, talk_id)
);

create table Room (
    id bigint not null,
    insertTimestamp datetime,
    lastUpdateTimestamp datetime,
    capacity integer not null check (capacity>=1),
    name varchar(255) not null,
    primary key (id),
    unique (name)
);


create table Speaker (
    id bigint not null,
    insertTimestamp datetime,
    lastUpdateTimestamp datetime,
    description varchar(255) not null,
    email varchar(255),
    firstName varchar(255) not null,
    name varchar(255) not null,
    primary key (id),
    unique (email),
    unique (name, firstName)
);


create table Talk (
    id bigint not null,
    insertTimestamp datetime,
    lastUpdateTimestamp datetime,
    description varchar(255) not null,
    duration bigint not null check (duration>=1),
    name varchar(255) not null,
    start datetime not null,
    primary key (id),
    unique (name, start, duration)
);


create table TalkRoom (
    id bigint not null,
    room_id bigint,
    talk_id bigint,
    primary key (id),
    unique (talk_id, room_id)
);

create table TalkSpeaker (
    id bigint not null,
    speaker_id bigint,
    talk_id bigint,
    primary key (id),
    unique (talk_id, speaker_id)
);

create table conference (
    id bigint not null,
    insertTimestamp datetime,
    lastUpdateTimestamp datetime,
    description varchar(255) not null,
    end datetime not null,
    name varchar(255) not null,
    start datetime not null,
    primary key (id)
);


alter table ConferenceTalk 
    add index FKF62DCC08F40E9C4 (conference_id), 
    add constraint FKF62DCC08F40E9C4 
    foreign key (conference_id) 
    references conference (id);


alter table ConferenceTalk 
    add index FKF62DCC084E976F44 (talk_id), 
    add constraint FKF62DCC084E976F44 
    foreign key (talk_id) 
    references Talk (id);


alter table TalkRoom 
    add index FKDF7557C7FCD94CE4 (room_id), 
    add constraint FKDF7557C7FCD94CE4 
    foreign key (room_id) 
    references Room (id);


alter table TalkRoom 
    add index FKDF7557C74E976F44 (talk_id), 
    add constraint FKDF7557C74E976F44 
    foreign key (talk_id) 
    references Talk (id);


alter table TalkSpeaker 
    add index FK4E5C119394C61C10 (speaker_id), 
    add constraint FK4E5C119394C61C10 
    foreign key (speaker_id) 
    references Speaker (id);


alter table TalkSpeaker 
    add index FK4E5C11934E976F44 (talk_id), 
    add constraint FK4E5C11934E976F44 
    foreign key (talk_id) 
    references Talk (id);


create table hibernate_sequence (
     next_val bigint 
);

insert into hibernate_sequence values ( 100 );