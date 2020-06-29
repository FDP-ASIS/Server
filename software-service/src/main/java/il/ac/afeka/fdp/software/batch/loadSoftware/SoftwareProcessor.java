package il.ac.afeka.fdp.software.batch.loadSoftware;

import il.ac.afeka.fdp.software.dao.SoftwareCrud;
import il.ac.afeka.fdp.software.data.Software;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class SoftwareProcessor implements ItemProcessor<Map.Entry<String, String[]>, Software[]> {

    @Autowired
    private SoftwareCrud softwareCrud;

    @Override
    public Software[] process(Map.Entry<String, String[]> item) throws Exception {
        final String name = item.getKey();
        return Arrays.stream(item.getValue())
                .map(version -> new Software(name, version))
                .filter(software -> !softwareCrud.existsByNameAndVersion(software.getName(),software.getVersion()))
                .toArray(Software[]::new);
    }
}
