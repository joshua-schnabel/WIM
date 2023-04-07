CREATE TABLE guests (
  id VARCHAR(16) NOT NULL,
  firstname VARCHAR(255),
  lastname VARCHAR(255),
  guestType VARCHAR(32),
  invitationId VARCHAR(16),
  PRIMARY KEY (id)
);

CREATE TABLE invitations (
  id VARCHAR(16) NOT NULL,
  code VARCHAR(8) NOT NULL,
  name VARCHAR(255),
  specialRequest VARCHAR(255),
  specialRequestAccepted BOOLEAN,
  specialRequestAnswer VARCHAR(255),
  status VARCHAR(32),
  PRIMARY KEY (id),
  UNIQUE (code)
);

CREATE INDEX idx_invitations_code ON invitations (code);

CREATE TABLE guestStati (
  id INT PRIMARY KEY AUTO_INCREMENT,
  guestId VARCHAR(16),
  accepted BOOLEAN,
  invitationId VARCHAR(16) NOT NULL,
  PRIMARY KEY (id)
);