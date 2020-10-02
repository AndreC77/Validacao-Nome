package br.com.validacaonome.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlackListRepository extends JpaRepository<BlackListEntity,  Long>{
	
	@Query("SELECT COUNT(*) FROM names WHERE name = :name")
	Long conferirBlackList(@Param("name")String name);
	
}
