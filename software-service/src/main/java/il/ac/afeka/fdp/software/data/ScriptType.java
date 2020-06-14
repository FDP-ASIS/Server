package il.ac.afeka.fdp.software.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScriptType {
    INSTALLATION("installation"),
    DELETION("deletion");

    private final String action;
}
