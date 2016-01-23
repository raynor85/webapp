-- Clean
DELETE FROM setting;
DELETE FROM helpmessage;
DELETE FROM applicationfollow;
DELETE FROM person;
DELETE FROM userconnection;
DELETE FROM applicationversion;
DELETE FROM applicationreference;
DELETE FROM emailcounter;
DROP INDEX IF EXISTS userconnectionrank;
DROP INDEX IF EXISTS personemail;
DROP INDEX IF EXISTS applicationreferencename;
DROP INDEX IF EXISTS applicationreferenceapiname;

-- Indexes
CREATE UNIQUE INDEX userconnectionrank ON userconnection(userid, providerid, rank);
CREATE UNIQUE INDEX personemail ON person(email);
CREATE UNIQUE INDEX applicationreferencename ON applicationreference(name);
CREATE UNIQUE INDEX applicationreferenceapiname ON applicationreference(apiname);

-- Email counter
INSERT INTO emailcounter(id, count, date) VALUES (1, 0, now());
