package il.ac.afeka.fdp.course.data.boundary;

import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentBoundary {
    private Integer code;
    private String name;

    public DepartmentBoundary(DepartmentEntity departmentEntity) {
        this.code = departmentEntity.getCode();
        this.name = departmentEntity.getName();
    }

    public DepartmentEntity convertToEntity() {
        return DepartmentEntity.of(code, name);
    }

    public DepartmentEntity convertToEntity(int code) {
        return DepartmentEntity.of(code, name);
    }
}
