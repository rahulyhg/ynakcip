package com.prism.pickany247.Response;

import java.util.List;

public class GroceryFilterResponse {


    /**
     * Module : ["Grocery"]
     * Sub_Category : [{"sub_category_id":"27","sub_category_name":"Ghee"},{"sub_category_id":"28","sub_category_name":"Oils"},{"sub_category_id":"29","sub_category_name":"Masala Ingredients"}]
     * Product_Types : [{"id":"58","product_type":"Jelly"},{"id":"57","product_type":"Paste"},{"id":"12","product_type":"Liquid"},{"id":"13","product_type":"Powder"},{"id":"59","product_type":"Cream"}]
     * Brands : [{"brand_id":"3","name":"Amul"},{"brand_id":"66","name":"Milky Mist"},{"brand_id":"87","name":"Patanjali"},{"brand_id":"51","name":"Johnson & Johnson"},{"brand_id":"84","name":"Olivado"},{"brand_id":"39","name":"Gold Drop"},{"brand_id":"40","name":"Gold Winner"},{"brand_id":"124","name":"No Brand"}]
     * Measurements : [{"id":"1","capacity":"100 ml"},{"id":"2","capacity":"1 lts"},{"id":"8","capacity":"2 kgs"},{"id":"11","capacity":"200 ml"},{"id":"12","capacity":"250 ml"},{"id":"13","capacity":"500 ml"},{"id":"19","capacity":"5 lts"},{"id":"22","capacity":"1 kgs"},{"id":"23","capacity":"60 ml"}]
     * Rating : [{"Rating1":"0","Rating2":"0","Rating3":"0","Rating4":"0","Rating5":"0"}]
     * message : No Filters
     */

    private String message;
    private List<String> Module;
    private List<SubCategoryBean> Sub_Category;
    private List<ProductTypesBean> Product_Types;
    private List<BrandsBean> Brands;
    private List<MeasurementsBean> Measurements;
    private List<RatingBean> Rating;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getModule() {
        return Module;
    }

    public void setModule(List<String> Module) {
        this.Module = Module;
    }

    public List<SubCategoryBean> getSub_Category() {
        return Sub_Category;
    }

    public void setSub_Category(List<SubCategoryBean> Sub_Category) {
        this.Sub_Category = Sub_Category;
    }

    public List<ProductTypesBean> getProduct_Types() {
        return Product_Types;
    }

    public void setProduct_Types(List<ProductTypesBean> Product_Types) {
        this.Product_Types = Product_Types;
    }

    public List<BrandsBean> getBrands() {
        return Brands;
    }

    public void setBrands(List<BrandsBean> Brands) {
        this.Brands = Brands;
    }

    public List<MeasurementsBean> getMeasurements() {
        return Measurements;
    }

    public void setMeasurements(List<MeasurementsBean> Measurements) {
        this.Measurements = Measurements;
    }

    public List<RatingBean> getRating() {
        return Rating;
    }

    public void setRating(List<RatingBean> Rating) {
        this.Rating = Rating;
    }

    public static class SubCategoryBean {
        /**
         * sub_category_id : 27
         * sub_category_name : Ghee
         */

        private String sub_category_id;
        private String sub_category_name;

        public String getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(String sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public String getSub_category_name() {
            return sub_category_name;
        }

        public void setSub_category_name(String sub_category_name) {
            this.sub_category_name = sub_category_name;
        }
    }

    public static class ProductTypesBean {
        /**
         * id : 58
         * product_type : Jelly
         */

        private String id;
        private String product_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }
    }

    public static class BrandsBean {
        /**
         * brand_id : 3
         * name : Amul
         */

        private String brand_id;
        private String name;

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MeasurementsBean {
        /**
         * id : 1
         * capacity : 100 ml
         */

        private String id;
        private String capacity;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }
    }

    public static class RatingBean {
        /**
         * Rating1 : 0
         * Rating2 : 0
         * Rating3 : 0
         * Rating4 : 0
         * Rating5 : 0
         */

        private String Rating1;
        private String Rating2;
        private String Rating3;
        private String Rating4;
        private String Rating5;

        public String getRating1() {
            return Rating1;
        }

        public void setRating1(String Rating1) {
            this.Rating1 = Rating1;
        }

        public String getRating2() {
            return Rating2;
        }

        public void setRating2(String Rating2) {
            this.Rating2 = Rating2;
        }

        public String getRating3() {
            return Rating3;
        }

        public void setRating3(String Rating3) {
            this.Rating3 = Rating3;
        }

        public String getRating4() {
            return Rating4;
        }

        public void setRating4(String Rating4) {
            this.Rating4 = Rating4;
        }

        public String getRating5() {
            return Rating5;
        }

        public void setRating5(String Rating5) {
            this.Rating5 = Rating5;
        }
    }
}
