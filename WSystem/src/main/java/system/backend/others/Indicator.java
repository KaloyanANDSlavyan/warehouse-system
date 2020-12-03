package system.backend.others;

import system.backend.validators.indicators.ValidationIndicator;

import javax.persistence.criteria.CriteriaBuilder;

public class Indicator {
    private static Indicator indicator;
    private Class<?> validationIndicator;

    public Indicator(){

    }

    public static Indicator getInstance(){
        if(indicator == null)
            indicator = new Indicator();
        return indicator;
    }

    public Class<?> getValidationIndicator() {
        return validationIndicator;
    }

    public void setValidationIndicator(Class<?> validationIndicator) {
        this.validationIndicator = validationIndicator;
    }
}
