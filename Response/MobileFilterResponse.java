package com.prism.pickany247.Response;

import java.util.List;

// FIXME generate failure  field _$OperatingSystem200
// FIXME generate failure  field _$OperatingSystem311
public class MobileFilterResponse {


    /**
     * Module : ["Mobiles"]
     * Sub_Category : [{"sub_category_id":"1","sub_category_name":"New"}]
     * Operating_System : [{"id":"1","name":"Andriod"}]
     * Brands : [{"id":"7","name":"LYF"},{"id":"15","name":"LG"}]
     * Colors : [{"id":"1","name":"Gold"},{"id":"4","name":"Grey"},{"id":"2","name":"Black"},{"id":"3","name":"Blue"},{"id":"7","name":"Silver"},{"id":"8","name":"White"}]
     * Ram : [{"id":"10","name":"16 GB"}]
     * Storage : [{"id":"5","name":"4 GB"},{"id":"13","name":"3 GB"}]
     * Rating : [{"Rating1":"0","Rating2":"0","Rating3":"2","Rating4":"1","Rating5":"0"}]
     * message : Get All Filters
     */

    private String message;
    private List<String> Module;
    private List<SubCategoryBean> Sub_Category;
    private List<OperatingSystemBean> Operating_System;
    private List<BrandsBean> Brands;
    private List<ColorsBean> Colors;
    private List<RamBean> Ram;
    private List<StorageBean> Storage;
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

    public List<OperatingSystemBean> getOperating_System() {
        return Operating_System;
    }

    public void setOperating_System(List<OperatingSystemBean> Operating_System) {
        this.Operating_System = Operating_System;
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

    public List<RamBean> getRam() {
        return Ram;
    }

    public void setRam(List<RamBean> Ram) {
        this.Ram = Ram;
    }

    public List<StorageBean> getStorage() {
        return Storage;
    }

    public void setStorage(List<StorageBean> Storage) {
        this.Storage = Storage;
    }

    public List<RatingBean> getRating() {
        return Rating;
    }

    public void setRating(List<RatingBean> Rating) {
        this.Rating = Rating;
    }

    public static class SubCategoryBean {
        /**
         * sub_category_id : 1
         * sub_category_name : New
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

    public static class OperatingSystemBean {
        /**
         * id : 1
         * name : Andriod
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

    public static class BrandsBean {
        /**
         * id : 7
         * name : LYF
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

    public static class RamBean {
        /**
         * id : 10
         * name : 16 GB
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

    public static class StorageBean {
        /**
         * id : 5
         * name : 4 GB
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
         * Rating3 : 2
         * Rating4 : 1
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
