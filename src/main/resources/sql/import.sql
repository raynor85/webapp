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

-- Application references
-- Firefox
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.mozilla.org/en-US/firefox/all/', 'firefox.png', 'Firefox', 'firefox', true, 'APPLICATION');
-- Paint.NET
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.dotpdn.com/downloads/pdn.html', 'paintnet.png', 'Paint.NET', 'paintnet', true, 'APPLICATION');
-- Notepad++
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://notepad-plus-plus.org/download/', 'notepadplusplus.png', 'Notepad++', 'notepadplusplus', true, 'APPLICATION');
-- Gimp
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.gimp.org/downloads/', 'gimp.png', 'GIMP', 'gimp', true, 'APPLICATION');
-- Adobe Reader
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.adobe.com/support/downloads/product.jsp?product=10&platform=windows', 'adobereader.png', 'Adobe Reader', 'adobereader', true, 'APPLICATION');
-- VLC
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.videolan.org/vlc/download-windows.html', 'vlcmediaplayer.png', 'VLC Media Player', 'vlcmediaplayer', true, 'APPLICATION');
-- Media Player Classic
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://mpc-hc.org/downloads/', 'mpclassic.png', 'Media Player Classic', 'mediaplayerclassic', true, 'APPLICATION');
-- CCleaner
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.piriform.com/ccleaner/download', 'ccleaner.png', 'CCleaner', 'ccleaner', true, 'APPLICATION');
-- Filezilla
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://filezilla-project.org/download.php?show_all=1', 'filezilla.png', 'Filezilla', 'filezilla', true, 'APPLICATION');
-- Chromium
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://chromium.woolyss.com/download/', 'chromium.png', 'Chromium', 'chromium', true, 'APPLICATION');
-- Adobe Photoshop
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.adobe.com/support/downloads/product.jsp?product=39&platform=windows', 'adobephotoshop.png', 'Adobe Photoshop', 'adobephotoshop', true, 'APPLICATION');
-- 7-Zip
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.7-zip.org/download.html', '7zip.png', '7-Zip', '7zip', true, 'APPLICATION');
-- Thunderbird
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.mozilla.org/en-US/thunderbird/all.html', 'thunderbird.png', 'Thunderbird', 'thunderbird', true, 'APPLICATION');
-- Pidgin
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.pidgin.im/download/windows', 'pidgin.png', 'Pidgin', 'pidgin', true, 'APPLICATION');
-- K-lite codecs
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.free-codecs.com/k_lite_codec_pack_download.htm', 'klitecodecs.png', 'K-lite Codec Pack', 'klitecodecs', true, 'APPLICATION');
-- Songbird
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, false, 'http://sourceforge.net/projects/songbird.mirror/', 'songbird.png', 'Songbird', 'songbird', true, 'APPLICATION');
-- Picasa
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://picasa.google.com/', 'picasa.png', 'Picasa', 'picasa', true, 'APPLICATION');
-- iTunes
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://swdlp.apple.com/iframes/82/en_us/82_en_us.html', 'itunes.png', 'iTunes', 'itunes', true, 'APPLICATION');
-- Winmerge
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://winmerge.org/downloads/', 'winmerge.png', 'WinMerge', 'winmerge', true, 'APPLICATION');
-- PDFCreator
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.pdfforge.org/pdfcreator/choose-version', 'pdfcreator.png', 'PDFCreator', 'pdfcreator', true, 'APPLICATION');

