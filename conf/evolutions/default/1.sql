# ---  !Ups

CREATE TABLE wine (
  id INTEGER,
  color VARCHAR (255),
  name VARCHAR(255),
  year INTEGER,
  denomination VARCHAR(255),
  country VARCHAR(255),
  description VARCHAR(1024),
  comments VARCHAR(2048)
);

-- insert test data
insert into wine values (1,"red","name",1999,"denom","country","desc","comments");

# --- !Downs

DELETE FROM wine;

DROP TABLE IF EXISTS wine;