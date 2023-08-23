package ua.foxminded.javaspring.lenskyi.university.controller.form.reader;

import java.util.List;

public class EditUserFormInputReader {

    private List<String> checkboxSelectedValues;

    public List<String> getCheckboxSelectedValues() {
        return checkboxSelectedValues;
    }

    public void setCheckboxSelectedValues(List<String> checkboxSelectedValues) {
        this.checkboxSelectedValues = checkboxSelectedValues;
    }
}