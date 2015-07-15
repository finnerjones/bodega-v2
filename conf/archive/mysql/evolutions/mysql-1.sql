# ---  !Ups
-- PostgreSql

CREATE TABLE wine (
  wine_id INTEGER NOT NULL AUTO_INCREMENT,
  wine_name VARCHAR(255) NOT NULL,
  wine_type VARCHAR(64) NOT NULL,
  wine_country VARCHAR(255) NOT NULL,
  wine_description VARCHAR(1024),
  wine_year INTEGER NOT NULL,
  wine_grapes VARCHAR(256),
  wine_price DOUBLE(7,2),
  wine_cellar VARCHAR(256),
  wine_denom_origin VARCHAR(512),
  wine_vender VARCHAR(512),
  wine_alcohol DOUBLE(5,2),
  wine_date_purchased DATE,
  wine_date_opened DATE,
  wine_date_inserted DATE,
  wine_date_last_modified DATE,
  wine_comments VARCHAR(2048),
  PRIMARY KEY (wine_id)
);

-- insert test data
insert into wine values (1,"test wine name","test wine type","test wine country",
                          "test wine description",2015,"tempranillo",99999.99,"test wine cellar",
                          "test wine denomination origin","test wine vender",999.99,"2015-04-29","2015-04-29",CURRENT_DATE,CURRENT_DATE,"test wine comments");

# --- !Downs

DELETE FROM wine;

DROP TABLE IF EXISTS wine;