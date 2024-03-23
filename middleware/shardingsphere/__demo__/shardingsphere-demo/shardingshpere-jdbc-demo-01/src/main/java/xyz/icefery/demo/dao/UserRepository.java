package xyz.icefery.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.icefery.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
