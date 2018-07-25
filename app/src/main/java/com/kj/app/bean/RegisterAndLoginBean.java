package com.kj.app.bean;

import java.util.List;

public class RegisterAndLoginBean {
    /**
     * hasAnyArchives : true
     * carArchives : [{"associatedServiceVariantEntityList":[{"carTypeAmount":2,"carTypeProportion":null,"charge":0.1,"chargeType":"FIX","chargeTypeDesc":"钉住总价","deficiency":null,"deliveryDuration":10,"difficultDegree":"ONE_STANDARD","difficultDegreeDesc":"1.0","duration":40,"id":2,"infeasible":null,"mgtModified":"2018-06-28 09:42:27","name":"更换空滤A150","outputValue":null,"outputValueMax":null,"outputValueMin":null,"priceRange":null,"priceRangeMax":null,"priceRangeMin":null,"progress":100,"recommendationGrade":null,"relationProcedureStatus":null,"selected":null,"serviceBaseId":2,"serviceBaseName":"更换空滤","serviceCategoryName":null,"serviceCategoryNameDesc":null,"skuAmount":null,"status":true,"variantGoodsAmount":2,"version":12}],"carId":1,"carNum":"渝E11111","carTypeId":1,"carTypeName":"169031-M266920-GA722801","currentCarAge":374,"currentMiles":17322,"cycleBaseId":6,"cycleBaseName":"空气滤芯","cycleVariantId":6,"cycleVariantName":"空气滤芯-变体1","healthIndex":0,"id":3,"lastReplaceCycleDate":null,"lastReplaceCycleKm":0,"mgtCreate":"2018-07-05 17:26:26","mustReplaceCycleKm":0,"mustReplaceCycleTime":0,"nextReplaceCycleKm":12000,"perMiles":46.32,"recommendReplaceCycleKm":10000,"recommendReplaceCycleTime":0,"recommendationGrade":null,"restSafetyMiles":0,"version":0,"warningDate":"2018-07-06 10:43:02"},{"associatedServiceVariantEntityList":[{"carTypeAmount":2,"carTypeProportion":null,"charge":0.1,"chargeType":"FIX","chargeTypeDesc":"钉住总价","deficiency":null,"deliveryDuration":0,"difficultDegree":"ONE_STANDARD","difficultDegreeDesc":"1.0","duration":40,"id":4,"infeasible":null,"mgtModified":"2018-05-25 10:20:47","name":"K5积碳（A150）","outputValue":null,"outputValueMax":null,"outputValueMin":null,"priceRange":null,"priceRangeMax":null,"priceRangeMin":null,"progress":100,"recommendationGrade":null,"relationProcedureStatus":null,"selected":null,"serviceBaseId":3,"serviceBaseName":"K5积碳","serviceCategoryName":null,"serviceCategoryNameDesc":null,"skuAmount":null,"status":false,"variantGoodsAmount":7,"version":8}],"carId":1,"carNum":"渝E11111","carTypeId":1,"carTypeName":"169031-M266920-GA722801","currentCarAge":374,"currentMiles":17322,"cycleBaseId":10,"cycleBaseName":"发动机积碳","cycleVariantId":3,"cycleVariantName":"发动机积碳-变体1","healthIndex":0.1339,"id":2,"lastReplaceCycleDate":null,"lastReplaceCycleKm":0,"mgtCreate":"2018-07-05 17:26:26","mustReplaceCycleKm":0,"mustReplaceCycleTime":0,"nextReplaceCycleKm":20000,"perMiles":46.32,"recommendReplaceCycleKm":20000,"recommendReplaceCycleTime":0,"recommendationGrade":null,"restSafetyMiles":2678,"version":0,"warningDate":"2018-09-08 00:00:00"},{"associatedServiceVariantEntityList":[{"carTypeAmount":2,"carTypeProportion":null,"charge":0.1,"chargeType":"FIX","chargeTypeDesc":"钉住总价","deficiency":null,"deliveryDuration":0,"difficultDegree":"ONE_STANDARD","difficultDegreeDesc":"1.0","duration":40,"id":1,"infeasible":null,"mgtModified":"2018-07-05 17:44:39","name":"更换火花塞（4根）","outputValue":null,"outputValueMax":null,"outputValueMin":null,"priceRange":null,"priceRangeMax":null,"priceRangeMin":null,"progress":100,"recommendationGrade":null,"relationProcedureStatus":null,"selected":null,"serviceBaseId":1,"serviceBaseName":"更换火花塞","serviceCategoryName":null,"serviceCategoryNameDesc":null,"skuAmount":null,"status":false,"variantGoodsAmount":1,"version":13}],"carId":1,"carNum":"渝E11111","carTypeId":1,"carTypeName":"169031-M266920-GA722801","currentCarAge":374,"currentMiles":17322,"cycleBaseId":1,"cycleBaseName":"火花塞","cycleVariantId":1,"cycleVariantName":"k-火花塞0001","healthIndex":0,"id":1,"lastReplaceCycleDate":null,"lastReplaceCycleKm":0,"mgtCreate":"2018-07-05 17:26:25","mustReplaceCycleKm":0,"mustReplaceCycleTime":0,"nextReplaceCycleKm":15000,"perMiles":46.32,"recommendReplaceCycleKm":15000,"recommendReplaceCycleTime":3,"recommendationGrade":null,"restSafetyMiles":0,"version":0,"warningDate":"2018-07-06 10:43:03"}]
     * totalPercent : 0
     * currentMiles : 17322
     * perMiles : 46.32
     */

