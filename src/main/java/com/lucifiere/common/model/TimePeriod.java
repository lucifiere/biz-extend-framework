package com.lucifiere.common.model;

import java.util.List;

/**
 * 时间对象
 * @author 和同
 */
public class TimePeriod {

    /**
     * 星期几
     */
    private List<String> weeks;

    /**
     * 时刻
     */
    private List<Period> periodTimeList;

    public List<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }

    public List<Period> getPeriodTimeList() {
        return periodTimeList;
    }

    public void setPeriodTimeList(List<Period> periodTimeList) {
        this.periodTimeList = periodTimeList;
    }
}
