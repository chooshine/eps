package com.eps.mybatis.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class TestRecordDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Integer limitSize;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public TestRecordDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
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
     * This method corresponds to the database table e_test_record_detail
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
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
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
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLimitSize(Integer limitSize) {
        this.limitSize=limitSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_test_record_detail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLimitSize() {
        return limitSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table e_test_record_detail
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

        public Criteria andTestRecIdIsNull() {
            addCriterion("TEST_REC_ID is null");
            return (Criteria) this;
        }

        public Criteria andTestRecIdIsNotNull() {
            addCriterion("TEST_REC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTestRecIdEqualTo(Integer value) {
            addCriterion("TEST_REC_ID =", value, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdNotEqualTo(Integer value) {
            addCriterion("TEST_REC_ID <>", value, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdGreaterThan(Integer value) {
            addCriterion("TEST_REC_ID >", value, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TEST_REC_ID >=", value, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdLessThan(Integer value) {
            addCriterion("TEST_REC_ID <", value, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdLessThanOrEqualTo(Integer value) {
            addCriterion("TEST_REC_ID <=", value, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdIn(List<Integer> values) {
            addCriterion("TEST_REC_ID in", values, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdNotIn(List<Integer> values) {
            addCriterion("TEST_REC_ID not in", values, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdBetween(Integer value1, Integer value2) {
            addCriterion("TEST_REC_ID between", value1, value2, "testRecId");
            return (Criteria) this;
        }

        public Criteria andTestRecIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TEST_REC_ID not between", value1, value2, "testRecId");
            return (Criteria) this;
        }

        public Criteria andQuesIdIsNull() {
            addCriterion("QUES_ID is null");
            return (Criteria) this;
        }

        public Criteria andQuesIdIsNotNull() {
            addCriterion("QUES_ID is not null");
            return (Criteria) this;
        }

        public Criteria andQuesIdEqualTo(Integer value) {
            addCriterion("QUES_ID =", value, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdNotEqualTo(Integer value) {
            addCriterion("QUES_ID <>", value, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdGreaterThan(Integer value) {
            addCriterion("QUES_ID >", value, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUES_ID >=", value, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdLessThan(Integer value) {
            addCriterion("QUES_ID <", value, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdLessThanOrEqualTo(Integer value) {
            addCriterion("QUES_ID <=", value, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdIn(List<Integer> values) {
            addCriterion("QUES_ID in", values, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdNotIn(List<Integer> values) {
            addCriterion("QUES_ID not in", values, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdBetween(Integer value1, Integer value2) {
            addCriterion("QUES_ID between", value1, value2, "quesId");
            return (Criteria) this;
        }

        public Criteria andQuesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("QUES_ID not between", value1, value2, "quesId");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("SCORE is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("SCORE is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Double value) {
            addCriterion("SCORE =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Double value) {
            addCriterion("SCORE <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Double value) {
            addCriterion("SCORE >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Double value) {
            addCriterion("SCORE >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Double value) {
            addCriterion("SCORE <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Double value) {
            addCriterion("SCORE <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Double> values) {
            addCriterion("SCORE in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Double> values) {
            addCriterion("SCORE not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Double value1, Double value2) {
            addCriterion("SCORE between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Double value1, Double value2) {
            addCriterion("SCORE not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andOScoreIsNull() {
            addCriterion("O_SCORE is null");
            return (Criteria) this;
        }

        public Criteria andOScoreIsNotNull() {
            addCriterion("O_SCORE is not null");
            return (Criteria) this;
        }

        public Criteria andOScoreEqualTo(String value) {
            addCriterion("O_SCORE =", value, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreNotEqualTo(String value) {
            addCriterion("O_SCORE <>", value, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreGreaterThan(String value) {
            addCriterion("O_SCORE >", value, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreGreaterThanOrEqualTo(String value) {
            addCriterion("O_SCORE >=", value, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreLessThan(String value) {
            addCriterion("O_SCORE <", value, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreLessThanOrEqualTo(String value) {
            addCriterion("O_SCORE <=", value, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreLike(String value) {
            addCriterion("O_SCORE like", value, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreNotLike(String value) {
            addCriterion("O_SCORE not like", value, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreIn(List<String> values) {
            addCriterion("O_SCORE in", values, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreNotIn(List<String> values) {
            addCriterion("O_SCORE not in", values, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreBetween(String value1, String value2) {
            addCriterion("O_SCORE between", value1, value2, "oScore");
            return (Criteria) this;
        }

        public Criteria andOScoreNotBetween(String value1, String value2) {
            addCriterion("O_SCORE not between", value1, value2, "oScore");
            return (Criteria) this;
        }

        public Criteria andEnterTimeIsNull() {
            addCriterion("ENTER_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEnterTimeIsNotNull() {
            addCriterion("ENTER_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEnterTimeEqualTo(String value) {
            addCriterion("ENTER_TIME =", value, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeNotEqualTo(String value) {
            addCriterion("ENTER_TIME <>", value, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeGreaterThan(String value) {
            addCriterion("ENTER_TIME >", value, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ENTER_TIME >=", value, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeLessThan(String value) {
            addCriterion("ENTER_TIME <", value, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeLessThanOrEqualTo(String value) {
            addCriterion("ENTER_TIME <=", value, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeLike(String value) {
            addCriterion("ENTER_TIME like", value, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeNotLike(String value) {
            addCriterion("ENTER_TIME not like", value, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeIn(List<String> values) {
            addCriterion("ENTER_TIME in", values, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeNotIn(List<String> values) {
            addCriterion("ENTER_TIME not in", values, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeBetween(String value1, String value2) {
            addCriterion("ENTER_TIME between", value1, value2, "enterTime");
            return (Criteria) this;
        }

        public Criteria andEnterTimeNotBetween(String value1, String value2) {
            addCriterion("ENTER_TIME not between", value1, value2, "enterTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeIsNull() {
            addCriterion("LEFT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLeftTimeIsNotNull() {
            addCriterion("LEFT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLeftTimeEqualTo(String value) {
            addCriterion("LEFT_TIME =", value, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeNotEqualTo(String value) {
            addCriterion("LEFT_TIME <>", value, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeGreaterThan(String value) {
            addCriterion("LEFT_TIME >", value, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeGreaterThanOrEqualTo(String value) {
            addCriterion("LEFT_TIME >=", value, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeLessThan(String value) {
            addCriterion("LEFT_TIME <", value, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeLessThanOrEqualTo(String value) {
            addCriterion("LEFT_TIME <=", value, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeLike(String value) {
            addCriterion("LEFT_TIME like", value, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeNotLike(String value) {
            addCriterion("LEFT_TIME not like", value, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeIn(List<String> values) {
            addCriterion("LEFT_TIME in", values, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeNotIn(List<String> values) {
            addCriterion("LEFT_TIME not in", values, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeBetween(String value1, String value2) {
            addCriterion("LEFT_TIME between", value1, value2, "leftTime");
            return (Criteria) this;
        }

        public Criteria andLeftTimeNotBetween(String value1, String value2) {
            addCriterion("LEFT_TIME not between", value1, value2, "leftTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeIsNull() {
            addCriterion("ANSWER_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeIsNotNull() {
            addCriterion("ANSWER_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeEqualTo(String value) {
            addCriterion("ANSWER_TIME =", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotEqualTo(String value) {
            addCriterion("ANSWER_TIME <>", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeGreaterThan(String value) {
            addCriterion("ANSWER_TIME >", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ANSWER_TIME >=", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeLessThan(String value) {
            addCriterion("ANSWER_TIME <", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeLessThanOrEqualTo(String value) {
            addCriterion("ANSWER_TIME <=", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeLike(String value) {
            addCriterion("ANSWER_TIME like", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotLike(String value) {
            addCriterion("ANSWER_TIME not like", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeIn(List<String> values) {
            addCriterion("ANSWER_TIME in", values, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotIn(List<String> values) {
            addCriterion("ANSWER_TIME not in", values, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeBetween(String value1, String value2) {
            addCriterion("ANSWER_TIME between", value1, value2, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotBetween(String value1, String value2) {
            addCriterion("ANSWER_TIME not between", value1, value2, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerLogIsNull() {
            addCriterion("ANSWER_LOG is null");
            return (Criteria) this;
        }

        public Criteria andAnswerLogIsNotNull() {
            addCriterion("ANSWER_LOG is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerLogEqualTo(String value) {
            addCriterion("ANSWER_LOG =", value, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogNotEqualTo(String value) {
            addCriterion("ANSWER_LOG <>", value, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogGreaterThan(String value) {
            addCriterion("ANSWER_LOG >", value, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogGreaterThanOrEqualTo(String value) {
            addCriterion("ANSWER_LOG >=", value, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogLessThan(String value) {
            addCriterion("ANSWER_LOG <", value, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogLessThanOrEqualTo(String value) {
            addCriterion("ANSWER_LOG <=", value, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogLike(String value) {
            addCriterion("ANSWER_LOG like", value, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogNotLike(String value) {
            addCriterion("ANSWER_LOG not like", value, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogIn(List<String> values) {
            addCriterion("ANSWER_LOG in", values, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogNotIn(List<String> values) {
            addCriterion("ANSWER_LOG not in", values, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogBetween(String value1, String value2) {
            addCriterion("ANSWER_LOG between", value1, value2, "answerLog");
            return (Criteria) this;
        }

        public Criteria andAnswerLogNotBetween(String value1, String value2) {
            addCriterion("ANSWER_LOG not between", value1, value2, "answerLog");
            return (Criteria) this;
        }

        public Criteria andCommentRecIsNull() {
            addCriterion("COMMENT_REC is null");
            return (Criteria) this;
        }

        public Criteria andCommentRecIsNotNull() {
            addCriterion("COMMENT_REC is not null");
            return (Criteria) this;
        }

        public Criteria andCommentRecEqualTo(String value) {
            addCriterion("COMMENT_REC =", value, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecNotEqualTo(String value) {
            addCriterion("COMMENT_REC <>", value, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecGreaterThan(String value) {
            addCriterion("COMMENT_REC >", value, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecGreaterThanOrEqualTo(String value) {
            addCriterion("COMMENT_REC >=", value, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecLessThan(String value) {
            addCriterion("COMMENT_REC <", value, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecLessThanOrEqualTo(String value) {
            addCriterion("COMMENT_REC <=", value, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecLike(String value) {
            addCriterion("COMMENT_REC like", value, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecNotLike(String value) {
            addCriterion("COMMENT_REC not like", value, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecIn(List<String> values) {
            addCriterion("COMMENT_REC in", values, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecNotIn(List<String> values) {
            addCriterion("COMMENT_REC not in", values, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecBetween(String value1, String value2) {
            addCriterion("COMMENT_REC between", value1, value2, "commentRec");
            return (Criteria) this;
        }

        public Criteria andCommentRecNotBetween(String value1, String value2) {
            addCriterion("COMMENT_REC not between", value1, value2, "commentRec");
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
     * This class corresponds to the database table e_test_record_detail
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
     * This class corresponds to the database table e_test_record_detail
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