    private boolean hasAnyArchives;
    private int totalPercent;
    private int currentMiles;
    private double perMiles;
    private List<CarArchivesBean> carArchives;

    public boolean isHasAnyArchives() {
        return hasAnyArchives;
    }

    public void setHasAnyArchives(boolean hasAnyArchives) {
        this.hasAnyArchives = hasAnyArchives;
    }

    public int getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(int totalPercent) {
        this.totalPercent = totalPercent;
    }

    public int getCurrentMiles() {
        return currentMiles;
    }

    public void setCurrentMiles(int currentMiles) {
        this.currentMiles = currentMiles;
    }

    public double getPerMiles() {
        return perMiles;
    }

    public void setPerMiles(double perMiles) {
        this.perMiles = perMiles;
    }

    public List<CarArchivesBean> getCarArchives() {
        return carArchives;
    }

    public void setCarArchives(List<CarArchivesBean> carArchives) {
        this.carArchives = carArchives;
    }

    public static class CarArchivesBean {
        /**
         * associatedServiceVariantEntityList : [{"carTypeAmount":2,"carTypeProportion":null,"charge":0.1,"chargeType":"FIX","chargeTypeDesc":"钉住总价","deficiency":null,"deliveryDuration":10,"difficultDegree":"ONE_STANDARD","difficultDegreeDesc":"1.0","duration":40,"id":2,"infeasible":null,"mgtModified":"2018-06-28 09:42:27","name":"更换空滤A150","outputValue":null,"outputValueMax":null,"outputValueMin":null,"priceRange":null,"priceRangeMax":null,"priceRangeMin":null,"progress":100,"recommendationGrade":null,"relationProcedureStatus":null,"selected":null,"serviceBaseId":2,"serviceBaseName":"更换空滤","serviceCategoryName":null,"serviceCategoryNameDesc":null,"skuAmount":null,"status":true,"variantGoodsAmount":2,"version":12}]
         * carId : 1
         * carNum : 渝E11111
         * carTypeId : 1
         * carTypeName : 169031-M266920-GA722801
         * currentCarAge : 374
         * currentMiles : 17322
         * cycleBaseId : 6
         * cycleBaseName : 空气滤芯
         * cycleVariantId : 6
         * cycleVariantName : 空气滤芯-变体1
         * healthIndex : 0
         * id : 3
         * lastReplaceCycleDate : null
         * lastReplaceCycleKm : 0
         * mgtCreate : 2018-07-05 17:26:26
         * mustReplaceCycleKm : 0
         * mustReplaceCycleTime : 0
         * nextReplaceCycleKm : 12000
         * perMiles : 46.32
         * recommendReplaceCycleKm : 10000
         * recommendReplaceCycleTime : 0
         * recommendationGrade : null
         * restSafetyMiles : 0
         * version : 0
         * warningDate : 2018-07-06 10:43:02
         */

