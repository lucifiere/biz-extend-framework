package com.lucifiere.biz.model.condition;

import com.lucifiere.common.model.Period;
import com.lucifiere.common.model.TimePeriod;

import java.io.Serializable;
import java.util.List;

/**
 * 时间范围
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月15日
 */
public class DateCondition implements Serializable {

    private static final long serialVersionUID = -7880261132323999467L;

    /**
     * 日期
     */
    private Period date;

    /**
     * 具体时间设置
     */
    private List<TimePeriod> timePeriodList;

    public DateCondition() {
    }

    public Period getDate() {
        return date;
    }

    public void setDate(Period date) {
        this.date = date;
    }

    public List<TimePeriod> getTimePeriodList() {
        return timePeriodList;
    }

    public void setTimePeriodList(List<TimePeriod> timePeriodList) {
        this.timePeriodList = timePeriodList;
    }
}
