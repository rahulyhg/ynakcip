package com.prism.pickany247.Response;

import java.util.List;

public class StationeryCatResponse {


    /**
     * Categories : [{"Module":"stationery","category_name":"Arts & Crafts","value":"FALSE","status":"1","id":"1","image":"http://pickany247.com//stationery/images/main_images/60665893-art-stationery.jpg"},{"Module":"stationery","category_name":"Desk Organizers","value":"FALSE","status":"1","id":"2","image":"http://pickany247.com//stationery/images/main_images/26818847-desk-stationery.jpg"},{"Module":"stationery","category_name":"Office Stationery","value":"FALSE","status":"1","id":"3","image":"http://pickany247.com//stationery/images/main_images/79678344-office-stationery.jpg"},{"Module":"stationery","category_name":"School & College","value":"FALSE","status":"1","id":"4","image":"http://pickany247.com//stationery/images/main_images/39801025-school-and-collage-stationery.jpg"},{"Module":"stationery","category_name":"Extra Stationery","value":"FALSE","status":"1","id":"5","image":"http://pickany247.com//stationery/images/main_images/22860717-extra-stationery.jpg"}]
     * Banners : [{"id":"24","category":"adds","image_type":"slider","image":"image/adds/416794361_ad.jpg","imagebelongsto":"web","position":"1","title":"","status":"1"},{"id":"25","category":"adds","image_type":"slider","image":"image/adds/408290360_ad4.jpg","imagebelongsto":"web","position":"1","title":"","status":"1"},{"id":"27","category":"adds","image_type":"slider","image":"image/adds/533348898_ad3.jpg","imagebelongsto":"web","position":"0","title":"","status":"1"}]
     * message : Get All Categories and Banners
     */

    private String message;
    private List<CategoriesBean> Categories;
    private List<BannersBean> Banners;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoriesBean> getCategories() {
        return Categories;
    }

    public void setCategories(List<CategoriesBean> Categories) {
        this.Categories = Categories;
    }

    public List<BannersBean> getBanners() {
        return Banners;
    }

    public void setBanners(List<BannersBean> Banners) {
        this.Banners = Banners;
    }

    public static class CategoriesBean {
        /**
         * Module : stationery
         * category_name : Arts & Crafts
         * value : FALSE
         * status : 1
         * id : 1
         * image : http://pickany247.com//stationery/images/main_images/60665893-art-stationery.jpg
         */

        private String Module;
        private String category_name;
        private String value;
        private String status;
        private String id;
        private String image;

        public String getModule() {
            return Module;
        }

        public void setModule(String Module) {
            this.Module = Module;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class BannersBean {
        /**
         * id : 24
         * category : adds
         * image_type : slider
         * image : image/adds/416794361_ad.jpg
         * imagebelongsto : web
         * position : 1
         * title :
         * status : 1
         */

        private String id;
        private String category;
        private String image_type;
        private String image;
        private String imagebelongsto;
        private String position;
        private String title;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getImage_type() {
            return image_type;
        }

        public void setImage_type(String image_type) {
            this.image_type = image_type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImagebelongsto() {
            return imagebelongsto;
        }

        public void setImagebelongsto(String imagebelongsto) {
            this.imagebelongsto = imagebelongsto;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
