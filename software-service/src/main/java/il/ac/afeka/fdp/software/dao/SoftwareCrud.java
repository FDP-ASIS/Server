package il.ac.afeka.fdp.software.dao;

import il.ac.afeka.fdp.software.data.Software;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SoftwareCrud extends MongoRepository<Software, String> {
    boolean existsSoftwareBySoftwareName(String softwareName);
    Software findBySoftwareName(@Param("softwareName") String softwareName);
    List<Software> findAllBySoftwareName(@Param("softwareName") String softwareName, Pageable pageable);
    List<Software> findAllByVersion(@Param("software") String version, Pageable pageable);
}
