package il.ac.afeka.fdp.software.config;

import il.ac.afeka.fdp.software.data.RepoData;
import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.exceptions.BadReqException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@ConfigurationProperties(prefix = "repo")
@Data
public class RepoReq {

    private String baseUrl;
    private String downloadUrl;
    private String scriptWithExtension;

    @Autowired
    private RestTemplate restTemplate;

    public String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new BadReqException(e.getMessage());
        }
    }

    public RepoData[] getData(String url) {
        try {
            return restTemplate.getForEntity(new URI(url), RepoData[].class).getBody();
        } catch (URISyntaxException e) {
            throw new BadReqException(e.getMessage());
        }
    }

    public String getScript(String name, String version, ScriptType type) {
        return this.getDownloadUrl() + "/" + this.encodeValue(name) + "/" + this.encodeValue(version) + "/"
                + type.getAction() + "/" + this.scriptWithExtension;
    }

}
