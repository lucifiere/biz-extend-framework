package com.lucifiere.biz.model.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 排除时间
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月15日
 */
public class ExcludeTimeCondition implements CommonCondition {

    /**
     * 排除日期
     */
    private List<DateCondition> excludeDates;

    public ExcludeTimeCondition(){
        excludeDates = new ArrayList<>();
    }

    public List<DateCondition> getExcludeDates() {
        if(excludeDates == null){
            excludeDates = new ArrayList<>();
        }
        return excludeDates;
    }

    public void setExcludeDates(List<DateCondition> excludeDates) {
        this.excludeDates = excludeDates;
    }
}
