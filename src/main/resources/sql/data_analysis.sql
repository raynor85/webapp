-- What is the most followed app?
select ar.name, count(*)
from applicationfollow af, applicationreference ar
where af.application_id = ar.id  
group by ar.name
order by count(*) desc;

-- Who is following the most?
select p.email, count(*)
from applicationfollow af, person p 
where af.person_id = p.id
group by p.email
order by count(*) desc;

-- Who is reading the notifications?
select p.email, count(*)
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
