insert into speaker(id, name, firstname, description, email, insertTimestamp, lastUpdateTimestamp) values(1, 'Richter', 'Janett', '', 'jrichter@web.de', current_timestamp, current_timestamp);
insert into speaker(id, name, firstname, description,email, insertTimestamp, lastUpdateTimestamp) values(2, 'Herling', 'Frank', '', 'fherling@web.de', current_timestamp, current_timestamp);
insert into speaker(id, name, firstname, description,email, insertTimestamp, lastUpdateTimestamp) values(3, 'Mueller', 'Peter', '', 'pmueller@web.de', current_timestamp, current_timestamp);
insert into speaker(id, name, firstname, description,email, insertTimestamp, lastUpdateTimestamp) values(4, 'Schmidt', 'Juergen', '', 'jschmidt@web.de', current_timestamp, current_timestamp);


insert into room(id, name, capacity, insertTimestamp, lastUpdateTimestamp) values (1, 'Raum Frankfurt', 100, current_timestamp, current_timestamp);
insert into room(id, name, capacity, insertTimestamp, lastUpdateTimestamp) values (2, 'Raum Berlin', 150, current_timestamp, current_timestamp);
insert into room(id, name, capacity, insertTimestamp, lastUpdateTimestamp) values (3, 'Raum Bamberg', 100, current_timestamp, current_timestamp);
insert into room(id, name, capacity, insertTimestamp, lastUpdateTimestamp) values (4, 'Raum Stuttgart', 250, current_timestamp, current_timestamp);


--insert into talk(id, name, description, durationInMinutes, start, end, room_id,  updateTimeStamp) values(1, 'JEE Einfuehrung', 'Eine JEE Einfuehrung', '60', '09:00', '09:30', 1, current_timestamp);

--insert into speakersfortalk(id) values (1);
--insert into speakersfortalk_speaker(speakersfortalk_id, speakers_id) values(1,2);
--insert into speakersfortalk_speaker(speakersfortalk_id, speakers_id) values(1,3);




--insert into conference(id, name, description, start, end, updateTimeStamp) values(1, 'JEE Conference', 'The JEE conference', '2013-12-13', '2013-12-14', current_timestamp);
