package com.prism.pickany247.Response;

import java.util.List;

public class HomeResponse {


    /**
     * Modules : [{"id":"5","image":"http://pickany247.com/image/home/910794-homekitchen_bg.jpg","category":"home","image_type":"banner","position":"2","status":"1","title":"Home Kitchen"},{"id":"6","image":"http://pickany247.com/image/home/53730790-mobiles_bg.jpg","category":"home","image_type":"banner","position":"1","status":"1","title":"Mobiles"},{"id":"7","image":"http://pickany247.com/image/home/54436983-car_&_bike_bg.jpg","category":"home","image_type":"banner","position":"0","status":"1","title":"Car & Bike Rentals"},{"id":"8","image":"http://pickany247.com/image/home/63411102-celebrations_bg.jpg","category":"home","image_type":"banner","position":"3","status":"1","title":"Celebrations"},{"id":"9","image":"http://pickany247.com/image/home/49380152-stationery_bg.jpg","category":"home","image_type":"banner","position":"4","status":"1","title":"Stationery"}]
     * message : Get All Modules
     */

    private String message;
    private List<ModulesBean> Modules;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModulesBean> getModules() {
        return Modules;
    }

    public void setModules(List<ModulesBean> Modules) {
        this.Modules = Modules;
    }

    public static class ModulesBean {
        /**
         * id : 5
         * image : http://pickany247.com/image/home/910794-homekitchen_bg.jpg
         * category : home
         * image_type : banner
         * position : 2
         * status : 1
         * title : Home Kitchen
         */

        private String id;
        private String image;
        private String category;
        private String image_type;
        private String position;
        private String status;
        private String title;

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

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
