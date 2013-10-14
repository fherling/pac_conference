--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements

insert into appartment(id, name, capacity) values(0, 'Ferienwohnung', 5);
insert into appartment(id, name, capacity) values(1, 'Pension 3 Bettzimmer', 3);
insert into appartment(id, name, capacity) values(2, 'Pension 2 Bettzimmer', 2);

insert into booking(id, name, email, phone_number, appartment_id, arrival, departure, numberOfPerson) values (0, 'herling', 'john.smith@mailinator.com', '2125551212', 2, '2013-10-12','2013-10-12', 1);
