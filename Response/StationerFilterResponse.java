package com.prism.pickany247.Response;

import java.util.List;

public class StationerFilterResponse {


    /**
     * Sub_Category : []
     * Product_Types : [{"product_type":"Documernt Envelopes"}]
     * Brands : [{"brand_name":"Trio","id":"113"}]
     * Colors : [{"id":"1","name":"Gold"},{"id":"2","name":"Black"},{"id":"3","name":"Blue"},{"id":"4","name":"Grey"},{"id":"5","name":"Orange"},{"id":"6","name":"Red"},{"id":"7","name":"Silver"},{"id":"8","name":"White"},{"id":"9","name":"Yellow"},{"id":"10","name":"Platinum"},{"id":"11","name":"Rose"},{"id":"12","name":"Titan"},{"id":"13","name":"Brown"},{"id":"14","name":"Pink"},{"id":"15","name":"Terra Gold"},{"id":"17","name":"Multicolor"},{"id":"18","name":"White & Gold"},{"id":"19","name":"White/Grey"},{"id":"20","name":"Ceramic Black"}]
     * Rating : [{"Rating1":"0","Rating2":"0","Rating3":"0","Rating4":"0","Rating5":"0"}]
     * message : Get All Filters
     */

    private String message;
    private List<?> Sub_Category;
    private List<ProductTypesBean> Product_Types;
    private List<BrandsBean> Brands;
    private List<ColorsBean> Colors;
    private List<RatingBean> Rating;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<?> getSub_Category() {
        return Sub_Category;
    }

    public void setSub_Category(List<?> Sub_Category) {
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

    public List<ColorsBean> getColors() {
        return Colors;
    }

    public void setColors(List<ColorsBean> Colors) {
        this.Colors = Colors;
    }

    public List<RatingBean> getRating() {
        return Rating;
    }

    public void setRating(List<RatingBean> Rating) {
        this.Rating = Rating;
    }

    public static class ProductTypesBean {
        /**
         * product_type : Documernt Envelopes
         */

        private String product_type;

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }
    }

    public static class BrandsBean {
        /**
         * brand_name : Trio
         * id : 113
         */

        private String brand_name;
        private String id;

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class ColorsBean {
        /**
         * id : 1
         * name : Gold
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
