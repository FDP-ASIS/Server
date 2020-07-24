package il.ac.afeka.fdp.software.dao;

import il.ac.afeka.fdp.software.data.Software;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SoftwareCrud extends MongoRepository<Software, String> {
    boolean existsByNameAndVersion(@Param("name") String name, @Param("version") String version);
    List<Software> findByNameIgnoreCase(@Param("name") String name);
}