        private int carId;
        private String carNum;
        private int carTypeId;
        private String carTypeName;
        private int currentCarAge;
        private int currentMiles;
        private int cycleBaseId;
        private String cycleBaseName;
        private int cycleVariantId;
        private String cycleVariantName;
        private int healthIndex;
        private int id;
        private Object lastReplaceCycleDate;
        private int lastReplaceCycleKm;
        private String mgtCreate;
        private int mustReplaceCycleKm;
        private int mustReplaceCycleTime;
        private int nextReplaceCycleKm;
        private double perMiles;
        private int recommendReplaceCycleKm;
        private int recommendReplaceCycleTime;
        private Object recommendationGrade;
        private int restSafetyMiles;
        private int version;
        private String warningDate;
        private List<AssociatedServiceVariantEntityListBean> associatedServiceVariantEntityList;

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }

        public int getCarTypeId() {
            return carTypeId;
        }

        public void setCarTypeId(int carTypeId) {
            this.carTypeId = carTypeId;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
        }

        public int getCurrentCarAge() {
            return currentCarAge;
        }

        public void setCurrentCarAge(int currentCarAge) {
            this.currentCarAge = currentCarAge;
        }

        public int getCurrentMiles() {
            return currentMiles;
        }

        public void setCurrentMiles(int currentMiles) {
            this.currentMiles = currentMiles;
        }

        public int getCycleBaseId() {
            return cycleBaseId;
        }

        public void setCycleBaseId(int cycleBaseId) {
            this.cycleBaseId = cycleBaseId;
        }

        public String getCycleBaseName() {
            return cycleBaseName;
        }

        public void setCycleBaseName(String cycleBaseName) {
            this.cycleBaseName = cycleBaseName;
        }

        public int getCycleVariantId() {
            return cycleVariantId;
        }

        public void setCycleVariantId(int cycleVariantId) {
            this.cycleVariantId = cycleVariantId;
        }

        public String getCycleVariantName() {
            return cycleVariantName;
        }

        public void setCycleVariantName(String cycleVariantName) {
            this.cycleVariantName = cycleVariantName;
        }

        public int getHealthIndex() {
            return healthIndex;
        }

