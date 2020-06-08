package il.ac.afeka.fdp.course.infra;

import il.ac.afeka.fdp.course.dao.DepartmentCrud;
import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import il.ac.afeka.fdp.course.exceptions.AlreadyExistsException;
import il.ac.afeka.fdp.course.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentCrud departmentCrud;

    @Override
    public List<DepartmentEntity> create(List<DepartmentEntity> departments) {
        if (departments.stream().anyMatch(entity -> this.departmentCrud.existsById(entity.getCode()))) {
            throw new AlreadyExistsException("Department already exists");
        }
        return this.departmentCrud.saveAll(departments);
    }

    @Override
    public DepartmentEntity getDepartmentByCode(int code) {
        return this.departmentCrud.findById(code).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<DepartmentEntity> getAllDepartments(int page, int size, Sort.Direction direction, String sort) {
        return this.departmentCrud.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    @Override
    public void editDepartment(int departmentCode, DepartmentEntity department) {
        DepartmentEntity departmentEntity = this.departmentCrud.findById(departmentCode).orElseThrow(NotFoundException::new);
        if (departmentEntity.getCode() == null)
            department.setCode(departmentCode);
        if (departmentEntity.getName() == null)
            department.setName(departmentEntity.getName());

        this.departmentCrud.save(department);
        if (departmentCode != departmentEntity.getCode())
            this.departmentCrud.deleteById(departmentCode);
    }

    @Override
    public void deleteDepartmentByCode(int departmentCode) {
        if (!this.departmentCrud.existsById(departmentCode)) {
            throw new NotFoundException("Course with courseCode not found:" + departmentCode);
        }
        this.departmentCrud.deleteById(departmentCode);
    }

    @Override
    public void deleteAll() {
        this.departmentCrud.deleteAll();
    }
}