-- Application versions
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-09 00:00:00.643', '29.0.1', 'https://download.mozilla.org/?product=firefox-29.0.1-SSL&os=win&lang=en-US', 'https://download.mozilla.org/?product=firefox-29.0.1-SSL&os=win&lang=fr', NULL, NULL, (select id from applicationreference where apiname = 'firefox'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-08-17 00:00:00.768', '3.5.11', 'http://www.dotpdn.com/files/Paint.NET.3.5.11.Install.zip', NULL, NULL, NULL, (select id from applicationreference where apiname = 'paintnet'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-02 00:00:01.899', '6.6.3', 'http://download.tuxfamily.org/notepadplus/6.6.3/npp.6.6.3.Installer.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'notepadplusplus'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-11-28 00:00:11.082', '2.8.10', 'http://download.gimp.org/pub/gimp/v2.8/windows/gimp-2.8.10-setup.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'gimp'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-13 00:00:11.661', '11.0.07', 'http://www.adobe.com/support/downloads/detail.jsp?ftpID=5784', NULL, NULL, NULL, (select id from applicationreference where apiname = 'adobereader'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-06-19 00:00:12.349', '2.1.3', 'http//get.videolan.org/vlc/2.1.3/win32/vlc-2.1.3-win32.exe', NULL, 'http://download.videolan.org/pub/videolan/vlc/last/win64/', NULL, (select id from applicationreference where apiname = 'vlcmediaplayer'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-01 00:00:12.711', '1.7.5', 'http://sourceforge.net/projects/mpc-hc/files/MPC%20HomeCinema%20-%20Win32/MPC-HC_v1.7.5_x86/MPC-HC.1.7.5.x86.exe/download', NULL, 'http://sourceforge.net/projects/mpc-hc/files/MPC%20HomeCinema%20-%20x64/MPC-HC_v1.7.5_x64/MPC-HC.1.7.5.x64.exe/download', NULL, (select id from applicationreference where apiname = 'mediaplayerclassic'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-22 00:00:13.021', '4.14.4707', 'http://www.piriform.com/ccleaner/download/standard', NULL, NULL, NULL, (select id from applicationreference where apiname = 'ccleaner'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-01 00:00:14.139', '3.8.1', 'http://sourceforge.net/projects/filezilla/files/FileZilla_Client/3.8.1/FileZilla_3.8.1_win32-setup.exe/download?nowrap', NULL, NULL, NULL, (select id from applicationreference where apiname = 'filezilla'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-02 00:00:14.801', '37.0.2001.0', 'https://storage.googleapis.com/chromium-browser-continuous/Win/271314/mini_installer.exe', NULL, 'https://storage.googleapis.com/chromium-browser-continuous/Win_x64/271314/mini_installer.exe', NULL, (select id from applicationreference where apiname = 'chromium'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-11-07 00:00:10.502', '13.0.1.3', 'http://www.adobe.com/support/downloads/detail.jsp?ftpID=5677', NULL, NULL, NULL, (select id from applicationreference where apiname = 'adobephotoshop'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2010-11-18 00:00:11.446', '9.20', 'http://downloads.sourceforge.net/sevenzip/7z920.exe', NULL, 'http://downloads.sourceforge.net/sevenzip/7z920-x64.msi', NULL, (select id from applicationreference where apiname = '7zip'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-04-29 00:00:12.266', '24.5.0', 'https://download.mozilla.org/?product=thunderbird-24.5.0&os=win&lang=en-US', 'https://download.mozilla.org/?product=thunderbird-24.5.0&os=win&lang=fr', NULL, NULL, (select id from applicationreference where apiname = 'thunderbird'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-04 00:00:12.831', '2.10.9', 'http://sourceforge.net/projects/pidgin/files/Pidgin/2.10.9/pidgin-2.10.9.exe/download', NULL, NULL, NULL, (select id from applicationreference where apiname = 'pidgin'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-04 00:00:13.047', '10.5', 'http://www.free-codecs.com/download_soft.php?d=7405&s=775&r=', NULL, NULL, NULL, (select id from applicationreference where apiname = 'klitecodecs'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-02-04 00:00:14.647', '2.2.0', 'http://sourceforge.net/projects/songbird.mirror/files/latest/download', NULL, NULL, NULL, (select id from applicationreference where apiname = 'songbird'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-12 00:00:14.716', '3.9', 'https://dl.google.com/picasa/picasa39-setup.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'picasa'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-28 00:00:16.115', '11.2.2', 'https://secure-appldnld.apple.com/iTunes11/031-02995.20140528.Tadim/iTunesSetup.exe', NULL, 'https://secure-appldnld.apple.com/iTunes11/031-02993.20140528.Pu4r5/iTunes64Setup.exe', NULL, (select id from applicationreference where apiname = 'itunes'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-02-02 00:00:16.755', '2.14.0', 'http://downloads.sourceforge.net/winmerge/WinMerge-2.14.0-Setup.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'winmerge'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-04-25 00:00:17.157', '1.7.3', 'http://download.pdfforge.org/download/pdfcreator/PDFCreator-stable', NULL, NULL, NULL, (select id from applicationreference where apiname = 'pdfcreator'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-05 00:00:03.576', '6.6.4', 'http://download.tuxfamily.org/notepadplus/6.6.4/npp.6.6.4.Installer.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'notepadplusplus'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-06 00:00:15.129', '37.0.2031.0', 'https://storage.googleapis.com/chromium-browser-continuous/Win/274979/mini_installer.exe', NULL, 'https://storage.googleapis.com/chromium-browser-continuous/Win_x64/274979/mini_installer.exe', NULL, (select id from applicationreference where apiname = 'chromium'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-06 00:00:18.135', '10.5.5', 'http://www.free-codecs.com/download_soft.php?d=7405&s=775&r=', NULL, NULL, NULL, (select id from applicationreference where apiname = 'klitecodecs'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-10 00:00:14.801', '37.0.2039.0', 'https://storage.googleapis.com/chromium-browser-continuous/Win/275756/mini_installer.exe', NULL, 'https://storage.googleapis.com/chromium-browser-continuous/Win_x64/275756/mini_installer.exe', NULL, (select id from applicationreference where apiname = 'chromium'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-11 00:00:00.643', '30.0', 'https://download.mozilla.org/?product=firefox-30.0&os=win&lang=en-US', 'https://download.mozilla.org/?product=firefox-30.0&os=win&lang=fr', NULL, NULL, (select id from applicationreference where apiname = 'firefox'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-11 00:00:12.266', '24.6.0', 'https://download.mozilla.org/?product=thunderbird-24.6.0&os=win&lang=en-US', 'https://download.mozilla.org/?product=thunderbird-24.6.0&os=win&lang=fr', NULL, NULL, (select id from applicationreference where apiname = 'thunderbird'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-13 00:00:01.899', '6.6.6', 'http://download.tuxfamily.org/notepadplus/6.6.6/npp.6.6.6.Installer.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'notepadplusplus'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-19 12:00:00.855', '2014', 'http://www.adobe.com/support/downloads/detail.jsp?ftpID=5771', NULL, NULL, NULL, (select id from applicationreference where apiname = 'adobephotoshop'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-22 12:00:01.308', '38.0.2063.0', 'https://storage.googleapis.com/chromium-browser-continuous/Win/278987/mini_installer.exe', NULL, 'https://storage.googleapis.com/chromium-browser-continuous/Win_x64/278987/mini_installer.exe', NULL, (select id from applicationreference where apiname = 'chromium'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-25 00:00:08.556', '6.6.7', 'http://download.tuxfamily.org/notepadplus/6.6.7/npp.6.6.7.Installer.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'notepadplusplus'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-25 12:00:05.407', '4.0', 'http://www.dotpdn.com/files/paint.net.4.0.install.zip', NULL, NULL, NULL, (select id from applicationreference where apiname = 'paintnet'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-26 00:00:02.661', '4.15.4725', 'http://www.piriform.com/ccleaner/download/standard', NULL, NULL, NULL, (select id from applicationreference where apiname = 'ccleaner'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-28 00:00:13.047', '10.6', 'http://www.free-codecs.com/download_soft.php?d=7405&s=775&r=', NULL, NULL, NULL, (select id from applicationreference where apiname = 'klitecodecs'));

