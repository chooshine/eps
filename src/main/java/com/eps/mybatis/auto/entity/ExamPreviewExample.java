package com.eps.mybatis.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class ExamPreviewExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitSize;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public ExamPreviewExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
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
     * This method corresponds to the database table e_exam_preview
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
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
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
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitSize(Integer limitSize) {
        this.limitSize=limitSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_exam_preview
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitSize() {
        return limitSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table e_exam_preview
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

        public Criteria andExamIdIsNull() {
            addCriterion("EXAM_ID is null");
            return (Criteria) this;
        }

        public Criteria andExamIdIsNotNull() {
            addCriterion("EXAM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andExamIdEqualTo(Integer value) {
            addCriterion("EXAM_ID =", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotEqualTo(Integer value) {
            addCriterion("EXAM_ID <>", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdGreaterThan(Integer value) {
            addCriterion("EXAM_ID >", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXAM_ID >=", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLessThan(Integer value) {
            addCriterion("EXAM_ID <", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLessThanOrEqualTo(Integer value) {
            addCriterion("EXAM_ID <=", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdIn(List<Integer> values) {
            addCriterion("EXAM_ID in", values, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotIn(List<Integer> values) {
            addCriterion("EXAM_ID not in", values, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_ID between", value1, value2, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_ID not between", value1, value2, "examId");
            return (Criteria) this;
        }

        public Criteria andSealFlagIsNull() {
            addCriterion("SEAL_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andSealFlagIsNotNull() {
            addCriterion("SEAL_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andSealFlagEqualTo(Integer value) {
            addCriterion("SEAL_FLAG =", value, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagNotEqualTo(Integer value) {
            addCriterion("SEAL_FLAG <>", value, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagGreaterThan(Integer value) {
            addCriterion("SEAL_FLAG >", value, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("SEAL_FLAG >=", value, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagLessThan(Integer value) {
            addCriterion("SEAL_FLAG <", value, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagLessThanOrEqualTo(Integer value) {
            addCriterion("SEAL_FLAG <=", value, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagIn(List<Integer> values) {
            addCriterion("SEAL_FLAG in", values, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagNotIn(List<Integer> values) {
            addCriterion("SEAL_FLAG not in", values, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagBetween(Integer value1, Integer value2) {
            addCriterion("SEAL_FLAG between", value1, value2, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andSealFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("SEAL_FLAG not between", value1, value2, "sealFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagIsNull() {
            addCriterion("MARKTAG_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagIsNotNull() {
            addCriterion("MARKTAG_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagEqualTo(Integer value) {
            addCriterion("MARKTAG_FLAG =", value, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagNotEqualTo(Integer value) {
            addCriterion("MARKTAG_FLAG <>", value, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagGreaterThan(Integer value) {
            addCriterion("MARKTAG_FLAG >", value, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("MARKTAG_FLAG >=", value, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagLessThan(Integer value) {
            addCriterion("MARKTAG_FLAG <", value, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagLessThanOrEqualTo(Integer value) {
            addCriterion("MARKTAG_FLAG <=", value, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagIn(List<Integer> values) {
            addCriterion("MARKTAG_FLAG in", values, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagNotIn(List<Integer> values) {
            addCriterion("MARKTAG_FLAG not in", values, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagBetween(Integer value1, Integer value2) {
            addCriterion("MARKTAG_FLAG between", value1, value2, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("MARKTAG_FLAG not between", value1, value2, "marktagFlag");
            return (Criteria) this;
        }

        public Criteria andMarktagContentIsNull() {
            addCriterion("MARKTAG_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andMarktagContentIsNotNull() {
            addCriterion("MARKTAG_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andMarktagContentEqualTo(String value) {
            addCriterion("MARKTAG_CONTENT =", value, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentNotEqualTo(String value) {
            addCriterion("MARKTAG_CONTENT <>", value, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentGreaterThan(String value) {
            addCriterion("MARKTAG_CONTENT >", value, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentGreaterThanOrEqualTo(String value) {
            addCriterion("MARKTAG_CONTENT >=", value, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentLessThan(String value) {
            addCriterion("MARKTAG_CONTENT <", value, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentLessThanOrEqualTo(String value) {
            addCriterion("MARKTAG_CONTENT <=", value, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentLike(String value) {
            addCriterion("MARKTAG_CONTENT like", value, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentNotLike(String value) {
            addCriterion("MARKTAG_CONTENT not like", value, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentIn(List<String> values) {
            addCriterion("MARKTAG_CONTENT in", values, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentNotIn(List<String> values) {
            addCriterion("MARKTAG_CONTENT not in", values, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentBetween(String value1, String value2) {
            addCriterion("MARKTAG_CONTENT between", value1, value2, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andMarktagContentNotBetween(String value1, String value2) {
            addCriterion("MARKTAG_CONTENT not between", value1, value2, "marktagContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagIsNull() {
            addCriterion("EXAMINFO_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagIsNotNull() {
            addCriterion("EXAMINFO_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagEqualTo(Integer value) {
            addCriterion("EXAMINFO_FLAG =", value, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagNotEqualTo(Integer value) {
            addCriterion("EXAMINFO_FLAG <>", value, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagGreaterThan(Integer value) {
            addCriterion("EXAMINFO_FLAG >", value, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXAMINFO_FLAG >=", value, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagLessThan(Integer value) {
            addCriterion("EXAMINFO_FLAG <", value, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagLessThanOrEqualTo(Integer value) {
            addCriterion("EXAMINFO_FLAG <=", value, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagIn(List<Integer> values) {
            addCriterion("EXAMINFO_FLAG in", values, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagNotIn(List<Integer> values) {
            addCriterion("EXAMINFO_FLAG not in", values, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagBetween(Integer value1, Integer value2) {
            addCriterion("EXAMINFO_FLAG between", value1, value2, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("EXAMINFO_FLAG not between", value1, value2, "examinfoFlag");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentIsNull() {
            addCriterion("EXAMINFO_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentIsNotNull() {
            addCriterion("EXAMINFO_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentEqualTo(String value) {
            addCriterion("EXAMINFO_CONTENT =", value, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentNotEqualTo(String value) {
            addCriterion("EXAMINFO_CONTENT <>", value, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentGreaterThan(String value) {
            addCriterion("EXAMINFO_CONTENT >", value, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentGreaterThanOrEqualTo(String value) {
            addCriterion("EXAMINFO_CONTENT >=", value, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentLessThan(String value) {
            addCriterion("EXAMINFO_CONTENT <", value, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentLessThanOrEqualTo(String value) {
            addCriterion("EXAMINFO_CONTENT <=", value, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentLike(String value) {
            addCriterion("EXAMINFO_CONTENT like", value, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentNotLike(String value) {
            addCriterion("EXAMINFO_CONTENT not like", value, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentIn(List<String> values) {
            addCriterion("EXAMINFO_CONTENT in", values, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentNotIn(List<String> values) {
            addCriterion("EXAMINFO_CONTENT not in", values, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentBetween(String value1, String value2) {
            addCriterion("EXAMINFO_CONTENT between", value1, value2, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andExaminfoContentNotBetween(String value1, String value2) {
            addCriterion("EXAMINFO_CONTENT not between", value1, value2, "examinfoContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagIsNull() {
            addCriterion("STUDENTINPUT_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagIsNotNull() {
            addCriterion("STUDENTINPUT_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagEqualTo(Integer value) {
            addCriterion("STUDENTINPUT_FLAG =", value, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagNotEqualTo(Integer value) {
            addCriterion("STUDENTINPUT_FLAG <>", value, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagGreaterThan(Integer value) {
            addCriterion("STUDENTINPUT_FLAG >", value, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("STUDENTINPUT_FLAG >=", value, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagLessThan(Integer value) {
            addCriterion("STUDENTINPUT_FLAG <", value, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagLessThanOrEqualTo(Integer value) {
            addCriterion("STUDENTINPUT_FLAG <=", value, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagIn(List<Integer> values) {
            addCriterion("STUDENTINPUT_FLAG in", values, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagNotIn(List<Integer> values) {
            addCriterion("STUDENTINPUT_FLAG not in", values, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagBetween(Integer value1, Integer value2) {
            addCriterion("STUDENTINPUT_FLAG between", value1, value2, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("STUDENTINPUT_FLAG not between", value1, value2, "studentinputFlag");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentIsNull() {
            addCriterion("STUDENTINPUT_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentIsNotNull() {
            addCriterion("STUDENTINPUT_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentEqualTo(String value) {
            addCriterion("STUDENTINPUT_CONTENT =", value, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentNotEqualTo(String value) {
            addCriterion("STUDENTINPUT_CONTENT <>", value, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentGreaterThan(String value) {
            addCriterion("STUDENTINPUT_CONTENT >", value, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentGreaterThanOrEqualTo(String value) {
            addCriterion("STUDENTINPUT_CONTENT >=", value, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentLessThan(String value) {
            addCriterion("STUDENTINPUT_CONTENT <", value, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentLessThanOrEqualTo(String value) {
            addCriterion("STUDENTINPUT_CONTENT <=", value, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentLike(String value) {
            addCriterion("STUDENTINPUT_CONTENT like", value, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentNotLike(String value) {
            addCriterion("STUDENTINPUT_CONTENT not like", value, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentIn(List<String> values) {
            addCriterion("STUDENTINPUT_CONTENT in", values, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentNotIn(List<String> values) {
            addCriterion("STUDENTINPUT_CONTENT not in", values, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentBetween(String value1, String value2) {
            addCriterion("STUDENTINPUT_CONTENT between", value1, value2, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andStudentinputContentNotBetween(String value1, String value2) {
            addCriterion("STUDENTINPUT_CONTENT not between", value1, value2, "studentinputContent");
            return (Criteria) this;
        }

        public Criteria andScoreFlagIsNull() {
            addCriterion("SCORE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andScoreFlagIsNotNull() {
            addCriterion("SCORE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andScoreFlagEqualTo(Integer value) {
            addCriterion("SCORE_FLAG =", value, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagNotEqualTo(Integer value) {
            addCriterion("SCORE_FLAG <>", value, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagGreaterThan(Integer value) {
            addCriterion("SCORE_FLAG >", value, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("SCORE_FLAG >=", value, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagLessThan(Integer value) {
            addCriterion("SCORE_FLAG <", value, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagLessThanOrEqualTo(Integer value) {
            addCriterion("SCORE_FLAG <=", value, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagIn(List<Integer> values) {
            addCriterion("SCORE_FLAG in", values, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagNotIn(List<Integer> values) {
            addCriterion("SCORE_FLAG not in", values, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagBetween(Integer value1, Integer value2) {
            addCriterion("SCORE_FLAG between", value1, value2, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andScoreFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("SCORE_FLAG not between", value1, value2, "scoreFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagIsNull() {
            addCriterion("NOTICE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagIsNotNull() {
            addCriterion("NOTICE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagEqualTo(Integer value) {
            addCriterion("NOTICE_FLAG =", value, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagNotEqualTo(Integer value) {
            addCriterion("NOTICE_FLAG <>", value, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagGreaterThan(Integer value) {
            addCriterion("NOTICE_FLAG >", value, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("NOTICE_FLAG >=", value, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagLessThan(Integer value) {
            addCriterion("NOTICE_FLAG <", value, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagLessThanOrEqualTo(Integer value) {
            addCriterion("NOTICE_FLAG <=", value, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagIn(List<Integer> values) {
            addCriterion("NOTICE_FLAG in", values, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagNotIn(List<Integer> values) {
            addCriterion("NOTICE_FLAG not in", values, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagBetween(Integer value1, Integer value2) {
            addCriterion("NOTICE_FLAG between", value1, value2, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("NOTICE_FLAG not between", value1, value2, "noticeFlag");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIsNull() {
            addCriterion("NOTICE_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIsNotNull() {
            addCriterion("NOTICE_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeContentEqualTo(String value) {
            addCriterion("NOTICE_CONTENT =", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotEqualTo(String value) {
            addCriterion("NOTICE_CONTENT <>", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentGreaterThan(String value) {
            addCriterion("NOTICE_CONTENT >", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentGreaterThanOrEqualTo(String value) {
            addCriterion("NOTICE_CONTENT >=", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLessThan(String value) {
            addCriterion("NOTICE_CONTENT <", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLessThanOrEqualTo(String value) {
            addCriterion("NOTICE_CONTENT <=", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLike(String value) {
            addCriterion("NOTICE_CONTENT like", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotLike(String value) {
            addCriterion("NOTICE_CONTENT not like", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIn(List<String> values) {
            addCriterion("NOTICE_CONTENT in", values, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotIn(List<String> values) {
            addCriterion("NOTICE_CONTENT not in", values, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentBetween(String value1, String value2) {
            addCriterion("NOTICE_CONTENT between", value1, value2, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotBetween(String value1, String value2) {
            addCriterion("NOTICE_CONTENT not between", value1, value2, "noticeContent");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table e_exam_preview
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
     * This class corresponds to the database table e_exam_preview
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