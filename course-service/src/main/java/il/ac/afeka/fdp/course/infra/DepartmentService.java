package il.ac.afeka.fdp.course.infra;

import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DepartmentService {
    List<DepartmentEntity> create(List<DepartmentEntity> departments);
    DepartmentEntity getDepartmentByCode(int code);
    List<DepartmentEntity> getAllDepartments(int page, int size, Sort.Direction direction, String sort);
    void editDepartment(int departmentCode, DepartmentEntity department);
    void deleteDepartmentByCode(int departmentCode);
    void deleteAll();
}
