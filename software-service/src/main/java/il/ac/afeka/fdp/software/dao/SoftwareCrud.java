package il.ac.afeka.fdp.software.dao;

import il.ac.afeka.fdp.software.data.Software;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface SoftwareCrud extends MongoRepository<Software, String> {
    //    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
//    List<Software> findByNameIgnoreCase(@Param("name") String name, PageRequest of);

    boolean existsByNameAndVersion(@Param("name") String name, @Param("version") String version);

    List<Software> findByNameIgnoreCase(@Param("name") String name);
}
