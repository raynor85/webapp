package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.updapy.model.ApplicationReference;
import com.updapy.model.User;
import com.updapy.model.enumeration.SocialMediaService;
import com.updapy.model.stats.Follower;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByApiKey(String key);

	User findByRssKey(String key);

	User findByAccountKey(String key);

	User findBySocialKey(String key);

	List<User> findByActiveTrue();

	List<User> findByFollowedApplicationsApplication(ApplicationReference application);

	@Query("select new com.updapy.model.stats.Follower(p.name, p.email, count(af.id)) from person p, ApplicationFollow af where p.id = af.user.id group by p.email, p.name order by count(af.id) desc")
	List<Follower> getTopFollowers(Pageable pageable);

	Long countByActiveFalse();

	Long countByActiveTrueAndSocialMediaService(SocialMediaService socialMediaService);

	List<User> findByOrderByCreationDateDesc(Pageable pageable);
}
