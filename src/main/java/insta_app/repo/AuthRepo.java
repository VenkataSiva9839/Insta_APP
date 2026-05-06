package insta_app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insta_app.entity.User;

@Repository
public interface AuthRepo extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query("update User u set u.login = true where u.id=:id")
	public void updateLogStatus(@Param("id") Integer id);

}
