package insta_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import insta_app.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

	boolean existsByTokenAndIsBlocked(String rt, boolean b);

}
