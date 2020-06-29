package il.ac.afeka.fdp.software.batch.loadSoftware;

import il.ac.afeka.fdp.software.dao.SoftwareCrud;
import il.ac.afeka.fdp.software.data.Software;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SoftwareWriter implements ItemWriter<Software[]> {

    @Autowired
    private SoftwareCrud softwareCrud;

    @Override
    public void write(List<? extends Software[]> items) throws Exception {
        items.forEach(softwareArr -> softwareCrud.saveAll(Arrays.stream(softwareArr).collect(Collectors.toList())));
    }
}
