package cn.kgc.coolrental.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElementUiTree {
    private Integer id;
    private String label;
    private Boolean disable = true;
    private List<ElementUiTree> children;
}
