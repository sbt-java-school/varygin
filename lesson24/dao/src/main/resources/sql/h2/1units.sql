CREATE TABLE IF NOT EXISTS `units` (
  id          NUMBER(10) AUTO_INCREMENT NOT NULL,
  name        VARCHAR(250) NOT NULL ,
  short_name  VARCHAR(25) NOT NULL ,

  PRIMARY KEY (id),
  CONSTRAINT uq_name UNIQUE (name)
);