package com.bizmobiz.bizmoweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bizmobiz.bizmoweb.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.phoneNumber = :phone_number AND u.password = :password")
	public User findByPhoneNumberAndPassword(@Param("phone_number") String phone_number, @Param("password") String password);
	
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
	public User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
	
	@Query("SELECT u FROM User u WHERE u.phoneNumber = :phone_number")
	public List<User> findByPhoneNumber(@Param("phone_number") String phone_number);
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User findByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.facebookUid = :facebookUid")
	public User findByFacebookUid(@Param("facebookUid") String facebookUid);
	
	@Query("SELECT u FROM User u WHERE u.googleUid = :googleUid")
	public User findByGoogleUid(@Param("googleUid") String googleUid);
}
