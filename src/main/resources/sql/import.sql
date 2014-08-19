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
-- Media Player Classic - HC
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://mpc-hc.org/downloads/', 'mpclassichc.png', 'MPC - HC', 'mediaplayerclassichc', true, 'APPLICATION');
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
-- Avast
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/Avast-Free-Antivirus/3000-2239_4-10019223.html', 'avast.png', 'Avast! Free Antivirus', 'avast', false, 'APPLICATION');
-- Spybot
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://fileforum.betanews.com/detail/Spybot-Search-Destroy/1043809773', 'spybot.png', 'Spybot', 'spybot', false, 'APPLICATION');
-- PDF-XChange Viewer
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.tracker-software.com/history/viewer_history.xml', 'pdfxchangeviewer.png', 'PDF-XChange Viewer', 'pdfxchangeviewer', false, 'APPLICATION');
-- Media Player Classic - BE
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://sourceforge.net/p/mpcbe/activity/feed', 'mpclassicbe.png', 'MPC - BE', 'mediaplayerclassicbe', false, 'APPLICATION');
-- XnView
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.xnview.com/en/xnview/', 'xnview.png', 'XnView', 'xnview', false, 'APPLICATION');
-- Glary Utilities
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.glarysoft.com/', 'glaryutilities.png', 'Glary Utilities', 'glaryutilities', false, 'APPLICATION');
-- AdwCleaner
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://toolslib.net/downloads/viewdownload/1-adwcleaner/', 'adwcleaner.png', 'AdwCleaner', 'adwcleaner', false, 'APPLICATION');
-- RSSOwl
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.rssowl.org/', 'rssowl.png', 'RSSOwl', 'rssowl', false, 'APPLICATION');
-- Slim Drivers
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/SlimDrivers-Free/3000-18513_4-75279940.html', 'slimdrivers.png', 'SlimDrivers', 'slimdrivers', false, 'DRIVER');
-- XnViewMP
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.xnview.com/en/xnviewmp/', 'xnviewmp.png', 'XnViewMp', 'xnviewmp', false, 'APPLICATION');
-- VMware Workstation
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://my.vmware.com/web/vmware/info/slug/desktop_end_user_computing/vmware_workstation/10_0', 'vmwareworkstation.png', 'VMware Workstation', 'vmwareworkstation', false, 'APPLICATION');
-- KeePass pro
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://keepass.info/download.html', 'keepasspro.png', 'KeePass Pro', 'keepasspro', false, 'APPLICATION');
-- YoWindow
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://yowindow.com/download', 'yowindow.png', 'YoWindow', 'yowindow', false, 'APPLICATION');
-- Gmail Notifier Pro
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.gmailnotifier.se/download.php', 'gmailnotifier.png', 'Gmail Notifier', 'gmailnotifier', false, 'APPLICATION');
-- Java RE
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://java.com/en/download/manual.jsp', 'javare.png', 'Java RE', 'javare', false, 'APPLICATION');
-- Spotify
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/Spotify/3000-2141_4-10912348.html', 'spotify.png', 'Spotify', 'spotify', false, 'APPLICATION');
-- Skype
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/Skype/3000-2349_4-10225260.html', 'skype.png', 'Skype', 'skype', false, 'APPLICATION');
-- Flash Player
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.adobe.com/products/flashplayer/distribution3.html', 'flashplayer.png', 'Flash Player', 'flashplayer', false, 'APPLICATION');
-- Flash Player for IE
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.adobe.com/products/flashplayer/distribution3.html', 'flashplayerie.png', 'Flash Player for IE', 'flashplayerie', false, 'APPLICATION');
-- Microsoft Silverlight
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.microsoft.com/silverlight/', 'silverlight.png', 'Silverlight', 'silverlight', false, 'APPLICATION');
-- Shockwave Player
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.adobe.com/products/shockwaveplayer/distribution3.html', 'shockwaveplayer.png', 'Shockwave Player', 'shockwaveplayer', false, 'APPLICATION');
-- Adobe Air
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.adobe.com/products/air/runtime-distribution3.html', 'adobeair.png', 'Adobe Air', 'adobeair', false, 'APPLICATION');
-- Eclipse JEE
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://www.eclipse.org/downloads/', 'eclipsejee.png', 'Eclipse JEE', 'eclipsejee', false, 'APPLICATION');
-- Eclipse Standard
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://www.eclipse.org/downloads/', 'eclipsestandard.png', 'Eclipse Standard', 'eclipsestandard', false, 'APPLICATION');
-- IntelliJ IDEA Ultimate
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.jetbrains.com/js2/version.js', 'intellijideaultimate.png', 'IntelliJ IDEA Ultimate', 'intellijideaultimate', false, 'APPLICATION');
-- IntelliJ IDEA Community
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.jetbrains.com/js2/version.js', 'intellijideacommunity.png', 'IntelliJ IDEA Community', 'intellijideacommunity', false, 'APPLICATION');
-- Sublime Text 2
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.sublimetext.com/2', 'sublimetext2.png', 'Sublime Text 2', 'sublimetext2', false, 'APPLICATION');
-- Balsamiq
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://scripts.balsamiq.com/b/mockups-desktop/version.jsonp', 'balsamiq.png', 'Balsamiq', 'balsamiq', false, 'APPLICATION');
-- PenguiNet
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.siliconcircus.com/', 'penguinet.png', 'PenguiNet', 'penguinet', false, 'APPLICATION');
-- Unlocker
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.emptyloop.com/unlocker/', 'unlocker.png', 'Unlocker', 'unlocker', false, 'APPLICATION');
-- Avira Antivirus Free
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.avira.com/en/download/product/avira-free-antivirus', 'aviraantivirusfree.png', 'Avira Antivirus', 'aviraantivirusfree', false, 'APPLICATION');
-- Bullzip PDF Printer Community
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.bullzip.com/products/pdf/download.php', 'bullzippdfprintercommmunity.png', 'Bullzip PDF Printer', 'bullzippdfprintercommmunity', false, 'APPLICATION');
-- Revo Uninstaller Free
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.revouninstaller.com/revo_uninstaller_free_download.html', 'revouninstallerfree.png', 'Revo Uninstaller', 'revouninstallerfree', false, 'APPLICATION');
-- Malwarebytes Anti-Malware Free
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://www.malwarebytes.org/downloads/', 'malwarebytesantimalwarefree.png', 'Malwarebytes Anti-Malware', 'malwarebytesantimalwarefree', false, 'APPLICATION');
-- SQuirreL SQL
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.squirrelsql.org/', 'squirrelsql.png', 'SQuirreL SQL', 'squirrelsql', false, 'APPLICATION');
-- HeidiSQL
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.heidisql.com/download.php', 'heidisql.png', 'HeidiSQL', 'heidisql', false, 'APPLICATION');
-- PDFsam
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.pdfsam.org/download/', 'pdfsam.png', 'PDFsam', 'pdfsam', false, 'APPLICATION');
-- Advanced SystemCare
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/Advanced-SystemCare-Free/3000-2086_4-10407614.html', 'advancedsystemcare.png', 'Advanced SystemCare', 'advancedsystemcare', false, 'APPLICATION');
-- IrfanView
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/IrfanView/3000-2192_4-10021962.html', 'irfanview.png', 'IrfanView', 'irfanview', false, 'APPLICATION');
-- YTD Video Downloader
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/YTD-Video-Downloader/3000-2071_4-10647340.html', 'ytdvideodownloader.png', 'YTD Video Downloader', 'ytdvideodownloader', false, 'APPLICATION');
-- Ad-Aware Free
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/Ad-Aware-Free-Antivirus/3000-8022_4-10045910.html', 'adawarefree.png', 'Ad-Aware Free', 'adawarefree', false, 'APPLICATION');
-- GOM Media Player
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/GOM-Media-Player/3000-13632_4-10551786.html', 'gommediaplayer.png', 'GOM Media Player', 'gommediaplayer', false, 'APPLICATION');
-- Smart Defrag
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/Smart-Defrag-3/3000-2094_4-10759533.html', 'smartdefrag.png', 'Smart Defrag', 'smartdefrag', false, 'APPLICATION');
-- Virtual DJ
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/Virtual-DJ/3000-18502_4-10212112.html', 'virtualdj.png', 'Virtual DJ', 'virtualdj', false, 'APPLICATION');
-- PhotoScape
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/PhotoScape/3000-2192_4-10703122.html', 'photoscape.png', 'PhotoScape', 'photoscape', false, 'APPLICATION');
-- Driver Max
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/DriverMax/3000-18513_4-10572602.html', 'drivermax.png', 'Driver Max', 'drivermax', false, 'DRIVER');
-- PrimoPDF
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/PrimoPDF/3000-18497_4-10264577.html', 'primopdf.png', 'PrimoPDF', 'primopdf', false, 'APPLICATION');
-- FastStone Image Viewer
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/FastStone-Image-Viewer/3000-2192_4-10324485.html', 'faststoneimageviewer.png', 'FastStone Image Viewer', 'faststoneimageviewer', false, 'APPLICATION');
-- WinRAR
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.rarlab.com/download.htm', 'winrar.png', 'WinRAR', 'winrar', false, 'APPLICATION');
-- WinZip
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.winzip.com/dprob.html', 'winzip.png', 'WinZip', 'winzip', false, 'APPLICATION');
-- BitTorrent
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.bittorrent.com/downloads/win', 'bittorrent.png', 'BitTorrent', 'bittorrent', false, 'APPLICATION');
-- µTorrent
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.utorrent.com/downloads/win', 'utorrent.png', 'µTorrent', 'utorrent', false, 'APPLICATION');
-- Popcorn Time
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.time4popcorn.eu', 'popcorntime.png', 'Time 4 Popcorn', 'time4popcorn', false, 'APPLICATION');
-- Opera
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.opera.com/download/guide/?os=windows&list=all', 'opera.png', 'Opera', 'opera', false, 'APPLICATION');
-- Captvty
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://captvty.fr', 'captvty.png', 'Captvty', 'captvty', false, 'APPLICATION');
-- Tor Browser
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://www.torproject.org/projects/torbrowser.html.en', 'torbrowser.png', 'Tor Browser', 'torbrowser', false, 'APPLICATION');
-- QuickTime
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://swdlp.apple.com/iframes/81/en_us/81_en_us.html', 'quicktime.png', 'QuickTime', 'quicktime', false, 'APPLICATION');
-- TeamViewer
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.teamviewer.com/en/download/windows.aspx', 'teamviewer.png', 'TeamViewer', 'teamviewer', false, 'APPLICATION');
-- KMPlayer
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.kmplayer.com/', 'kmplayer.png', 'KMPlayer', 'kmplayer', false, 'APPLICATION');
-- Audacity
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://audacity.sourceforge.net/download/windows', 'audacity.png', 'Audacity', 'audacity', false, 'APPLICATION');
-- RoboForm
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.roboform.com/download', 'roboform.png', 'RoboForm', 'roboform', false, 'APPLICATION');
-- USB Disk Security
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.zbshareware.com/lang/eng/download.html', 'usbdisksecurity.png', 'USB Disk Security', 'usbdisksecurity', false, 'APPLICATION');
-- SyncBackPro
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.2brightsparks.com/download-syncbackpro.html', 'syncbackpro.png', 'SyncBackPro', 'syncbackpro', false, 'APPLICATION');
-- SyncBackSE
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.2brightsparks.com/download-syncbackse.html', 'syncbackse.png', 'SyncBackSE', 'syncbackse', false, 'APPLICATION');
-- TomTom Home
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://uk.support.tomtom.com/app/answers/detail/a_id/10020/', 'tomtomhome.png', 'TomTom Home', 'tomtomhome', false, 'APPLICATION');
-- Chrome
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/Google-Chrome/3000-2356_4-10881381.html', 'chrome.png', 'Chrome', 'chrome', false, 'APPLICATION');
-- QuiteRSS
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://quiterss.org/en/download', 'quiterss.png', 'QuiteRSS', 'quiterss', false, 'APPLICATION');
-- ESET NOD32 Antivirus
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.eset.com/us/download/home/detail/family/2/', 'esetnod32antivirus.png', 'ESET NOD32 Antivirus', 'esetnod32antivirus', false, 'APPLICATION');
-- PotPlayer
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://daumpotplayer.com/download/', 'potplayer.png', 'PotPlayer', 'potplayer', false, 'APPLICATION');
-- Disk Defrag Touch
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.auslogics.com/en/software/disk-defrag-touch/', 'diskdefragtouch.png', 'Disk Defrag Touch', 'diskdefragtouch', false, 'APPLICATION');
-- UltraDefrag
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://ultradefrag.sourceforge.net/en/index.html?download', 'ultradefrag.png', 'UltraDefrag', 'ultradefrag', false, 'APPLICATION');
-- XMedia Recode
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.xmedia-recode.de/download.html', 'xmediarecode.png', 'XMedia Recode', 'xmediarecode', false, 'APPLICATION');
-- Speccy
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.piriform.com/speccy/download', 'speccy.png', 'Speccy', 'speccy', false, 'APPLICATION');
-- RAM Disk
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.softperfect.com/products/ramdisk/', 'ramdisk.png', 'RAM Disk', 'ramdisk', false, 'APPLICATION');
-- Popcorn Time
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://popcorntime.io/', 'popcorntime.png', 'Popcorn Time', 'popcorntime', false, 'APPLICATION');
-- PlayClaw
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.playclaw.com/download.php', 'playclaw.png', 'PlayClaw', 'playclaw', false, 'APPLICATION');
-- Subsonic
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.subsonic.org/pages/download.jsp', 'subsonic.png', 'Subsonic', 'subsonic', false, 'APPLICATION');
-- Teamspeak Client
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.teamspeak.com/?page=downloads', 'teamspeak.png', 'Teamspeak Client', 'teamspeakclient', false, 'APPLICATION');
-- Teamspeak Server
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.teamspeak.com/?page=downloads', 'teamspeak.png', 'Teamspeak Server', 'teamspeakserver', false, 'APPLICATION');
-- Wireshark
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'https://www.wireshark.org/download.html', 'wireshark.png', 'Wireshark', 'wireshark', false, 'APPLICATION');
-- LibreOffice
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.libreoffice.org/download/libreoffice-fresh/?type=win-x86', 'libreoffice.png', 'LibreOffice', 'libreoffice', false, 'APPLICATION');
-- OpenOffice
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://www.openoffice.org/download/other.html', 'openoffice.png', 'OpenOffice', 'openoffice', false, 'APPLICATION');
-- DAEMON Tools Lite
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/DAEMON-Tools-Lite/3000-2094_4-10778842.html', 'daemontoolslite.png', 'DAEMON Tools Lite', 'daemontoolslite', false, 'APPLICATION');
-- AVG Free Antivirus 2014
INSERT INTO applicationreference(id, creationdate, updatedate, dbversion, active, globalurl, iconfilename, name, apiname, notified, category) VALUES (nextval('application_reference_seq'), now(), null, 0, true, 'http://download.cnet.com/AVG-AntiVirus-Free/3000-2239_4-10320142.html', 'avgfreeantivirus.png', 'AVG Free Antivirus', 'avgfreeantivirus', false, 'APPLICATION');

