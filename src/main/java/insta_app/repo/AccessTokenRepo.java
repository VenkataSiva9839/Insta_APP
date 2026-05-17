package insta_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import insta_app.entity.AccessToken;

public interface AccessTokenRepo extends JpaRepository<AccessToken, Integer>{

	boolean existsByTokenAndIsBlocked(String at, boolean b);

}
