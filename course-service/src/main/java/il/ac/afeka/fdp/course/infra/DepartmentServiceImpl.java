package il.ac.afeka.fdp.course.infra;

import il.ac.afeka.fdp.course.dao.DepartmentCrud;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import il.ac.afeka.fdp.course.exceptions.department.DepartmentAlreadyExistsException;
import il.ac.afeka.fdp.course.exceptions.department.DepartmentNotFoundException;
import il.ac.afeka.fdp.course.exceptions.root.BadReqException;
import il.ac.afeka.fdp.course.utils.FinalStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentCrud departmentCrud;

    @Override
    public List<DepartmentEntity> create(List<DepartmentEntity> departments) {
        if (departments.stream().anyMatch(entity -> entity.getName() == null || entity.getName().isEmpty()))
            throw new BadReqException(FinalStrings.EMPTY_FIELD);
        if (departments.stream().anyMatch(entity -> this.departmentCrud.existsById(entity.getCode()))) {
            throw new DepartmentAlreadyExistsException();
        }
        return this.departmentCrud.saveAll(departments.stream()
        .peek(departmentEntity -> departmentEntity.setCreatedDate(new Date()))
                .collect(Collectors.toList()));
    }

    @Override
    public DepartmentEntity getDepartmentByCode(int code) {
        return this.departmentCrud.findById(code).orElseThrow(() -> new DepartmentNotFoundException(code));
    }

    @Override
    public List<DepartmentEntity> getAllDepartments(int page, int size, Sort.Direction direction, String sort) {
        return this.departmentCrud.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    @Override
    public void editDepartment(int code, DepartmentEntity department) {
        DepartmentEntity departmentEntity = this.departmentCrud.findById(code).orElseThrow(() -> new DepartmentNotFoundException(code));
        if (departmentEntity.getCode() == null)
            department.setCode(code);
        if (departmentEntity.getName() == null)
            department.setName(departmentEntity.getName());

        this.departmentCrud.save(department);
        if (code != departmentEntity.getCode())
            this.departmentCrud.deleteById(code);
    }

    @Override
    public void deleteDepartmentByCode(int code) {
        if (!this.departmentCrud.existsById(code)) {
            throw new DepartmentNotFoundException(code);
        }
        this.departmentCrud.deleteById(code);
    }

    @Override
    public void deleteAll() {
        this.departmentCrud.deleteAll();
    }

    @Override
    public List<DepartmentEntity> getDepartmentsByName(String name, int page, int size, Sort.Direction direction, String sort) {
        return this.departmentCrud.findByNameRegex(".*"+name+".*", PageRequest.of(page, size, direction, sort));
    }
}