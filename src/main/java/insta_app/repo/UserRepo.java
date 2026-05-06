package insta_app.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import insta_app.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {


	
}