-- Users
-- test@updapy.com / UpdapyPwd
INSERT INTO person(id, creationdate, updatedate, dbversion, early, email, name, password, activationdate, active, langupdate, osversion, generationaccountkeydate, accountkey, generationapikeydate, apikey, generationrsskeydate, rsskey, langemail) VALUES (nextval('person_seq'), now(), null, 0, false, 'test@updapy.com', 'Updapy', '$2a$10$faNVhJX.ZrvmpklDMFa.4OBWwG4GbYEQCFzTPaR9hJ.aHYU7zCRrC', now(), true, 'en', 'WIN_32_BITS', now(), 'i8v3wb35qz17pxfa0exzmoy18gsmwwwzobhu5ne2nubb1hxs3i', now(), 'b06klbu3k7c582bjxmfd96psqg6katy6usrs067ge77bnleo0u', now(), 'naedhgrk9zb5yk2tmnn71oatpmjwj61f3l7i4xncssur0880jv', 'en');
INSERT INTO helpmessage(id, creationdate, updatedate, dbversion, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'DASHBOARD_HOW_TO', currval('person_seq'));
INSERT INTO helpmessage(id, creationdate, updatedate, dbversion, hidden, type, person_id) VALUES (nextval('help_message_seq'), now(), null, 0, false, 'DASHBOARD_ALERT_DISABLED', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, dbversion, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'ALERT_BY_EMAIL', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, dbversion, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'EMAIL_FOR_EACH_APPLICATION', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, dbversion, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, false, 'EMAIL_WEEKLY_DIGEST', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, dbversion, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'NEWSLETTER', currval('person_seq'));
