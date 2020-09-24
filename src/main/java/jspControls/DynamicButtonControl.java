package jspControls;

import lombok.Data;

@Data
public class DynamicButtonControl implements Cloneable {
    private String name;
    private String value;
    private ButtonType buttonType;
    private String text;

    @Override
    public Object clone() {
        DynamicButtonControl dynamicButtonControl = new DynamicButtonControl();
        dynamicButtonControl.setName(this.name);
        dynamicButtonControl.setValue(this.value);
        dynamicButtonControl.setButtonType(this.buttonType);
        dynamicButtonControl.setText(this.text);
        return dynamicButtonControl;
    }
}