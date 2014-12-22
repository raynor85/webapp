-- Remove emails already sent
DELETE FROM emaildeletedapplication
WHERE sent = true;
DELETE FROM emailnewsletter
WHERE sent = true;
DELETE FROM emailsingleupdate
WHERE sent = true;
DELETE FROM emailweeklyupdate_applicationversion
WHERE emailweeklyupdate_id in (
  SELECT id FROM emailweeklyupdate
  WHERE sent = true
);
DELETE FROM emailweeklyupdate
WHERE sent = true;
DELETE FROM emailaddedapplication_applicationreference
WHERE emailaddedapplication_id in (
  SELECT id 
  FROM emailaddedapplication
  WHERE sent = true
);
DELETE FROM emailaddedapplication
WHERE sent = true;

-- Remove old notifications
SELECT cleanNotifications(50);

-- Remove old versions
SELECT cleanVersions(1);
