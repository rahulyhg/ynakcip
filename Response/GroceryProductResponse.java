package com.prism.pickany247.Response;

import java.util.List;

public class GroceryProductResponse {


    /**
     * filtered_products : [{"id":"57","Module":"grocery","main_category_id":"12","main_category_name":"Pickles and Crunches","sub_category_id":"52","sub_category_name":"Pickles ","product_id":"PIKG68FJP3","product_name":"Amla Pickle","ImagePath":"http://pickany247.com/grocery/grocery/","SingleImage":"../../image/grocery/product_images/PIKG68FJP3/priya-amla-pickle-without-garlic.jpg","AllImages":"../../image/grocery/product_images/PIKG68FJP3/priya-amla-pickle-without-garlic.jpg","brand_id":"91","product_type":"19","color":"","capacity":"300 gms","incl_price":"75.00","rating":"0"},{"id":"59","Module":"grocery","main_category_id":"12","main_category_name":"Pickles and Crunches","sub_category_id":"52","sub_category_name":"Pickles ","product_id":"PIKG81738G","product_name":"Bitter Gourd Pickle","ImagePath":"http://pickany247.com/grocery/grocery/","SingleImage":"../../image/grocery/product_images/PIKG81738G/0785018012275-bitter-gourd-karela-pickle-front.jpg","AllImages":"../../image/grocery/product_images/PIKG81738G/0785018012275-bitter-gourd-karela-pickle-front.jpg,../../image/grocery/product_images/PIKG81738G/priya_bitter_gourd_pickle.jpg","brand_id":"91","product_type":"19","color":"","capacity":"300 gms","incl_price":"75.00","rating":"0"},{"id":"68","Module":"grocery","main_category_id":"12","main_category_name":"Pickles and Crunches","sub_category_id":"53","sub_category_name":"Crunches","product_id":"PIKG7Y4K0X","product_name":"Chana Papad","ImagePath":"http://pickany247.com/grocery/grocery/","SingleImage":"../../image/grocery/product_images/PIKG7Y4K0X/haldiram-chana-papad.jpg","AllImages":"../../image/grocery/product_images/PIKG7Y4K0X/haldiram-chana-papad.jpg","brand_id":"131","product_type":"21","color":"","capacity":"200 gms","incl_price":"60.90","rating":"0"},{"id":"60","Module":"grocery","main_category_id":"12","main_category_name":"Pickles and Crunches","sub_category_id":"52","sub_category_name":"Pickles ","product_id":"PIKG35QX95","product_name":"Gongura Pickle","ImagePath":"http://pickany247.com/grocery/grocery/","SingleImage":"../../image/grocery/product_images/PIKG35QX95/priya_s_gongura_pickle.jpg","AllImages":"../../image/grocery/product_images/PIKG35QX95/priya_s_gongura_pickle.jpg","brand_id":"91","product_type":"19","color":"","capacity":"300 gms","incl_price":"75.00","rating":"0"}]
     * message : Get Filtered Products
     */

    private String message;
    private List<FilteredProductsBean> filtered_products;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FilteredProductsBean> getFiltered_products() {
        return filtered_products;
    }

    public void setFiltered_products(List<FilteredProductsBean> filtered_products) {
        this.filtered_products = filtered_products;
    }

    public static class FilteredProductsBean {
        /**
         * id : 57
         * Module : grocery
         * main_category_id : 12
         * main_category_name : Pickles and Crunches
         * sub_category_id : 52
         * sub_category_name : Pickles
         * product_id : PIKG68FJP3
         * product_name : Amla Pickle
         * ImagePath : http://pickany247.com/grocery/grocery/
         * SingleImage : ../../image/grocery/product_images/PIKG68FJP3/priya-amla-pickle-without-garlic.jpg
         * AllImages : ../../image/grocery/product_images/PIKG68FJP3/priya-amla-pickle-without-garlic.jpg
         * brand_id : 91
         * product_type : 19
         * color :
         * capacity : 300 gms
         * incl_price : 75.00
         * rating : 0
         */

        private String id;
        private String Module;
        private String main_category_id;
        private String main_category_name;
        private String sub_category_id;
        private String sub_category_name;
        private String product_id;
        private String product_name;
        private String ImagePath;
        private String SingleImage;
        private String AllImages;
        private String brand_id;
        private String product_type;
        private String color;
        private String capacity;
        private String incl_price;
        private String rating;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModule() {
            return Module;
        }

        public void setModule(String Module) {
            this.Module = Module;
        }

        public String getMain_category_id() {
            return main_category_id;
        }

        public void setMain_category_id(String main_category_id) {
            this.main_category_id = main_category_id;
        }

        public String getMain_category_name() {
            return main_category_name;
        }

        public void setMain_category_name(String main_category_name) {
            this.main_category_name = main_category_name;
        }

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

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String ImagePath) {
            this.ImagePath = ImagePath;
        }

        public String getSingleImage() {
            return SingleImage;
        }

        public void setSingleImage(String SingleImage) {
            this.SingleImage = SingleImage;
        }

        public String getAllImages() {
            return AllImages;
        }

        public void setAllImages(String AllImages) {
            this.AllImages = AllImages;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public String getIncl_price() {
            return incl_price;
        }

        public void setIncl_price(String incl_price) {
            this.incl_price = incl_price;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }
    }
}
