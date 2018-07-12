package com.prism.pickany247.Response;

import java.util.List;

public class HomeResponse {


    /**
     * Home_Banners : [{"id":"3","image":"http://pickany247.com/image/home/73684282-services-slider.jpg","category":"home","image_type":"banner"},{"id":"19","image":"http://pickany247.com/image/home/865731070_car-and-bike-banner.jpg","category":"home","image_type":"banner"},{"id":"18","image":"http://pickany247.com/image/home/23621422-mobile2.jpg","category":"home","image_type":"banner"},{"id":"17","image":"http://pickany247.com/image/home/43372206-seasonings.jpg","category":"home","image_type":"banner"}]
     * Modules : [{"id":"5","image":"http://pickany247.com/image/home/910794-homekitchen_bg.jpg","category":"home","image_type":"banner","position":"2","title":"Home Kitchen"},{"id":"6","image":"http://pickany247.com/image/home/53730790-mobiles_bg.jpg","category":"home","image_type":"banner","position":"1","title":"Mobiles"},{"id":"7","image":"http://pickany247.com/image/home/54436983-car_&_bike_bg.jpg","category":"home","image_type":"banner","position":"0","title":"Car & Bike Rentals"},{"id":"8","image":"http://pickany247.com/image/home/63411102-celebrations_bg.jpg","category":"home","image_type":"banner","position":"3","title":"Celebrations"},{"id":"9","image":"http://pickany247.com/image/home/49380152-stationery_bg.jpg","category":"home","image_type":"banner","position":"4","title":"Stationery"},{"id":"54","image":"http://pickany247.com/image/bath_and_body/700752777_stationery_bg1.jpg","category":"home","image_type":"banner","position":"6","title":"Bath & Body"},{"id":"55","image":"http://pickany247.com/image//159744075_services_bg1.jpg","category":"home","image_type":"banner","position":"5","title":"Services"},{"id":"56","image":"http://pickany247.com/image//830245332_stationery_bg.jpg","category":"home","image_type":"banner","position":"7","title":"Hotels"},{"id":"57","image":"http://pickany247.com/image//793550134_electronic_accessories_bg.jpg","category":"home","image_type":"banner","position":"8","title":"Electronic Accessories"},{"id":"58","image":"http://pickany247.com/image//472698369_mobiles_bg.jpg","category":"home","image_type":"banner","position":"9","title":"Grocery"}]
     * message : Get All Home_Banners & Modules
     */

    private String message;
    private List<HomeBannersBean> Home_Banners;
    private List<ModulesBean> Modules;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HomeBannersBean> getHome_Banners() {
        return Home_Banners;
    }

    public void setHome_Banners(List<HomeBannersBean> Home_Banners) {
        this.Home_Banners = Home_Banners;
    }

    public List<ModulesBean> getModules() {
        return Modules;
    }

    public void setModules(List<ModulesBean> Modules) {
        this.Modules = Modules;
    }

    public static class HomeBannersBean {
        /**
         * id : 3
         * image : http://pickany247.com/image/home/73684282-services-slider.jpg
         * category : home
         * image_type : banner
         */

        private String id;
        private String image;
        private String category;
        private String image_type;

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
    }

    public static class ModulesBean {
        /**
         * id : 5
         * image : http://pickany247.com/image/home/910794-homekitchen_bg.jpg
         * category : home
         * image_type : banner
         * position : 2
         * title : Home Kitchen
         */

        private String id;
        private String image;
        private String category;
        private String image_type;
        private String position;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
