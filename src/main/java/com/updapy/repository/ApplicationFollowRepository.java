package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.updapy.model.ApplicationFollow;
import com.updapy.model.User;
import com.updapy.model.stats.FollowedApplication;
import com.updapy.model.stats.Rating;

public interface ApplicationFollowRepository extends JpaRepository<ApplicationFollow, Long> {

	@Query("select new com.updapy.model.stats.FollowedApplication(ar.name, count(*)) from ApplicationFollow af, ApplicationReference ar where af.application = ar group by ar.name order by count(*) desc")
	List<FollowedApplication> getTopFollowedApplications(Pageable pageable);

	ApplicationFollow findByUserAndApplicationApiName(User user, String apiName);

	@Query("select new com.updapy.model.stats.Rating(avg(rating), count(*)) from ApplicationFollow where application.apiName = :apiName and rating is not null")
	Rating findAverageRatingByApplication(@Param("apiName") String apiName);

}
