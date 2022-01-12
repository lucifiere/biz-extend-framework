package com.lucifiere.biz.model.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 有效时间条件对象
 *
 * @author 和同
 */
public class EffectiveTimeCondition implements CommonCondition {

    /**
     * 适用日期
     */
    private List<DateCondition> availableDates;

    public EffectiveTimeCondition(){
        availableDates = new ArrayList<>();
    }

    public List<DateCondition> getAvailableDates() {
        if(availableDates==null){
            availableDates = new ArrayList<>();
        }
        return availableDates;
    }

    public void setAvailableDates(List<DateCondition> availableDates) {
        this.availableDates = availableDates;
    }

}
