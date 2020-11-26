package system.backend.others;

import system.backend.validators.indicators.ValidationIndicator;

import javax.persistence.criteria.CriteriaBuilder;

public class Indicator {
    private static Indicator indicator;
    private ValidationIndicator validationIndicator;

    Indicator(){

    }

    public static Indicator getInstance(){
        if(indicator == null)
            indicator = new Indicator();
        return indicator;
    }

    public ValidationIndicator getValidationIndicator() {
        return validationIndicator;
    }

    public void setValidationIndicator(ValidationIndicator validationIndicator) {
        this.validationIndicator = validationIndicator;
    }
}
