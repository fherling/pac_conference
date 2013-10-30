insert into speaker(id, name, firstname, description, email, insertTimestamp, lastUpdateTimestamp) values(1, 'Richter', 'Janett', '', 'jrichter@web.de', current_timestamp, current_timestamp);
insert into speaker(id, name, firstname, description,email, insertTimestamp, lastUpdateTimestamp) values(2, 'Herling', 'Frank', '', 'fherling@web.de', current_timestamp, current_timestamp);
insert into speaker(id, name, firstname, description,email, insertTimestamp, lastUpdateTimestamp) values(3, 'Mueller', 'Peter', '', 'pmueller@web.de', current_timestamp, current_timestamp);
insert into speaker(id, name, firstname, description,email, insertTimestamp, lastUpdateTimestamp) values(4, 'Schmidt', 'Juergen', '', 'jschmidt@web.de', current_timestamp, current_timestamp);


insert into room(id, name, capacity, insertTimestamp, lastUpdateTimestamp) values (5, 'Raum Frankfurt', 100, current_timestamp, current_timestamp);
insert into room(id, name, capacity, insertTimestamp, lastUpdateTimestamp) values (6, 'Raum Berlin', 150, current_timestamp, current_timestamp);
insert into room(id, name, capacity, insertTimestamp, lastUpdateTimestamp) values (7, 'Raum Bamberg', 100, current_timestamp, current_timestamp);
insert into room(id, name, capacity, insertTimestamp, lastUpdateTimestamp) values (8, 'Raum Stuttgart', 250, current_timestamp, current_timestamp);


insert into conference(id, name, description, start, end, insertTimestamp, lastUpdateTimestamp) values(9, 'JEE Conference', 'The JEE conference', '2013-12-13', '2013-12-17', current_timestamp, current_timestamp);

insert into talk(id, name, description, duration, start, insertTimestamp, lastUpdateTimestamp ) values(10, 'JEE Einfuehrung', 'Eine JEE Einfuehrung', '60', '2013-12-13 09:00', current_timestamp, current_timestamp);



insert into conferencetalk (id, conference_id, talk_id) values(11,9,10);
insert into talkspeaker (id,  talk_id, speaker_id) values(12,10,1);
insert into talkroom (id,  talk_id, room_id) values(13,10,8);

