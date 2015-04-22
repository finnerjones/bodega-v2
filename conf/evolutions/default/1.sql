# ---  !Ups

CREATE TABLE wine (
  wine_id INTEGER NOT NULL AUTO_INCREMENT,
  wine_name VARCHAR(255) NOT NULL,
  wine_type VARCHAR(64) NOT NULL,
  wine_country VARCHAR(255) NOT NULL,
  wine_description VARCHAR(1024),
  wine_year INTEGER NOT NULL,
  wine_grapes VARCHAR(256),
  wine_price DECIMAL(10,2),
  wine_celler VARCHAR(256),
  wine_denom_origin VARCHAR(512),
  wine_vender VARCHAR(512),
  wine_alcohol DECIMAL(5,2),
  wine_date_purchased DATE,
  wine_date_opened DATE,
  wine_date_inserted DATE,
  wine_date_last_modified DATE,
  wine_comments VARCHAR(2048),
  PRIMARY KEY (wine_id)
);

-- insert test data
insert into wine values (1,"test_wine_name","test_wine_type","test_wine_country",null,1999,null,null,null,null,null,null,null,null,null,null,null);

# --- !Downs

DELETE FROM wine;

DROP TABLE IF EXISTS wine;