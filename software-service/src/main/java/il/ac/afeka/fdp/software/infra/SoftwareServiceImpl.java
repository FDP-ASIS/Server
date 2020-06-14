package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.data.RepoData;
import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.exceptions.BadReqException;
import il.ac.afeka.fdp.software.exceptions.ServerErrorException;
import il.ac.afeka.fdp.software.exceptions.SoftwareNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Value("${repo}")
    private String baseRepoUrl;

    private final String URL_BASE_SUFFIX = "/contents/scripts";

    private final RestTemplate restTemplate = new RestTemplate();

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    public RepoData[] getUrl(String url) {
        try {
            return restTemplate.getForEntity(new URI(baseRepoUrl + URL_BASE_SUFFIX + "/" + url), RepoData[].class).getBody();
        } catch (URISyntaxException e) {
            throw new ServerErrorException();
        }
    }

    @Override
    public List<String> getAllSoftware(int page, int size, Sort.Direction direction) {
        Stream<String> stringStream = Arrays.stream(getUrl("")).map(RepoData::getName);
        if (direction == Sort.Direction.DESC)
            stringStream = stringStream.sorted(Comparator.reverseOrder());
        return stringStream.skip(page * size).limit(size).collect(Collectors.toList());
    }

    @Override
    public List<String> getSoftwareVersions(String name, int page, int size) {
        try {
            return Arrays.stream(getUrl(name.toLowerCase())).map(RepoData::getName).skip(page * size).limit(size).collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            throw new SoftwareNotFoundException();
        }
    }

    @Override
    public String getScriptURL(String name, String version, ScriptType scriptType) {
        try {
            return Arrays.stream(getUrl(name.toLowerCase() + "/" + encodeValue(version) + "/" + scriptType.getAction()))
                    .findFirst().orElseThrow().getDownload_url();
        } catch (HttpClientErrorException | NoSuchElementException e) {
            throw new BadReqException();
        } catch (UnsupportedEncodingException e) {
            throw new ServerErrorException();
        }
    }
}
