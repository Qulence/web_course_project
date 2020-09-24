package jspControls;

import lombok.Data;

@Data
public class DynamicInputControl {
    private String id;
    private String name;
    private String labelText;
    private String placeholderText;
    private String value;
}