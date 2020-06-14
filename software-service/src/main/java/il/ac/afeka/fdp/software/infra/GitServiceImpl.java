package il.ac.afeka.fdp.software.infra;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GitServiceImpl{
//        implements GitService {
//    private SoftwareCrud softwareCrud;
//
//    @Autowired
//    public GitServiceImpl(SoftwareCrud softwareCrud) {
//        super();
//        this.softwareCrud = softwareCrud;
//    }
//
//    @Override
//    public List<String> getAllNames() throws JSONException, URISyntaxException {
//        String baseUrl = "https://api.github.com/repos/FDP-ASIS/Repository/contents/scripts";
//        JSONArray data = new JSONArray(doingSome(baseUrl));
//        List<String> objects = new ArrayList<String>();
//        for(int i=0; i<data.length(); i++)
//        {
//            JSONObject obj=data.getJSONObject(i);
//            objects.add(obj.getString("name"));
//        }
//        return objects;
//    }
//
//    @Override
//    public List<String> getAllVersions(String softwareName) throws JSONException, URISyntaxException {
//        String baseUrl = "https://api.github.com/repos/FDP-ASIS/Repository/contents/scripts";
//        JSONArray data = new JSONArray(doingSome(baseUrl));
//        List<String> objects = new ArrayList<String>();
//        for(int i=0; i<data.length(); i++) {
//            if (data.getJSONObject(i).getString("name").equals(softwareName)){
//                JSONObject obj = data.getJSONObject(i);
//                if (obj.getString("type").equals("dir")) {
//                    JSONArray data2 = new JSONArray(doingSome(obj.getString("url")));
//                    for (int k = 0; k < data2.length(); k++) {
//                        JSONObject obj4 = data2.getJSONObject(k);
//                        objects.add(obj4.getString("name"));
//                    }
//                }
//            }
//        }
//        return objects;
//    }
//
//    @Override
//    public List<String> getVersionLinkInstall(String softwareName, String version) throws JSONException, URISyntaxException {
//        String baseUrl = "https://api.github.com/repos/FDP-ASIS/Repository/contents/scripts";
//        JSONArray data = new JSONArray(doingSome(baseUrl));
//        List<String> objects = new ArrayList<String>();
//        for(int i=0; i<data.length(); i++) {
//            if (data.getJSONObject(i).getString("name").equals(softwareName)){
//                JSONObject obj = data.getJSONObject(i);
//                if (obj.getString("type").equals("dir")) {
//                    JSONArray data2 = new JSONArray(doingSome(obj.getString("url")));
//                    for (int k = 0; k < data2.length(); k++) {
//                        JSONObject obj4 = data2.getJSONObject(k);
//                        if (obj4.getString("name").equals(version)) {
//                            JSONArray data4 = new JSONArray(doingSome(obj4.getString("url")));
//                            for (int l = 0; l < data4.length(); l++) {
//                                JSONObject obj5 = data4.getJSONObject(l);
//                                if (obj5.getString("name").equals("installation")) {
//                                    JSONArray data6 = new JSONArray(doingSome(obj5.getString("url")));
//                                    for (int m = 0; m < data6.length(); m++) {
//                                        JSONObject obj6 = data6.getJSONObject(m);
//                                        objects.add(obj6.getString("download_url"));
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return objects;
//    }
//
//    @Override
//    public List<String> getVersionLinkDelete(String softwareName, String version) throws JSONException, URISyntaxException {
//        String baseUrl = "https://api.github.com/repos/FDP-ASIS/Repository/contents/scripts";
//        JSONArray data = new JSONArray(doingSome(baseUrl));
//        List<String> objects = new ArrayList<String>();
//        for(int i=0; i<data.length(); i++) {
//            if (data.getJSONObject(i).getString("name").equals(softwareName)){
//                JSONObject obj = data.getJSONObject(i);
//                if (obj.getString("type").equals("dir")) {
//                    JSONArray data2 = new JSONArray(doingSome(obj.getString("url")));
//                    for (int k = 0; k < data2.length(); k++) {
//                        JSONObject obj4 = data2.getJSONObject(k);
//                        if (obj4.getString("name").equals(version)) {
//                            JSONArray data4 = new JSONArray(doingSome(obj4.getString("url")));
//                            for (int l = 0; l < data4.length(); l++) {
//                                JSONObject obj5 = data4.getJSONObject(l);
//                                if (obj5.getString("name").equals("deletion")) {
//                                    JSONArray data6 = new JSONArray(doingSome(obj5.getString("url")));
//                                    for (int m = 0; m < data6.length(); m++) {
//                                        JSONObject obj6 = data6.getJSONObject(m);
//                                        objects.add(obj6.getString("download_url"));
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return objects;
//    }
//
//    public String doingSome(String baseUrl) throws URISyntaxException {
//        RestTemplate restTemplate = new RestTemplate();
//        URI uri = new URI(baseUrl);
//        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
//        String responseBody = response.getBody();
//        return responseBody;
//    }
}
