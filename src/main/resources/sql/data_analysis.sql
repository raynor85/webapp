-- What is the most followed app?
select ar.name, count(*) as nbfollowers
from applicationfollow af, applicationreference ar
where af.application_id = ar.id  
group by ar.name
order by count(*) desc;

-- Who is following the most?
select p.email, count(af.*) as nbappfollowed
from person p left outer join applicationfollow af on p.id = af.person_id
group by p.email
order by count(af.*) desc;

-- Who is reading the notifications?
select p.email, count(*) as nbunread
from applicationnotification an, person p 
where an.person_id = p.id 
and read = false
group by p.email
order by count(*) asc;

-- What are the latest feedbacks?
select feedback
from accountremoval
where feedback is not null
and feedback != ''
order by removedate desc;  

-- What are the most probable failures?
select application_id, name, globalurl, count, typelasterror
from applicationreference a, retrievalerror e
where a.id = e.application_id 
and count > 2
and application_id not in (98, 99); -- Teamspeak are not working well

-- Local: active only the apps that are failing
update applicationreference
set active = false;
update applicationreference
set active = true
where id in (1, 2, 3); -- Replace with the ids

