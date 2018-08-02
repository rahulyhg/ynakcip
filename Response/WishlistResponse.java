package com.prism.pickany247.Response;

import java.util.List;

public class WishlistResponse {


    /**
     * wishList : [{"id":"156","user_id":"23","category":"stationery","item_id":"37","product_name":"Magazine rack inter design","product_id":"PIKA205I6","unit_price":"1950.00","image":"http://pickany247.com/stationery/stationery/../images/product_images/PIKA205I6/inter-design-classico-wall-mount-magazine-rack-1.jpg"},{"id":"156","user_id":"23","category":"stationery","item_id":"37","product_name":"Redmi 5A","product_id":"PIK1684109","unit_price":"0.00","image":"http://pickany247.com/mobiles/mobiles/../images/uploaded/2018-01-30-04-54-07"}]
     * message : Wish List Items
     */

    private String message;
    private List<WishListBean> wishList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<WishListBean> getWishList() {
        return wishList;
    }

    public void setWishList(List<WishListBean> wishList) {
        this.wishList = wishList;
    }

    public static class WishListBean {
        /**
         * id : 156
         * user_id : 23
         * category : stationery
         * item_id : 37
         * product_name : Magazine rack inter design
         * product_id : PIKA205I6
         * unit_price : 1950.00
         * image : http://pickany247.com/stationery/stationery/../images/product_images/PIKA205I6/inter-design-classico-wall-mount-magazine-rack-1.jpg
         */

        private String id;
        private String user_id;
        private String category;
        private String item_id;
        private String product_name;
        private String product_id;
        private String unit_price;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
