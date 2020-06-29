package il.ac.afeka.fdp.software.dao;

import il.ac.afeka.fdp.software.data.Software;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareCrud extends MongoRepository<Software, String> {
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Software> findByNameStartingWith(@Param("name") String name, PageRequest of);
    boolean existsByNameAndVersion(@Param("name") String name, @Param("version") String version);
}
