package il.ac.afeka.fdp.course.infra;

import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DepartmentService {
    DepartmentEntity getDepartmentByCode(int code);
    List<DepartmentEntity> create(List<DepartmentEntity> departments);
    List<DepartmentEntity> getDepartmentsByCode(int code, int page, int size, Sort.Direction direction, String sort);
    List<DepartmentEntity> getDepartmentsByName(String name, int page, int size, Sort.Direction direction, String sort);
    List<DepartmentEntity> getAllDepartments(int page, int size, Sort.Direction direction, String sort);
    void editDepartment(int departmentCode, DepartmentEntity department);
    void deleteDepartmentByCode(int departmentCode);
    void deleteAll();
}
