package com.prism.pickany247.Response;

import java.util.List;

public class ProductResponse {


    /**
     * filtered_products : [{"id":"331","Module":"celebrations","main_category_id":"1","main_category_name":"Cakes","sub_category_id":"","sub_category_name":"","product_id":"PIKO48425","product_name":"Ferraro-Almond","ImagePath":"http://pickany247.com/celebrations/celebrations/","SingleImage":"../images/uploaded/2018-03-29-12-24-35ferraro-almond.jpg","AllImages":"","brand_id":"","product_type":"","os":"","color":"","storage":"","ram":"","cart_type":"single","tax_rate":"18.00","item_id":"404,405","unit_price_incl_tax":"650.00,925.00","capacity":"0.5,1","heart_shape":"0,1","eggless":"","eggless_amt":"75.00","heart_shape_amt":"100.00","availability":"10","flavour":"","message":"","screen":"","processor":"","camera":"","battery":"","rating":"","discount":"","features":"","delivery_time":"10 AM to 2 PM,2 PM to 6 PM,6 PM to 10 PM,10 PM to 11.59 PM"}]
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
         * id : 331
         * Module : celebrations
         * main_category_id : 1
         * main_category_name : Cakes
         * sub_category_id :
         * sub_category_name :
         * product_id : PIKO48425
         * product_name : Ferraro-Almond
         * ImagePath : http://pickany247.com/celebrations/celebrations/
         * SingleImage : ../images/uploaded/2018-03-29-12-24-35ferraro-almond.jpg
         * AllImages :
         * brand_id :
         * product_type :
         * os :
         * color :
         * storage :
         * ram :
         * cart_type : single
         * tax_rate : 18.00
         * item_id : 404,405
         * unit_price_incl_tax : 650.00,925.00
         * capacity : 0.5,1
         * heart_shape : 0,1
         * eggless :
         * eggless_amt : 75.00
         * heart_shape_amt : 100.00
         * availability : 10
         * flavour :
         * message :
         * screen :
         * processor :
         * camera :
         * battery :
         * rating :
         * discount :
         * features :
         * delivery_time : 10 AM to 2 PM,2 PM to 6 PM,6 PM to 10 PM,10 PM to 11.59 PM
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
        private String os;
        private String color;
        private String storage;
        private String ram;
        private String cart_type;
        private String tax_rate;
        private String item_id;
        private String unit_price_incl_tax;
        private String capacity;
        private String heart_shape;
        private String eggless;
        private String eggless_amt;
        private String heart_shape_amt;
        private String availability;
        private String flavour;
        private String message;
        private String screen;
        private String processor;
        private String camera;
        private String battery;
        private String rating;
        private String discount;
        private String features;
        private String delivery_time;

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

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getStorage() {
            return storage;
        }

        public void setStorage(String storage) {
            this.storage = storage;
        }

        public String getRam() {
            return ram;
        }

        public void setRam(String ram) {
            this.ram = ram;
        }

        public String getCart_type() {
            return cart_type;
        }

        public void setCart_type(String cart_type) {
            this.cart_type = cart_type;
        }

        public String getTax_rate() {
            return tax_rate;
        }

        public void setTax_rate(String tax_rate) {
            this.tax_rate = tax_rate;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getUnit_price_incl_tax() {
            return unit_price_incl_tax;
        }

        public void setUnit_price_incl_tax(String unit_price_incl_tax) {
            this.unit_price_incl_tax = unit_price_incl_tax;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public String getHeart_shape() {
            return heart_shape;
        }

        public void setHeart_shape(String heart_shape) {
            this.heart_shape = heart_shape;
        }

        public String getEggless() {
            return eggless;
        }

        public void setEggless(String eggless) {
            this.eggless = eggless;
        }

        public String getEggless_amt() {
            return eggless_amt;
        }

        public void setEggless_amt(String eggless_amt) {
            this.eggless_amt = eggless_amt;
        }

        public String getHeart_shape_amt() {
            return heart_shape_amt;
        }

        public void setHeart_shape_amt(String heart_shape_amt) {
            this.heart_shape_amt = heart_shape_amt;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getFlavour() {
            return flavour;
        }

        public void setFlavour(String flavour) {
            this.flavour = flavour;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getScreen() {
            return screen;
        }

        public void setScreen(String screen) {
            this.screen = screen;
        }

        public String getProcessor() {
            return processor;
        }

        public void setProcessor(String processor) {
            this.processor = processor;
        }

        public String getCamera() {
            return camera;
        }

        public void setCamera(String camera) {
            this.camera = camera;
        }

        public String getBattery() {
            return battery;
        }

        public void setBattery(String battery) {
            this.battery = battery;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public String getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(String delivery_time) {
            this.delivery_time = delivery_time;
        }
    }
}
