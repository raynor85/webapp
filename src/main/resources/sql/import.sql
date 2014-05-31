-- Clean
DELETE FROM setting;
DELETE FROM helpmessage;
DELETE FROM person;
DELETE FROM userconnection;

-- Indexes
CREATE UNIQUE INDEX userconnectionrank ON userconnection(userid, providerid, rank);
CREATE UNIQUE INDEX personemail ON person(email);

-- Application references
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cdn.mozilla.net/pub/mozilla.org/firefox/releases/latest/', 'firefox.png', 'Firefox');
INSERT INTO applicationversion(id, creationdate, updatedate, version, versiondate, versionnumber, win32urlen, win64urlen, win32urlfr, win64urlfr, reference_id) VALUES (nextval('application_version_seq'), now(), null, 0, '2014-04-29 00:00:00.000', '29.0', 'http://download.cdn.mozilla.net/pub/mozilla.org/firefox/releases/29.0/win32/en-US/Firefox%20Setup%2029.0.exe', null, 'http://download.cdn.mozilla.net/pub/mozilla.org/firefox/releases/29.0/win32/fr/Firefox%20Setup%2029.0.exe', null, currval('application_reference_seq'));
INSERT INTO applicationversion(id, creationdate, updatedate, version, versiondate, versionnumber, win32urlen, win64urlen, win32urlfr, win64urlfr, reference_id) VALUES (nextval('application_version_seq'), now(), null, 0, '2014-05-07 00:00:00.000', '29.0.1', 'http://download.cdn.mozilla.net/pub/mozilla.org/firefox/releases/29.0.1/win32/en-US/Firefox%20Setup%2029.0.1.exe', null, 'http://download.cdn.mozilla.net/pub/mozilla.org/firefox/releases/29.0.1/win32/fr/Firefox%20Setup%2029.0.1.exe', null, currval('application_reference_seq'));
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://www.apple.com/itunes/download/', 'itunes.png', 'iTunes');

-- Users
-- test@updapy.com / UpdapyPwd
INSERT INTO person(id, creationdate, updatedate, version, early, email, name, password, activationdate, active, lang, generationaccountkeydate, accountkey, generationrestkeydate, restkey) VALUES (nextval('person_seq'), now(), null, 0, false, 'test@updapy.com', 'Updapy', '$2a$10$faNVhJX.ZrvmpklDMFa.4OBWwG4GbYEQCFzTPaR9hJ.aHYU7zCRrC', now(), true, 'eng', now(), 'i8v3wb35qz17pxfa0exzmoy18gsmwwwzobhu5ne2nubb1hxs3i', now(), 'b06klbu3k7c582bjxmfd96psqg6katy6usrs067ge77bnleo0u');
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'DASHBOARD_HOW_TO', currval('person_seq'));
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'DASHBOARD_ALERT_DISABLED', currval('person_seq'));
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'SEARCH_HOW_TO', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'ALERT_BY_EMAIL', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'EMAIL_FOR_EACH_APPLICATION', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, false, 'EMAIL_WEEKLY_DIGEST', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'NEWSLETTER', currval('person_seq'));
