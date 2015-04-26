DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS CONTAINERS;


CREATE TABLE USERS (
  NAME VARCHAR(50) NOT NULL,
  PASSWORD VARCHAR(50) NOT NULL,
  ROLE VARCHAR(50) NOT NULL,
  ENABLED TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY(NAME)
);

CREATE TABLE CONTAINERS (
  CONTAINER_ID INTEGER NOT NULL,
  CONTAINER_IMAGE VARCHAR(50),
  PRIMARY KEY(CONTAINER_ID)
);