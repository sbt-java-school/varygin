CREATE TABLE IF NOT EXISTS `recipes` (
  id          NUMBER(10) AUTO_INCREMENT NOT NULL,
  name        VARCHAR(250) NOT NULL ,
  description TEXT(1000),

  PRIMARY KEY (id)
);