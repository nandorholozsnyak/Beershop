CREATE TABLE USER
	  (
		id INT NOT NULL AUTO_INCREMENT,
		username VARCHAR(50) unique,
		password VARCHAR(50), 
		PRIMARY KEY (ID)
	  );