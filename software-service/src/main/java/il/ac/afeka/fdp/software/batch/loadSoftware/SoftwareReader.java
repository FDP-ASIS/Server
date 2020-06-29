package il.ac.afeka.fdp.software.batch.loadSoftware;


import il.ac.afeka.fdp.software.infra.SoftwareBatchService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SoftwareReader implements ItemReader<Map.Entry<String, String[]>> {

    @Autowired
    private SoftwareBatchService softwareBatchService;

    private int count;
    private static Map<String, String[]> allSoftwareInfo;

    @Override
    public Map.Entry<String, String[]> read() {
        if (count < allSoftwareInfo.size()) {
            List<Map.Entry<String, String[]>> entries = new ArrayList<>(allSoftwareInfo.entrySet());
            return entries.get(count++);
        } else {
            count = 0;
            this.initialize();
        }
        return null;
    }

    @PostConstruct
    private void initialize() {
        allSoftwareInfo = softwareBatchService.getAllSoftware().stream().collect(Collectors.toMap(Function.identity(),
                s -> softwareBatchService.getSoftwareVersions(s).toArray(new String[0]))
        );

//        //Only for development
//        allSoftwareInfo = new HashMap<String, String[]>() {
//            {
//                put("python", new String[]{"3.7", "3.8"});
//                put("eclipse", new String[]{"3.7", "2020"});
//            }
//        };
    }
}
