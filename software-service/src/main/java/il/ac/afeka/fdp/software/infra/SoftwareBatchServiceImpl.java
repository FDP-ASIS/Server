package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.data.RepoData;
import il.ac.afeka.fdp.software.exceptions.BadReqException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoftwareBatchServiceImpl implements SoftwareBatchService {

    @Value("${repo}")
    private String baseRepoUrl;

    private final String URL_BASE_SUFFIX = "/contents/scripts";

    private final RestTemplate restTemplate = new RestTemplate();

    private String encodeValue(String value) {
        try {
            return  URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new BadReqException(e.getMessage());
        }
    }

    public RepoData[] getUrl(String url) {
        try {
            return restTemplate.getForEntity(new URI(baseRepoUrl + URL_BASE_SUFFIX + "/" + url), RepoData[].class).getBody();
        } catch (URISyntaxException e) {
            throw new BadReqException(e.getMessage());
        }
    }

    @Override
    public List<String> getAllSoftware() {
        return Arrays.stream(getUrl("")).map(RepoData::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> getSoftwareVersions(String name) {
        return Arrays.stream(getUrl(encodeValue(name.toLowerCase()))).map(RepoData::getName).collect(Collectors.toList());
    }
}
