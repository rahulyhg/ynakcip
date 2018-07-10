package com.prism.pickany247.Response;

import java.util.List;

public class StationeryHomeResponse {


    /**
     * MainCategories : [{"Module":"stationery","category_name":"Art & Crafts Stationery","value":"FALSE","status":"1","id":"1"},{"Module":"stationery","category_name":"Desk Organizers","value":"FALSE","status":"1","id":"2"},{"Module":"stationery","category_name":"Office Stationery","value":"FALSE","status":"1","id":"3"},{"Module":"stationery","category_name":"School & College stationery","value":"FALSE","status":"1","id":"4"},{"Module":"stationery","category_name":"Extra Stationery","value":"FALSE","status":"1","id":"5"}]
     * message : Get All Main Categories
     */

    private String message;
    private List<MainCategoriesBean> MainCategories;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MainCategoriesBean> getMainCategories() {
        return MainCategories;
    }

    public void setMainCategories(List<MainCategoriesBean> MainCategories) {
        this.MainCategories = MainCategories;
    }

    public static class MainCategoriesBean {
        /**
         * Module : stationery
         * category_name : Art & Crafts Stationery
         * value : FALSE
         * status : 1
         * id : 1
         */

        private String Module;
        private String category_name;
        private String value;
        private String status;
        private String id;

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
    }
}