-- Application versions
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-09 00:00:00.643', '29.0.1', 'https://download.mozilla.org/?product=firefox-29.0.1-SSL&os=win&lang=en-US', 'https://download.mozilla.org/?product=firefox-29.0.1-SSL&os=win&lang=fr', NULL, NULL, (select id from applicationreference where apiname = 'firefox'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-08-17 00:00:00.768', '3.5.11', 'http://www.dotpdn.com/files/Paint.NET.3.5.11.Install.zip', NULL, NULL, NULL, (select id from applicationreference where apiname = 'paintnet'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-06-02 00:00:01.899', '6.6.3', 'http://download.tuxfamily.org/notepadplus/6.6.3/npp.6.6.3.Installer.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'notepadplusplus'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-11-28 00:00:11.082', '2.8.10', 'http://download.gimp.org/pub/gimp/v2.8/windows/gimp-2.8.10-setup.exe', NULL, NULL, NULL, (select id from applicationreference where apiname = 'gimp'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-13 00:00:11.661', '11.0.07', 'http://www.adobe.com/support/downloads/detail.jsp?ftpID=5784', NULL, NULL, NULL, (select id from applicationreference where apiname = 'adobereader'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2013-06-19 00:00:12.349', '2.1.3', 'http://get.videolan.org/vlc/2.1.3/win32/vlc-2.1.3-win32.exe', NULL, 'http://download.videolan.org/pub/videolan/vlc/last/win64/', NULL, (select id from applicationreference where apiname = 'vlcmediaplayer'));
INSERT INTO applicationversion(id, creationdate, updatedate, dbversion, versiondate, versionnumber, win32urlen, win32urlfr, win64urlen, win64urlfr, application_id) VALUES (nextval('application_version_seq'), now(), NULL, 0, '2014-05-01 00:00:12.711', '1.7.5', 'http://sourceforge.net/projects/mpc-hc/files/MPC%20HomeCinema%20-%20Win32/MPC-HC_v1.7.5_x86/MPC-HC.1.7.5.x86.exe/download', NULL, 'http://sourceforge.net/projects/mpc-hc/files/MPC%20HomeCinema%20-%20x64/MPC-HC_v1.7.5_x64/MPC-HC.1.7.5.x64.exe/download', NULL, (select id from applicationreference where apiname = 'mediaplayerclassichc'));
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
INSERT INTO setting(id, creationdate, updatedate, dbversion, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'EMAIL_APP_ADDED', currval('person_seq'));
INSERT INTO setting(id, creationdate, updatedate, dbversion, active, parameter, person_id) VALUES (nextval('setting_seq'), now(), null, 0, true, 'NEWSLETTER', currval('person_seq'));
