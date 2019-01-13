create or replace view movie_years as select distinct(year(m.world_premiere)) as year From movie m;
CREATE USER 'movie_user'@'localhost' IDENTIFIED BY 'movie_user999';
GRANT ALL ON movie_service.* TO 'movie_user'@'localhost';