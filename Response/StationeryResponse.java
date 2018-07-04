package com.prism.pickany247.Response;

import java.util.List;

public class StationeryResponse {


    /**
     * filtered_products : [{"Module":"stationery","main_category_id":"3","category_name":"Office Stationery","sub_category_id":"46","sub_category_name":"Planner Refillers","product_id":"PIKHH66NL","product_name":"Planner Refills","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIKHH66NL/1.jpg","AllImages":"../images/product_images/PIKHH66NL/1.jpg,../images/product_images/PIKHH66NL/2.jpg","unit_price":"2072.00","rating":"0"},{"Module":"stationery","main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"3","sub_category_name":"Magazine Racks","product_id":"PIKA205I6","product_name":"inter design","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIKA205I6/inter-design-classico-wall-mount-magazine-rack-1.jpg","AllImages":"../images/product_images/PIKA205I6/inter-design-classico-wall-mount-magazine-rack-1.jpg,../images/product_images/PIKA205I6/inter-design-classico-wall-mount-magazine-rack-2.jpg","unit_price":"1950.00","rating":"0"},{"Module":"stationery","main_category_id":"5","category_name":"Extra Stationery","sub_category_id":"80","sub_category_name":"Tape dispencers","product_id":"PIK83801W","product_name":"Permanent Double Stick Tape with Dispenser","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIK83801W/1.jpg","AllImages":"../images/product_images/PIK83801W/1.jpg,../images/product_images/PIK83801W/2.jpg","unit_price":"1861.00","rating":"0"},{"Module":"stationery","main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"7","sub_category_name":"Business Card Holders","product_id":"PIK2TI1Y6","product_name":"ahsha Premium Acrylic Clear Business Card Holder Display, Plastic Business Card Stand Organizer For Office","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIK2TI1Y6/Business-Card-Holders-RiaTech-1.jpg","AllImages":"../images/product_images/PIK2TI1Y6/Business-Card-Holders-RiaTech-1.jpg,../images/product_images/PIK2TI1Y6/Business-Card-Holders-RiaTech-2.jpg,../images/product_images/PIK2TI1Y6/Business-Card-Holders-RiaTech-3.jpg","unit_price":"1799.00","rating":"4"},{"Module":"stationery","main_category_id":"5","category_name":"Extra Stationery","sub_category_id":"88","sub_category_name":"Sliding files","product_id":"PIKFY5669","product_name":"Sliding File","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIKFY5669/1.jpg","AllImages":"../images/product_images/PIKFY5669/1.jpg,../images/product_images/PIKFY5669/2.jpg,../images/product_images/PIKFY5669/3.jpg","unit_price":"1608.00","rating":"0"},{"Module":"stationery","main_category_id":"3","category_name":"Office Stationery","sub_category_id":"31","sub_category_name":"Planners","product_id":"PIK5RJ2U7","product_name":"Planner","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIK5RJ2U7/1.jpg","AllImages":"../images/product_images/PIK5RJ2U7/1.jpg,../images/product_images/PIK5RJ2U7/2.jpg","unit_price":"1600.00","rating":"0"},{"Module":"stationery","main_category_id":"5","category_name":"Extra Stationery","sub_category_id":"93","sub_category_name":"FeviStik","product_id":"PIK8MQ659","product_name":"Fevistick","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIK8MQ659/22gms-each.jpg","AllImages":"../images/product_images/PIK8MQ659/22gms-each.jpg","unit_price":"1500.00","rating":"0"},{"Module":"stationery","main_category_id":"1","category_name":"Arts & Crafts","sub_category_id":"58","sub_category_name":"Paint Brushes","product_id":"PIKJKN341","product_name":"Paint brush","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIKJKN341/1.jpg","AllImages":"../images/product_images/PIKJKN341/1.jpg,../images/product_images/PIKJKN341/2.jpg","unit_price":"1499.00","rating":"0"},{"Module":"stationery","main_category_id":"4","category_name":"School & College","sub_category_id":"9","sub_category_name":"Pencils","product_id":"PIKH62356","product_name":"color pencils","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIKH62356/1.jpg","AllImages":"../images/product_images/PIKH62356/1.jpg,../images/product_images/PIKH62356/2.jpg,../images/product_images/PIKH62356/3.jpg","unit_price":"1399.00","rating":"0"},{"Module":"stationery","main_category_id":"3","category_name":"Office Stationery","sub_category_id":"46","sub_category_name":"Planner Refillers","product_id":"PIK546DB9","product_name":"Planner Refills","ImagePath":"http://pickany247.com/stationery/stationery/","SingleImage":"../images/product_images/PIK546DB9/1.jpg","AllImages":"../images/product_images/PIK546DB9/1.jpg,../images/product_images/PIK546DB9/2.jpg,../images/product_images/PIK546DB9/3.jpg","unit_price":"1321.00","rating":"0"}]
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
         * Module : stationery
         * main_category_id : 3
         * category_name : Office Stationery
         * sub_category_id : 46
         * sub_category_name : Planner Refillers
         * product_id : PIKHH66NL
         * product_name : Planner Refills
         * ImagePath : http://pickany247.com/stationery/stationery/
         * SingleImage : ../images/product_images/PIKHH66NL/1.jpg
         * AllImages : ../images/product_images/PIKHH66NL/1.jpg,../images/product_images/PIKHH66NL/2.jpg
         * unit_price : 2072.00
         * rating : 0
         */

        private String Module;
        private String main_category_id;
        private String category_name;
        private String sub_category_id;
        private String sub_category_name;
        private String product_id;
        private String product_name;
        private String ImagePath;
        private String SingleImage;
        private String AllImages;
        private String unit_price;
        private String rating;

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

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
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

        public String getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }
    }
}