        public void setHealthIndex(int healthIndex) {
            this.healthIndex = healthIndex;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getLastReplaceCycleDate() {
            return lastReplaceCycleDate;
        }

        public void setLastReplaceCycleDate(Object lastReplaceCycleDate) {
            this.lastReplaceCycleDate = lastReplaceCycleDate;
        }

        public int getLastReplaceCycleKm() {
            return lastReplaceCycleKm;
        }

        public void setLastReplaceCycleKm(int lastReplaceCycleKm) {
            this.lastReplaceCycleKm = lastReplaceCycleKm;
        }

        public String getMgtCreate() {
            return mgtCreate;
        }

        public void setMgtCreate(String mgtCreate) {
            this.mgtCreate = mgtCreate;
        }

        public int getMustReplaceCycleKm() {
            return mustReplaceCycleKm;
        }

        public void setMustReplaceCycleKm(int mustReplaceCycleKm) {
            this.mustReplaceCycleKm = mustReplaceCycleKm;
        }

        public int getMustReplaceCycleTime() {
            return mustReplaceCycleTime;
        }

        public void setMustReplaceCycleTime(int mustReplaceCycleTime) {
            this.mustReplaceCycleTime = mustReplaceCycleTime;
        }

        public int getNextReplaceCycleKm() {
            return nextReplaceCycleKm;
        }

        public void setNextReplaceCycleKm(int nextReplaceCycleKm) {
            this.nextReplaceCycleKm = nextReplaceCycleKm;
        }

        public double getPerMiles() {
            return perMiles;
        }

        public void setPerMiles(double perMiles) {
            this.perMiles = perMiles;
        }

        public int getRecommendReplaceCycleKm() {
            return recommendReplaceCycleKm;
        }

        public void setRecommendReplaceCycleKm(int recommendReplaceCycleKm) {
            this.recommendReplaceCycleKm = recommendReplaceCycleKm;
        }

        public int getRecommendReplaceCycleTime() {
            return recommendReplaceCycleTime;
        }

        public void setRecommendReplaceCycleTime(int recommendReplaceCycleTime) {
            this.recommendReplaceCycleTime = recommendReplaceCycleTime;
        }

        public Object getRecommendationGrade() {
            return recommendationGrade;
        }

        public void setRecommendationGrade(Object recommendationGrade) {
            this.recommendationGrade = recommendationGrade;
        }

        public int getRestSafetyMiles() {
            return restSafetyMiles;
        }

        public void setRestSafetyMiles(int restSafetyMiles) {
            this.restSafetyMiles = restSafetyMiles;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getWarningDate() {
            return warningDate;
        }

        public void setWarningDate(String warningDate) {
            this.warningDate = warningDate;
        }

        public List<AssociatedServiceVariantEntityListBean> getAssociatedServiceVariantEntityList() {
            return associatedServiceVariantEntityList;
        }

        public void setAssociatedServiceVariantEntityList(List<AssociatedServiceVariantEntityListBean> associatedServiceVariantEntityList) {
            this.associatedServiceVariantEntityList = associatedServiceVariantEntityList;
        }

        public static class AssociatedServiceVariantEntityListBean {
            /**
             * carTypeAmount : 2
             * carTypeProportion : null
             * charge : 0.1
             * chargeType : FIX
             * chargeTypeDesc : 钉住总价
             * deficiency : null
             * deliveryDuration : 10
             * difficultDegree : ONE_STANDARD
             * difficultDegreeDesc : 1.0
             * duration : 40
             * id : 2
             * infeasible : null
             * mgtModified : 2018-06-28 09:42:27
             * name : 更换空滤A150
             * outputValue : null
             * outputValueMax : null
             * outputValueMin : null
             * priceRange : null
             * priceRangeMax : null
             * priceRangeMin : null
             * progress : 100
             * recommendationGrade : null
             * relationProcedureStatus : null
             * selected : null
             * serviceBaseId : 2
             * serviceBaseName : 更换空滤
             * serviceCategoryName : null
             * serviceCategoryNameDesc : null
             * skuAmount : null
             * status : true
             * variantGoodsAmount : 2
             * version : 12
             */

            private int carTypeAmount;
            private Object carTypeProportion;
            private double charge;
            private String chargeType;
            private String chargeTypeDesc;
            private Object deficiency;
            private int deliveryDuration;
            private String difficultDegree;
            private String difficultDegreeDesc;
            private int duration;
            private int id;
            private Object infeasible;
            private String mgtModified;
            private String name;
            private Object outputValue;
            private Object outputValueMax;
            private Object outputValueMin;
            private Object priceRange;
            private Object priceRangeMax;
            private Object priceRangeMin;
            private int progress;
            private Object recommendationGrade;
            private Object relationProcedureStatus;
            private Object selected;
            private int serviceBaseId;
            private String serviceBaseName;
            private Object serviceCategoryName;
            private Object serviceCategoryNameDesc;
            private Object skuAmount;
            private boolean status;
            private int variantGoodsAmount;
            private int version;

            public int getCarTypeAmount() {
                return carTypeAmount;
            }

            public void setCarTypeAmount(int carTypeAmount) {
                this.carTypeAmount = carTypeAmount;
            }

            public Object getCarTypeProportion() {
                return carTypeProportion;
            }

            public void setCarTypeProportion(Object carTypeProportion) {
                this.carTypeProportion = carTypeProportion;
            }

            public double getCharge() {
                return charge;
            }

            public void setCharge(double charge) {
                this.charge = charge;
            }

            public String getChargeType() {
                return chargeType;
            }

            public void setChargeType(String chargeType) {
                this.chargeType = chargeType;
            }

            public String getChargeTypeDesc() {
                return chargeTypeDesc;
            }

            public void setChargeTypeDesc(String chargeTypeDesc) {
                this.chargeTypeDesc = chargeTypeDesc;
            }

            public Object getDeficiency() {
                return deficiency;
            }

            public void setDeficiency(Object deficiency) {
                this.deficiency = deficiency;
            }

            public int getDeliveryDuration() {
                return deliveryDuration;
            }

            public void setDeliveryDuration(int deliveryDuration) {
                this.deliveryDuration = deliveryDuration;
            }

            public String getDifficultDegree() {
                return difficultDegree;
            }

            public void setDifficultDegree(String difficultDegree) {
                this.difficultDegree = difficultDegree;
            }

            public String getDifficultDegreeDesc() {
                return difficultDegreeDesc;
            }

            public void setDifficultDegreeDesc(String difficultDegreeDesc) {
                this.difficultDegreeDesc = difficultDegreeDesc;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getInfeasible() {
                return infeasible;
            }

            public void setInfeasible(Object infeasible) {
                this.infeasible = infeasible;
            }

            public String getMgtModified() {
                return mgtModified;
            }

            public void setMgtModified(String mgtModified) {
                this.mgtModified = mgtModified;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getOutputValue() {
                return outputValue;
            }

            public void setOutputValue(Object outputValue) {
                this.outputValue = outputValue;
            }

            public Object getOutputValueMax() {
                return outputValueMax;
            }

            public void setOutputValueMax(Object outputValueMax) {
                this.outputValueMax = outputValueMax;
            }

            public Object getOutputValueMin() {
                return outputValueMin;
            }

            public void setOutputValueMin(Object outputValueMin) {
                this.outputValueMin = outputValueMin;
            }

            public Object getPriceRange() {
                return priceRange;
            }

            public void setPriceRange(Object priceRange) {
                this.priceRange = priceRange;
            }

            public Object getPriceRangeMax() {
                return priceRangeMax;
            }

            public void setPriceRangeMax(Object priceRangeMax) {
                this.priceRangeMax = priceRangeMax;
            }

            public Object getPriceRangeMin() {
                return priceRangeMin;
            }

            public void setPriceRangeMin(Object priceRangeMin) {
                this.priceRangeMin = priceRangeMin;
            }

            public int getProgress() {
                return progress;
            }

            public void setProgress(int progress) {
                this.progress = progress;
            }

            public Object getRecommendationGrade() {
                return recommendationGrade;
            }

            public void setRecommendationGrade(Object recommendationGrade) {
                this.recommendationGrade = recommendationGrade;
            }

            public Object getRelationProcedureStatus() {
                return relationProcedureStatus;
            }

            public void setRelationProcedureStatus(Object relationProcedureStatus) {
                this.relationProcedureStatus = relationProcedureStatus;
            }

            public Object getSelected() {
                return selected;
            }

            public void setSelected(Object selected) {
                this.selected = selected;
            }

            public int getServiceBaseId() {
                return serviceBaseId;
            }

            public void setServiceBaseId(int serviceBaseId) {
                this.serviceBaseId = serviceBaseId;
            }

            public String getServiceBaseName() {
                return serviceBaseName;
            }

            public void setServiceBaseName(String serviceBaseName) {
                this.serviceBaseName = serviceBaseName;
            }

            public Object getServiceCategoryName() {
                return serviceCategoryName;
            }

            public void setServiceCategoryName(Object serviceCategoryName) {
                this.serviceCategoryName = serviceCategoryName;
            }

            public Object getServiceCategoryNameDesc() {
                return serviceCategoryNameDesc;
            }

            public void setServiceCategoryNameDesc(Object serviceCategoryNameDesc) {
                this.serviceCategoryNameDesc = serviceCategoryNameDesc;
            }

            public Object getSkuAmount() {
                return skuAmount;
            }

            public void setSkuAmount(Object skuAmount) {
                this.skuAmount = skuAmount;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public int getVariantGoodsAmount() {
                return variantGoodsAmount;
            }

            public void setVariantGoodsAmount(int variantGoodsAmount) {
                this.variantGoodsAmount = variantGoodsAmount;
            }

            public int getVersion() {
                return version;
            }

            public void setVersion(int version) {
                this.version = version;
            }
        }
    }
}
