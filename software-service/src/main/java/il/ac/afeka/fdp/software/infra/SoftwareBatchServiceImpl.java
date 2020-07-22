package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.config.RepoReq;
import il.ac.afeka.fdp.software.data.RepoData;
import il.ac.afeka.fdp.software.logger.Logger;
import il.ac.afeka.fdp.software.logger.SoftwarePerformance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoftwareBatchServiceImpl implements SoftwareBatchService {

    @Autowired
    private RepoReq repoReq;

    @Logger
    @SoftwarePerformance
    public RepoData[] getData(String url) {
        url = (url != null ? url : "");
        return this.repoReq.getData(this.repoReq.getBaseUrl() + "/" + url);
    }

    @Override
    @Logger
    @SoftwarePerformance
    public List<String> getAllSoftware() {
        return Arrays.stream(getData(null)).map(RepoData::getName).collect(Collectors.toList());
    }

    @Override
    @Logger
    @SoftwarePerformance
    public List<String> getSoftwareVersions(String name) {
        return Arrays.stream(getData(this.repoReq.encodeValue(name.toLowerCase()))).map(RepoData::getName).collect(Collectors.toList());
    }
}
