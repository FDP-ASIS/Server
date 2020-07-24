package il.ac.afeka.fdp.software.infra;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCursor;
import il.ac.afeka.fdp.software.config.RepoReq;
import il.ac.afeka.fdp.software.dao.SoftwareCrud;
import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.data.Software;
import il.ac.afeka.fdp.software.exceptions.SoftwareNotFoundException;
import il.ac.afeka.fdp.software.logger.Logger;
import il.ac.afeka.fdp.software.logger.SoftwarePerformance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Software Service
 */
@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    private RepoReq repoReq;

    @Autowired
    private SoftwareCrud softwareCrud;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * @return all software in the system
     */
    @Override
    @Logger
    @SoftwarePerformance
    public List<String> getAllSoftware() {
        DistinctIterable<String> iterable = mongoTemplate
                .getCollection("SOFTWARE")
                .distinct("name", String.class);
        MongoCursor<String> cursor = iterable.iterator();
        List<String> list = new ArrayList<>();
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        list.sort(String::compareTo);
        return list;
    }

    /**
     * @param name software name
     * @return all versions of the software
     */
    @Override
    @Logger
    @SoftwarePerformance
    public List<Software> getSoftwareVersions(String name) {
        try {
            return this.softwareCrud.findByNameIgnoreCase(name).stream()
                    .sorted((software1, software2) -> software2.getVersion().compareTo(software1.getVersion()))
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            throw new SoftwareNotFoundException();
        }
    }

    /**
     * @param name software name
     * @param version software version
     * @param scriptType Installation/Deletion
     * @return download link of the script
     */
    @Override
    @Logger
    @SoftwarePerformance
    public String getScriptURL(String name, String version, ScriptType scriptType) {
        return this.repoReq.getScript(name.toLowerCase(), version, scriptType);
    }

    /**
     * @param id software id
     * @return the software details by id
     */
    @Override
    @Logger
    @SoftwarePerformance
    public Software getSoftware(String id) {
        return this.softwareCrud.findById(id).orElse(null);
    }
}
