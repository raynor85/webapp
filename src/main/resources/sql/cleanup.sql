-- Remove old notifications (we keep the last nb notifications for each user)
CREATE OR REPLACE FUNCTION cleanNotifications(nbNotificationsToKeep INT) RETURNS void AS $$
DECLARE
	p record;
BEGIN
    FOR p IN (SELECT person_id
        FROM applicationnotification
        GROUP BY person_id
        HAVING COUNT(id) > nbNotificationsToKeep)
    LOOP
		DELETE FROM applicationnotification
		WHERE person_id = p.person_id
		AND id NOT IN (
		  SELECT id
		  FROM applicationnotification
		  WHERE person_id = p.person_id
		  ORDER BY creationdate DESC
		  LIMIT nbNotificationsToKeep
		);
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Remove old versions (we keep only the nb latest version for each app)
CREATE OR REPLACE FUNCTION cleanVersions(nbVersionsToKeep INT) RETURNS void AS $$
DECLARE
	a record;
BEGIN
    FOR a IN (SELECT application_id
        FROM applicationversion
        GROUP BY application_id
        HAVING COUNT(id) > nbVersionsToKeep)
    LOOP
		DELETE FROM applicationversion
		WHERE application_id = a.application_id
		AND id NOT IN (
		  SELECT id
		  FROM applicationversion
		  WHERE application_id = a.application_id
		  ORDER BY creationdate DESC
		  LIMIT nbVersionsToKeep
		);
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Optimize database
CREATE OR REPLACE FUNCTION analyzeDatabase() RETURNS boolean AS $$
DECLARE
	tablename varchar;
BEGIN
    FOR tablename IN (SELECT c.relname
		FROM pg_catalog.pg_class c
		LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
		WHERE c.relkind = 'r'
		AND n.nspname <> 'pg_catalog'
		AND n.nspname <> 'information_schema'
		AND n.nspname !~ '^pg_toast'
		AND pg_catalog.pg_table_is_visible(c.oid))
    LOOP
		EXECUTE 'ANALYZE ' || tablename;
    END LOOP;
    RETURN true;
END;
$$ LANGUAGE plpgsql;

-- Global cleanup
CREATE OR REPLACE FUNCTION cleanDatabase() RETURNS boolean AS $$
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
	SELECT cleanNotifications(20);
	
	-- Remove old versions
	SELECT cleanVersions(1);

	SELECT true AS result;
$$ LANGUAGE sql;

