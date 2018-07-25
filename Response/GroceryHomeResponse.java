package com.prism.pickany247.Response;

import java.util.List;

public class GroceryHomeResponse {


    /**
     * Categories : [{"Module":"grocery","category_name":"Flours & Cereals","value":"FALSE","id":"1","image":"http://pickany247.com/image/grocery/categories/21993875-flours-and-cereals.jpg"},{"Module":"grocery","category_name":"Vegetables","value":"FALSE","id":"2","image":"http://pickany247.com/image/grocery/categories/43463134-vegetables.jpg"},{"Module":"grocery","category_name":"Oil & Seasonings","value":"FALSE","id":"3","image":"http://pickany247.com/image/grocery/categories/8847045-oils-and-seasonings.jpg"},{"Module":"grocery","category_name":"Pulses","value":"FALSE","id":"4","image":"http://pickany247.com/image/grocery/categories/15798950-pulses.jpg"},{"Module":"grocery","category_name":"Dairy Products","value":"FALSE","id":"5","image":"http://pickany247.com/image/grocery/categories/61654663-dairy-products.jpg"},{"Module":"grocery","category_name":"Tea, Coffee & Milk","value":"FALSE","id":"6","image":"http://pickany247.com/image/grocery/categories/28555297-tea-coffee-milk.jpg"},{"Module":"grocery","category_name":"Beverages","value":"FALSE","id":"7","image":"http://pickany247.com/image/grocery/categories/62051391-bevarages.jpg"},{"Module":"grocery","category_name":"Bakery","value":"FALSE","id":"8","image":"http://pickany247.com/image/grocery/categories/891113-bakery.jpg"},{"Module":"grocery","category_name":"Dry fruits","value":"FALSE","id":"9","image":"http://pickany247.com/image/grocery/categories/29464721-dry-fruits.jpg"},{"Module":"grocery","category_name":"Egg & Meat","value":"FALSE","id":"10","image":"http://pickany247.com/image/grocery/categories/90625000-egg-and-meat.jpg"},{"Module":"grocery","category_name":"Snacks","value":"FALSE","id":"11","image":"http://pickany247.com/image/grocery/categories/3680419-snacks.jpg"},{"Module":"grocery","category_name":"Pickles and Crunches","value":"FALSE","id":"12","image":"http://pickany247.com/image/grocery/categories/83212280-pickles-and-crunches.jpg"},{"Module":"grocery","category_name":"Home Needs","value":"FALSE","id":"13","image":"http://pickany247.com/image/grocery/categories/85308837-home-needs.jpg"},{"Module":"grocery","category_name":"Fruits","value":"FALSE","id":"14","image":"http://pickany247.com/image/grocery/categories/67899418-fruits.jpg"},{"Module":"grocery","category_name":"Ready to Eat","value":"FALSE","id":"15","image":"http://pickany247.com/image/grocery/categories/950958251_ready-to-eat.jpg"}]
     * Banners : [{"id":"28","image":"http://pickany247.com/image/grocery/223205566_grocery-&-vegetables.jpg","category":"1","image_type":"banner"},{"id":"29","image":"http://pickany247.com/image/grocery/509033203_grocery.jpg","category":"1","image_type":"banner"}]
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
         * Module : grocery
         * category_name : Flours & Cereals
         * value : FALSE
         * id : 1
         * image : http://pickany247.com/image/grocery/categories/21993875-flours-and-cereals.jpg
         */

        private String Module;
        private String category_name;
        private String value;
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
         * id : 28
         * image : http://pickany247.com/image/grocery/223205566_grocery-&-vegetables.jpg
         * category : 1
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
}
