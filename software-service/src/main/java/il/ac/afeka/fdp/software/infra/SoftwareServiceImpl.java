package il.ac.afeka.fdp.software.infra;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCursor;
import il.ac.afeka.fdp.software.config.RepoReq;
import il.ac.afeka.fdp.software.dao.SoftwareCrud;
import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.data.Software;
import il.ac.afeka.fdp.software.exceptions.SoftwareNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    private RepoReq repoReq;

    @Autowired
    private SoftwareCrud softwareCrud;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
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

    @Override
    public List<Software> getSoftwareVersions(String name) {
        try {
            return this.softwareCrud.findByNameIgnoreCase(name).stream()
//                    .map(Software::getVersion)
//                    .sorted(Comparator.reverseOrder())
                    .sorted((software1, software2) -> software2.getVersion().compareTo(software1.getVersion()))
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            throw new SoftwareNotFoundException();
        }
    }

    @Override
    public String getScriptURL(String name, String version, ScriptType scriptType) {
        return this.repoReq.getScript(name.toLowerCase(), version, scriptType);
    }

    @Override
    public Software getSoftware(String id) {
        return this.softwareCrud.findById(id).orElse(null);
    }
}
