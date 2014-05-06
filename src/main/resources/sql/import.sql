-- Clean
DELETE FROM setting;
DELETE FROM helpmessage;
DELETE FROM person;
DELETE FROM account;
DELETE FROM accountactivation;

-- Test data
-- test@updapy.com / UpdapyPwd
INSERT INTO accountactivation(id, creationdate, updatedate, version, activationdate, active, generationkeydate, key) VALUES (nextval('account_activation_seq'), now(), null, 0, now(), true, now(), 'i8v3wb35qz17pxfa0exzmoy18gsmwwwzobhu5ne2nubb1hxs3i');
INSERT INTO account(id, creationdate, updatedate, version, activation_id, removal_id) VALUES (nextval('account_seq'), now(), null, 0, currval('account_activation_seq'), null);
INSERT INTO person(id, creationdate, updatedate, version, early, email, name, password, account_id) VALUES (nextval('person_seq'), now(), null, 0, false, 'test@updapy.com', 'Updapy', '$2a$10$faNVhJX.ZrvmpklDMFa.4OBWwG4GbYEQCFzTPaR9hJ.aHYU7zCRrC', currval('account_seq'));
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'DASHBOARD_HOW_TO', currval('person_seq'));
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'DASHBOARD_ALERT_DISABLED', currval('person_seq'));
INSERT INTO helpmessage(id, creationdate, updatedate, version, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'SEARCH_HOW_TO', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'ALERT_BY_EMAIL', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'EMAIL_FOR_EACH_APPLICATION', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, false, 'EMAIL_WEEKLY_DIGEST', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, version, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'NEWSLETTER', currval('person_seq'));
