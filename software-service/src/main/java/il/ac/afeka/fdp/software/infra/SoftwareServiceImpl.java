package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.dao.SoftwareCrud;
import il.ac.afeka.fdp.software.exceptions.SoftwareAlreadyExistsException;
import il.ac.afeka.fdp.software.data.Software;
import il.ac.afeka.fdp.software.exceptions.SoftwareNotFoundException;
import il.ac.afeka.fdp.software.utils.FinalStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    private SoftwareCrud softwareCrud;

    @Autowired
    public SoftwareServiceImpl(SoftwareCrud softwareCrud) {
        super();
        this.softwareCrud = softwareCrud;
    }

    @Override
    public Software create(Software software) {
        String name = software.getSoftwareName();
        if (this.softwareCrud.existsSoftwareBySoftwareName(name)) {
            throw new SoftwareAlreadyExistsException(FinalStrings.SOFTWARE_EXISTS + name);
        }
        return this.softwareCrud.save(software);
    }

    @Override
    public Software getSoftwareByName(String softwareName) {
        Software softwareToReturn = this.softwareCrud.findBySoftwareName(softwareName);
        if (softwareToReturn == null) {
            throw new SoftwareNotFoundException(FinalStrings.NO_SOFTWARE_FOUND + softwareName);
        }
        return softwareToReturn;
    }

    @Override
    public List<Software> getAllSoftware(int page, int size, String sort) {
        return this.softwareCrud.findAll(PageRequest.of(page, size, Sort.Direction.ASC, sort)).getContent();
    }

    @Override
    public List<Software> getSoftwareBySoftwareName(String softwareName, int page, int size, String sort) {
        return this.softwareCrud.findAllBySoftwareName(softwareName, PageRequest.of(page, size, Sort.Direction.ASC, sort));
    }

    @Override
    public List<Software> getSoftwareByVersion(String version, int page, int size, String sort) {
        return this.softwareCrud.findAllByVersion(version, PageRequest.of(page, size, Sort.Direction.ASC, sort));
    }

    @Override
    public void editSoftware(String softwareName, Software software) {
        Software softwareToEdit = this.softwareCrud.findBySoftwareName(softwareName);
        if (softwareToEdit == null) {
            throw new SoftwareNotFoundException(FinalStrings.NO_SOFTWARE_FOUND + softwareName);
        }
        softwareToEdit.setSoftwareName(software.getSoftwareName());
        softwareToEdit.setVersion(software.getVersion());
    }

    @Override
    public void deleteSoftwareByName(String softwareName) {
        Software softwareToDelete = this.softwareCrud.findBySoftwareName(softwareName);
        if (softwareToDelete == null) {
            throw new SoftwareNotFoundException(FinalStrings.NO_SOFTWARE_FOUND + softwareName);
        }

        this.softwareCrud.delete(softwareToDelete);
    }

    @Override
    public void deleteAll() {
        this.softwareCrud.deleteAll();
    }
}
