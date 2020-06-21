insert into station (station_id, name) values (1, '강남역');
insert into station (station_id, name) values (2, '선릉역');
insert into station (station_id, name) values (3, '역삼역');
insert into station (station_id, name) values (4, '삼성역');
insert into station (station_id, name) values (5, '잠실역');
insert into station (station_id, name) values (6, '구의역');
insert into station (station_id, name) values (7, '시청역');
insert into station (station_id, name) values (8, '서울역');
insert into station (station_id, name) values (9, '사당역');

insert into line (line_id, name, start_time, end_time, interval_time) values (1, '2호선', CURRENT_TIME, CURRENT_TIME, 10);
insert into line (line_id, name, start_time, end_time, interval_time) values (2, '4호선', CURRENT_TIME, CURRENT_TIME, 10);

insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (1, null, 1, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (1, 1, 2, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (1, 2, 3, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (1, 3, 4, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (1, 4, 5, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (1, 5, 6, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (1, 6, 7, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (1, 7, 8, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (2, null, 2, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (2, 2, 9, 10, 10);
insert into line_station (line_id, pre_station_id, station_id, distance, duration) values (2, 9, 8, 10, 10);