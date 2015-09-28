package com.eps.mybatis.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class CatalogExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitSize;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public CatalogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
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
     * This method corresponds to the database table e_catalog
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
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
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
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitSize(Integer limitSize) {
        this.limitSize=limitSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_catalog
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitSize() {
        return limitSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table e_catalog
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

        public Criteria andCaIdIsNull() {
            addCriterion("CA_ID is null");
            return (Criteria) this;
        }

        public Criteria andCaIdIsNotNull() {
            addCriterion("CA_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCaIdEqualTo(Integer value) {
            addCriterion("CA_ID =", value, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdNotEqualTo(Integer value) {
            addCriterion("CA_ID <>", value, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdGreaterThan(Integer value) {
            addCriterion("CA_ID >", value, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("CA_ID >=", value, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdLessThan(Integer value) {
            addCriterion("CA_ID <", value, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdLessThanOrEqualTo(Integer value) {
            addCriterion("CA_ID <=", value, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdIn(List<Integer> values) {
            addCriterion("CA_ID in", values, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdNotIn(List<Integer> values) {
            addCriterion("CA_ID not in", values, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdBetween(Integer value1, Integer value2) {
            addCriterion("CA_ID between", value1, value2, "caId");
            return (Criteria) this;
        }

        public Criteria andCaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("CA_ID not between", value1, value2, "caId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNull() {
            addCriterion("SUBJECT_ID is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNotNull() {
            addCriterion("SUBJECT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdEqualTo(Integer value) {
            addCriterion("SUBJECT_ID =", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotEqualTo(Integer value) {
            addCriterion("SUBJECT_ID <>", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThan(Integer value) {
            addCriterion("SUBJECT_ID >", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SUBJECT_ID >=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThan(Integer value) {
            addCriterion("SUBJECT_ID <", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("SUBJECT_ID <=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIn(List<Integer> values) {
            addCriterion("SUBJECT_ID in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotIn(List<Integer> values) {
            addCriterion("SUBJECT_ID not in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdBetween(Integer value1, Integer value2) {
            addCriterion("SUBJECT_ID between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SUBJECT_ID not between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andCaNameIsNull() {
            addCriterion("CA_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCaNameIsNotNull() {
            addCriterion("CA_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCaNameEqualTo(String value) {
            addCriterion("CA_NAME =", value, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameNotEqualTo(String value) {
            addCriterion("CA_NAME <>", value, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameGreaterThan(String value) {
            addCriterion("CA_NAME >", value, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameGreaterThanOrEqualTo(String value) {
            addCriterion("CA_NAME >=", value, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameLessThan(String value) {
            addCriterion("CA_NAME <", value, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameLessThanOrEqualTo(String value) {
            addCriterion("CA_NAME <=", value, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameLike(String value) {
            addCriterion("CA_NAME like", value, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameNotLike(String value) {
            addCriterion("CA_NAME not like", value, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameIn(List<String> values) {
            addCriterion("CA_NAME in", values, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameNotIn(List<String> values) {
            addCriterion("CA_NAME not in", values, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameBetween(String value1, String value2) {
            addCriterion("CA_NAME between", value1, value2, "caName");
            return (Criteria) this;
        }

        public Criteria andCaNameNotBetween(String value1, String value2) {
            addCriterion("CA_NAME not between", value1, value2, "caName");
            return (Criteria) this;
        }

        public Criteria andPCaIdIsNull() {
            addCriterion("P_CA_ID is null");
            return (Criteria) this;
        }

        public Criteria andPCaIdIsNotNull() {
            addCriterion("P_CA_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPCaIdEqualTo(Integer value) {
            addCriterion("P_CA_ID =", value, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdNotEqualTo(Integer value) {
            addCriterion("P_CA_ID <>", value, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdGreaterThan(Integer value) {
            addCriterion("P_CA_ID >", value, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("P_CA_ID >=", value, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdLessThan(Integer value) {
            addCriterion("P_CA_ID <", value, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdLessThanOrEqualTo(Integer value) {
            addCriterion("P_CA_ID <=", value, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdIn(List<Integer> values) {
            addCriterion("P_CA_ID in", values, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdNotIn(List<Integer> values) {
            addCriterion("P_CA_ID not in", values, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdBetween(Integer value1, Integer value2) {
            addCriterion("P_CA_ID between", value1, value2, "pCaId");
            return (Criteria) this;
        }

        public Criteria andPCaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("P_CA_ID not between", value1, value2, "pCaId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNull() {
            addCriterion("FREQUENCY is null");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNotNull() {
            addCriterion("FREQUENCY is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencyEqualTo(Integer value) {
            addCriterion("FREQUENCY =", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotEqualTo(Integer value) {
            addCriterion("FREQUENCY <>", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThan(Integer value) {
            addCriterion("FREQUENCY >", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThanOrEqualTo(Integer value) {
            addCriterion("FREQUENCY >=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThan(Integer value) {
            addCriterion("FREQUENCY <", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThanOrEqualTo(Integer value) {
            addCriterion("FREQUENCY <=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyIn(List<Integer> values) {
            addCriterion("FREQUENCY in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotIn(List<Integer> values) {
            addCriterion("FREQUENCY not in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyBetween(Integer value1, Integer value2) {
            addCriterion("FREQUENCY between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotBetween(Integer value1, Integer value2) {
            addCriterion("FREQUENCY not between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andSortNumIsNull() {
            addCriterion("SORT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andSortNumIsNotNull() {
            addCriterion("SORT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andSortNumEqualTo(String value) {
            addCriterion("SORT_NUM =", value, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumNotEqualTo(String value) {
            addCriterion("SORT_NUM <>", value, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumGreaterThan(String value) {
            addCriterion("SORT_NUM >", value, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumGreaterThanOrEqualTo(String value) {
            addCriterion("SORT_NUM >=", value, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumLessThan(String value) {
            addCriterion("SORT_NUM <", value, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumLessThanOrEqualTo(String value) {
            addCriterion("SORT_NUM <=", value, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumLike(String value) {
            addCriterion("SORT_NUM like", value, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumNotLike(String value) {
            addCriterion("SORT_NUM not like", value, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumIn(List<String> values) {
            addCriterion("SORT_NUM in", values, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumNotIn(List<String> values) {
            addCriterion("SORT_NUM not in", values, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumBetween(String value1, String value2) {
            addCriterion("SORT_NUM between", value1, value2, "sortNum");
            return (Criteria) this;
        }

        public Criteria andSortNumNotBetween(String value1, String value2) {
            addCriterion("SORT_NUM not between", value1, value2, "sortNum");
            return (Criteria) this;
        }

        public Criteria andInUseIsNull() {
            addCriterion("IN_USE is null");
            return (Criteria) this;
        }

        public Criteria andInUseIsNotNull() {
            addCriterion("IN_USE is not null");
            return (Criteria) this;
        }

        public Criteria andInUseEqualTo(Integer value) {
            addCriterion("IN_USE =", value, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseNotEqualTo(Integer value) {
            addCriterion("IN_USE <>", value, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseGreaterThan(Integer value) {
            addCriterion("IN_USE >", value, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseGreaterThanOrEqualTo(Integer value) {
            addCriterion("IN_USE >=", value, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseLessThan(Integer value) {
            addCriterion("IN_USE <", value, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseLessThanOrEqualTo(Integer value) {
            addCriterion("IN_USE <=", value, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseIn(List<Integer> values) {
            addCriterion("IN_USE in", values, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseNotIn(List<Integer> values) {
            addCriterion("IN_USE not in", values, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseBetween(Integer value1, Integer value2) {
            addCriterion("IN_USE between", value1, value2, "inUse");
            return (Criteria) this;
        }

        public Criteria andInUseNotBetween(Integer value1, Integer value2) {
            addCriterion("IN_USE not between", value1, value2, "inUse");
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
     * This class corresponds to the database table e_catalog
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
     * This class corresponds to the database table e_catalog
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