package com.ule.oracle.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class InterfaceBlockExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public InterfaceBlockExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
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
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdIsNull() {
            addCriterion("USER_ONLY_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdIsNotNull() {
            addCriterion("USER_ONLY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdEqualTo(String value) {
            addCriterion("USER_ONLY_ID =", value, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdNotEqualTo(String value) {
            addCriterion("USER_ONLY_ID <>", value, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdGreaterThan(String value) {
            addCriterion("USER_ONLY_ID >", value, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ONLY_ID >=", value, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdLessThan(String value) {
            addCriterion("USER_ONLY_ID <", value, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ONLY_ID <=", value, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdLike(String value) {
            addCriterion("USER_ONLY_ID like", value, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdNotLike(String value) {
            addCriterion("USER_ONLY_ID not like", value, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdIn(List<String> values) {
            addCriterion("USER_ONLY_ID in", values, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdNotIn(List<String> values) {
            addCriterion("USER_ONLY_ID not in", values, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdBetween(String value1, String value2) {
            addCriterion("USER_ONLY_ID between", value1, value2, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andUserOnlyIdNotBetween(String value1, String value2) {
            addCriterion("USER_ONLY_ID not between", value1, value2, "userOnlyId");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeIsNull() {
            addCriterion("INTERFACE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeIsNotNull() {
            addCriterion("INTERFACE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeEqualTo(String value) {
            addCriterion("INTERFACE_CODE =", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeNotEqualTo(String value) {
            addCriterion("INTERFACE_CODE <>", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeGreaterThan(String value) {
            addCriterion("INTERFACE_CODE >", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("INTERFACE_CODE >=", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeLessThan(String value) {
            addCriterion("INTERFACE_CODE <", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeLessThanOrEqualTo(String value) {
            addCriterion("INTERFACE_CODE <=", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeLike(String value) {
            addCriterion("INTERFACE_CODE like", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeNotLike(String value) {
            addCriterion("INTERFACE_CODE not like", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeIn(List<String> values) {
            addCriterion("INTERFACE_CODE in", values, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeNotIn(List<String> values) {
            addCriterion("INTERFACE_CODE not in", values, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeBetween(String value1, String value2) {
            addCriterion("INTERFACE_CODE between", value1, value2, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeNotBetween(String value1, String value2) {
            addCriterion("INTERFACE_CODE not between", value1, value2, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeIsNull() {
            addCriterion("INTERFACE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeIsNotNull() {
            addCriterion("INTERFACE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeEqualTo(String value) {
            addCriterion("INTERFACE_TYPE =", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeNotEqualTo(String value) {
            addCriterion("INTERFACE_TYPE <>", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeGreaterThan(String value) {
            addCriterion("INTERFACE_TYPE >", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("INTERFACE_TYPE >=", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeLessThan(String value) {
            addCriterion("INTERFACE_TYPE <", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeLessThanOrEqualTo(String value) {
            addCriterion("INTERFACE_TYPE <=", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeLike(String value) {
            addCriterion("INTERFACE_TYPE like", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeNotLike(String value) {
            addCriterion("INTERFACE_TYPE not like", value, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeIn(List<String> values) {
            addCriterion("INTERFACE_TYPE in", values, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeNotIn(List<String> values) {
            addCriterion("INTERFACE_TYPE not in", values, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeBetween(String value1, String value2) {
            addCriterion("INTERFACE_TYPE between", value1, value2, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andInterfaceTypeNotBetween(String value1, String value2) {
            addCriterion("INTERFACE_TYPE not between", value1, value2, "interfaceType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeIsNull() {
            addCriterion("ROW_CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeIsNotNull() {
            addCriterion("ROW_CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("ROW_CREATE_TIME =", value, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("ROW_CREATE_TIME <>", value, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("ROW_CREATE_TIME >", value, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ROW_CREATE_TIME >=", value, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeLessThan(Date value) {
            addCriterionForJDBCDate("ROW_CREATE_TIME <", value, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ROW_CREATE_TIME <=", value, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("ROW_CREATE_TIME in", values, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("ROW_CREATE_TIME not in", values, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ROW_CREATE_TIME between", value1, value2, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowCreateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ROW_CREATE_TIME not between", value1, value2, "rowCreateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeIsNull() {
            addCriterion("ROW_LASTUPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeIsNotNull() {
            addCriterion("ROW_LASTUPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME =", value, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME <>", value, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME >", value, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME >=", value, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeLessThan(Date value) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME <", value, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME <=", value, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME in", values, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME not in", values, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME between", value1, value2, "rowLastupdateTime");
            return (Criteria) this;
        }

        public Criteria andRowLastupdateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ROW_LASTUPDATE_TIME not between", value1, value2, "rowLastupdateTime");
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
     * This class corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 29 17:19:17 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
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