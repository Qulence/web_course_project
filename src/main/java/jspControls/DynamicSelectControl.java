package jspControls;

import lombok.Data;

import java.util.List;

@Data
public class DynamicSelectControl {
    private String id;
    private String name;
    private String labelText;
    private List<DynamicOptionControl> dynamicOptionControls;
}
