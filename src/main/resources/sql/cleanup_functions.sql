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
