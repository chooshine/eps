package com.eps.mybatis.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SeatExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitSize;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public SeatExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitSize(Integer limitSize) {
        this.limitSize=limitSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitSize() {
        return limitSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andSeatIdIsNull() {
            addCriterion("SEAT_ID is null");
            return (Criteria) this;
        }

        public Criteria andSeatIdIsNotNull() {
            addCriterion("SEAT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSeatIdEqualTo(Integer value) {
            addCriterion("SEAT_ID =", value, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdNotEqualTo(Integer value) {
            addCriterion("SEAT_ID <>", value, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdGreaterThan(Integer value) {
            addCriterion("SEAT_ID >", value, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SEAT_ID >=", value, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdLessThan(Integer value) {
            addCriterion("SEAT_ID <", value, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdLessThanOrEqualTo(Integer value) {
            addCriterion("SEAT_ID <=", value, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdIn(List<Integer> values) {
            addCriterion("SEAT_ID in", values, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdNotIn(List<Integer> values) {
            addCriterion("SEAT_ID not in", values, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdBetween(Integer value1, Integer value2) {
            addCriterion("SEAT_ID between", value1, value2, "seatId");
            return (Criteria) this;
        }

        public Criteria andSeatIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SEAT_ID not between", value1, value2, "seatId");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNull() {
            addCriterion("CLASS_ID is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("CLASS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(Integer value) {
            addCriterion("CLASS_ID =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("CLASS_ID <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("CLASS_ID >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("CLASS_ID >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("CLASS_ID <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("CLASS_ID <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<Integer> values) {
            addCriterion("CLASS_ID in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<Integer> values) {
            addCriterion("CLASS_ID not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(Integer value1, Integer value2) {
            addCriterion("CLASS_ID between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("CLASS_ID not between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNull() {
            addCriterion("STUDENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNotNull() {
            addCriterion("STUDENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStudentIdEqualTo(Integer value) {
            addCriterion("STUDENT_ID =", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotEqualTo(Integer value) {
            addCriterion("STUDENT_ID <>", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThan(Integer value) {
            addCriterion("STUDENT_ID >", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("STUDENT_ID >=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThan(Integer value) {
            addCriterion("STUDENT_ID <", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThanOrEqualTo(Integer value) {
            addCriterion("STUDENT_ID <=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIn(List<Integer> values) {
            addCriterion("STUDENT_ID in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotIn(List<Integer> values) {
            addCriterion("STUDENT_ID not in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdBetween(Integer value1, Integer value2) {
            addCriterion("STUDENT_ID between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("STUDENT_ID not between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andSeatNameIsNull() {
            addCriterion("SEAT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSeatNameIsNotNull() {
            addCriterion("SEAT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSeatNameEqualTo(String value) {
            addCriterion("SEAT_NAME =", value, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameNotEqualTo(String value) {
            addCriterion("SEAT_NAME <>", value, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameGreaterThan(String value) {
            addCriterion("SEAT_NAME >", value, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameGreaterThanOrEqualTo(String value) {
            addCriterion("SEAT_NAME >=", value, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameLessThan(String value) {
            addCriterion("SEAT_NAME <", value, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameLessThanOrEqualTo(String value) {
            addCriterion("SEAT_NAME <=", value, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameLike(String value) {
            addCriterion("SEAT_NAME like", value, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameNotLike(String value) {
            addCriterion("SEAT_NAME not like", value, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameIn(List<String> values) {
            addCriterion("SEAT_NAME in", values, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameNotIn(List<String> values) {
            addCriterion("SEAT_NAME not in", values, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameBetween(String value1, String value2) {
            addCriterion("SEAT_NAME between", value1, value2, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNameNotBetween(String value1, String value2) {
            addCriterion("SEAT_NAME not between", value1, value2, "seatName");
            return (Criteria) this;
        }

        public Criteria andSeatNoIsNull() {
            addCriterion("SEAT_NO is null");
            return (Criteria) this;
        }

        public Criteria andSeatNoIsNotNull() {
            addCriterion("SEAT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSeatNoEqualTo(String value) {
            addCriterion("SEAT_NO =", value, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoNotEqualTo(String value) {
            addCriterion("SEAT_NO <>", value, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoGreaterThan(String value) {
            addCriterion("SEAT_NO >", value, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoGreaterThanOrEqualTo(String value) {
            addCriterion("SEAT_NO >=", value, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoLessThan(String value) {
            addCriterion("SEAT_NO <", value, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoLessThanOrEqualTo(String value) {
            addCriterion("SEAT_NO <=", value, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoLike(String value) {
            addCriterion("SEAT_NO like", value, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoNotLike(String value) {
            addCriterion("SEAT_NO not like", value, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoIn(List<String> values) {
            addCriterion("SEAT_NO in", values, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoNotIn(List<String> values) {
            addCriterion("SEAT_NO not in", values, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoBetween(String value1, String value2) {
            addCriterion("SEAT_NO between", value1, value2, "seatNo");
            return (Criteria) this;
        }

        public Criteria andSeatNoNotBetween(String value1, String value2) {
            addCriterion("SEAT_NO not between", value1, value2, "seatNo");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("START_DATE is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("START_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("START_DATE =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("START_DATE <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("START_DATE >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("START_DATE >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("START_DATE <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("START_DATE <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("START_DATE in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("START_DATE not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("START_DATE between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("START_DATE not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("END_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("END_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("END_DATE =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("END_DATE <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("END_DATE >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("END_DATE >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("END_DATE <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("END_DATE <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("END_DATE in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("END_DATE not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("END_DATE between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("END_DATE not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andExistFlagIsNull() {
            addCriterion("EXIST_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andExistFlagIsNotNull() {
            addCriterion("EXIST_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andExistFlagEqualTo(Integer value) {
            addCriterion("EXIST_FLAG =", value, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagNotEqualTo(Integer value) {
            addCriterion("EXIST_FLAG <>", value, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagGreaterThan(Integer value) {
            addCriterion("EXIST_FLAG >", value, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXIST_FLAG >=", value, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagLessThan(Integer value) {
            addCriterion("EXIST_FLAG <", value, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagLessThanOrEqualTo(Integer value) {
            addCriterion("EXIST_FLAG <=", value, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagIn(List<Integer> values) {
            addCriterion("EXIST_FLAG in", values, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagNotIn(List<Integer> values) {
            addCriterion("EXIST_FLAG not in", values, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagBetween(Integer value1, Integer value2) {
            addCriterion("EXIST_FLAG between", value1, value2, "existFlag");
            return (Criteria) this;
        }

        public Criteria andExistFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("EXIST_FLAG not between", value1, value2, "existFlag");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table c_seat
     *
     * @mbggenerated do_not_delete_during_merge Tue May 26 14:51:53 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table c_seat
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}