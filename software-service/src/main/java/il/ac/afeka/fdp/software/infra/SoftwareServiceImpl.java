package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.data.RepoData;
import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.exceptions.ServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Value("${repo}")
    private String baseRepoUrl;
//    private final RestTemplate restTemplate = new RestTemplate();

    public RepoData[] getUrl(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForEntity(new URI(baseRepoUrl + url), RepoData[].class).getBody();
        } catch (URISyntaxException e) {
            throw new ServerErrorException();
        }
    }

    @Override
    public List<String> getAllSoftware(int page, int size, Sort.Direction direction) {
        Stream<String> stringStream = Arrays.stream(getUrl("/contents/scripts")).map(RepoData::getName);
        if (direction == Sort.Direction.DESC)
            stringStream = stringStream.sorted(Comparator.reverseOrder());
        return stringStream.skip(page * size).limit(size).collect(Collectors.toList());
    }

    @Override
    public List<String> getVersionsOfSoftware(String softwareName) {
        return null;
    }

    @Override
    public List<String> getSoftwareByNameStartingWith(String softwareName) {
        return null;
    }

    @Override
    public String getScriptURL(String softwareName, String version, ScriptType scriptType) {
        return null;
    }
}
