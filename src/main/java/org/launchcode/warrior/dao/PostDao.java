package org.launchcode.warrior.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.warrior.models.Character;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PostDao extends CrudRepository<Character, Integer> {
    
    List<Character> findAll();
    
    List<Character> findByUid(int uid);
	//List<Character> findByAuthor(int authorId);
    

    
    // TODO - add method signatures as needed
	
}
