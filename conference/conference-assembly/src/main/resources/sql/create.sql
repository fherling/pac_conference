    drop table if exists ConferenceTalk;


    drop table if exists Room;


    drop table if exists Speaker;


    drop table if exists Talk;


    drop table if exists TalkRoom;


    drop table if exists TalkSpeaker;


    drop table if exists conference;


    drop table if exists hibernate_sequence;


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
        end date not null,
        name varchar(255) not null,
        start date not null,
        primary key (id)
    );


    alter table ConferenceTalk 
        add index FK_CONFERENCE_ID (conference_id), 
        add constraint FK_CONFERENCE_ID 
        foreign key (conference_id) 
        references conference (id);


    alter table ConferenceTalk 
        add index FK_TALK_ID (talk_id), 
        add constraint FK_TALK_ID 
        foreign key (talk_id) 
        references Talk (id);


    alter table TalkRoom 
        add index FK_TALKROOM_ROOM_ID (room_id), 
        add constraint FK_ROOM_ID 
        foreign key (room_id) 
        references Room (id);


    alter table TalkRoom 
        add index FK_TALKROOM_TALK_ID (talk_id), 
        add constraint FK_TALKROOM_TALK_ID 
        foreign key (talk_id) 
        references Talk (id);


    alter table TalkSpeaker 
        add index FK_TALKSPEAKER_SPEAKER_ID (speaker_id), 
        add constraint FK_TALKSPEAKER_SPEAKER_ID 
        foreign key (speaker_id) 
        references Speaker (id);


    alter table TalkSpeaker 
        add index FK_TALKSPEAKER_TALK_ID (talk_id), 
        add constraint FK_TALKSPEAKER_TALK_ID 
        foreign key (talk_id) 
        references Talk (id);


    create table hibernate_sequence (
         next_val bigint 
    );