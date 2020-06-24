package il.ac.afeka.fdp.software.batch.loadSoftware;

import org.springframework.batch.item.ItemProcessor;

public class SoftwareProcessor  implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        return null;
    }
}
