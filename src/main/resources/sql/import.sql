-- Clean
DELETE FROM setting;
DELETE FROM helpmessage;
DELETE FROM person;
DELETE FROM userconnection;
DELETE FROM applicationreference;

-- Indexes
CREATE UNIQUE INDEX userconnectionrank ON userconnection(userid, providerid, rank);
CREATE UNIQUE INDEX personemail ON person(email);

-- Application references
-- Firefox
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.mozilla.org/en-US/firefox/all/', 'firefox.png', 'Firefox');
-- Paint.NET
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.dotpdn.com/downloads/pdn.html', 'paintnet.png', 'Paint.NET');
-- Notepad++
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://notepad-plus-plus.org/download/', 'notepadplusplus.png', 'Notepad++');
-- Gimp
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.gimp.org/downloads/', 'gimp.png', 'GIMP');
-- Adobe Reader
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.adobe.com/support/downloads/product.jsp?product=10&platform=windows', 'adobereader.png', 'Adobe Reader');
-- VLC
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.videolan.org/vlc/download-windows.html', 'vlcmediaplayer.png', 'VLC Media Player');
-- Media Player Classic
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://mpc-hc.org/downloads/', 'mpclassic.png', 'Media Player Classic');
-- CCleaner
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.piriform.com/ccleaner/download', 'ccleaner.png', 'CCleaner');
-- Filezilla
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://filezilla-project.org/download.php?show_all=1', 'filezilla.png', 'Filezilla');
-- Chrome
INSERT INTO applicationreference(id, creationdate, updatedate, version, active, globalurl, iconfilename, name) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://chromium.woolyss.com/download/', 'chromium.png', 'Chromium');

-- Users
-- test@updapy.com / UpdapyPwd
INSERT INTO person(id, creationdate, updatedate, version, early, email, name, password, activationdate, active, lang, osversion, generationaccountkeydate, accountkey, generationrestkeydate, restkey) VALUES (nextval('person_seq'), now(), null, 0, false, 'test@updapy.com', 'Updapy', '$2a$10$faNVhJX.ZrvmpklDMFa.4OBWwG4GbYEQCFzTPaR9hJ.aHYU7zCRrC', now(), true, 'eng', 'WIN_32_BITS', now(), 'i8v3wb35qz17pxfa0exzmoy18gsmwwwzobhu5ne2nubb1hxs3i', now(), 'b06klbu3k7c582bjxmfd96psqg6katy6usrs067ge77bnleo0u');
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'DASHBOARD_HOW_TO', currval('person_seq'));
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'DASHBOARD_ALERT_DISABLED', currval('person_seq'));
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'SEARCH_HOW_TO', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'ALERT_BY_EMAIL', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'EMAIL_FOR_EACH_APPLICATION', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, false, 'EMAIL_WEEKLY_DIGEST', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'NEWSLETTER', currval('person_seq'));
