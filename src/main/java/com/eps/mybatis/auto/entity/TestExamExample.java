package com.eps.mybatis.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class TestExamExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitSize;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public TestExamExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
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
     * This method corresponds to the database table e_test_exam
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
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
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
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitSize(Integer limitSize) {
        this.limitSize=limitSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_exam
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitSize() {
        return limitSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table e_test_exam
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

        public Criteria andTestIdIsNull() {
            addCriterion("TEST_ID is null");
            return (Criteria) this;
        }

        public Criteria andTestIdIsNotNull() {
            addCriterion("TEST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTestIdEqualTo(Integer value) {
            addCriterion("TEST_ID =", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotEqualTo(Integer value) {
            addCriterion("TEST_ID <>", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdGreaterThan(Integer value) {
            addCriterion("TEST_ID >", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TEST_ID >=", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdLessThan(Integer value) {
            addCriterion("TEST_ID <", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdLessThanOrEqualTo(Integer value) {
            addCriterion("TEST_ID <=", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdIn(List<Integer> values) {
            addCriterion("TEST_ID in", values, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotIn(List<Integer> values) {
            addCriterion("TEST_ID not in", values, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdBetween(Integer value1, Integer value2) {
            addCriterion("TEST_ID between", value1, value2, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TEST_ID not between", value1, value2, "testId");
            return (Criteria) this;
        }

        public Criteria andExamIdIsNull() {
            addCriterion("exam_id is null");
            return (Criteria) this;
        }

        public Criteria andExamIdIsNotNull() {
            addCriterion("exam_id is not null");
            return (Criteria) this;
        }

        public Criteria andExamIdEqualTo(Integer value) {
            addCriterion("exam_id =", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotEqualTo(Integer value) {
            addCriterion("exam_id <>", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdGreaterThan(Integer value) {
            addCriterion("exam_id >", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("exam_id >=", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLessThan(Integer value) {
            addCriterion("exam_id <", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLessThanOrEqualTo(Integer value) {
            addCriterion("exam_id <=", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdIn(List<Integer> values) {
            addCriterion("exam_id in", values, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotIn(List<Integer> values) {
            addCriterion("exam_id not in", values, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdBetween(Integer value1, Integer value2) {
            addCriterion("exam_id between", value1, value2, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotBetween(Integer value1, Integer value2) {
            addCriterion("exam_id not between", value1, value2, "examId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNull() {
            addCriterion("subject_id is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNotNull() {
            addCriterion("subject_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdEqualTo(Integer value) {
            addCriterion("subject_id =", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotEqualTo(Integer value) {
            addCriterion("subject_id <>", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThan(Integer value) {
            addCriterion("subject_id >", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("subject_id >=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThan(Integer value) {
            addCriterion("subject_id <", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("subject_id <=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIn(List<Integer> values) {
            addCriterion("subject_id in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotIn(List<Integer> values) {
            addCriterion("subject_id not in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdBetween(Integer value1, Integer value2) {
            addCriterion("subject_id between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("subject_id not between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andHisFlagIsNull() {
            addCriterion("HIS_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andHisFlagIsNotNull() {
            addCriterion("HIS_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andHisFlagEqualTo(Integer value) {
            addCriterion("HIS_FLAG =", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagNotEqualTo(Integer value) {
            addCriterion("HIS_FLAG <>", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagGreaterThan(Integer value) {
            addCriterion("HIS_FLAG >", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("HIS_FLAG >=", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagLessThan(Integer value) {
            addCriterion("HIS_FLAG <", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagLessThanOrEqualTo(Integer value) {
            addCriterion("HIS_FLAG <=", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagIn(List<Integer> values) {
            addCriterion("HIS_FLAG in", values, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagNotIn(List<Integer> values) {
            addCriterion("HIS_FLAG not in", values, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagBetween(Integer value1, Integer value2) {
            addCriterion("HIS_FLAG between", value1, value2, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("HIS_FLAG not between", value1, value2, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagIsNull() {
            addCriterion("AUTOMARK_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagIsNotNull() {
            addCriterion("AUTOMARK_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagEqualTo(Integer value) {
            addCriterion("AUTOMARK_FLAG =", value, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagNotEqualTo(Integer value) {
            addCriterion("AUTOMARK_FLAG <>", value, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagGreaterThan(Integer value) {
            addCriterion("AUTOMARK_FLAG >", value, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("AUTOMARK_FLAG >=", value, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagLessThan(Integer value) {
            addCriterion("AUTOMARK_FLAG <", value, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagLessThanOrEqualTo(Integer value) {
            addCriterion("AUTOMARK_FLAG <=", value, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagIn(List<Integer> values) {
            addCriterion("AUTOMARK_FLAG in", values, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagNotIn(List<Integer> values) {
            addCriterion("AUTOMARK_FLAG not in", values, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagBetween(Integer value1, Integer value2) {
            addCriterion("AUTOMARK_FLAG between", value1, value2, "automarkFlag");
            return (Criteria) this;
        }

        public Criteria andAutomarkFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("AUTOMARK_FLAG not between", value1, value2, "automarkFlag");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table e_test_exam
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
     * This class corresponds to the database table e_test_exam
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