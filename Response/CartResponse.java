package com.prism.pickany247.Response;

import java.util.List;

public class CartResponse {


    /**
     * cart_items : [{"module":"stationery","id":"4666","item_id":"79","product_name":"Art Glue","image":"http://pickany247.com/stationery/stationery/../images/product_images/PIK202IX3/1.jpg","user_id":"23","request_code":"","category":"stationery","cart_type":"single","product_id":"PIK202IX3","quantity":"1","availability":"70","unit_price_incl_tax":"789.00","unit_price_exld_tax":"668.64","tax_rate":"18.00","tax_amt":"120.36","discount":"0.00","total_price":"789.00","storage":"","ram":"","color":"White","eggless":"","eggless_amt":"","heart_shape":"","heart_shape_amt":"","flavour":"","message":"","screen":"","processor":"","camera":"","battery":"","features":""},{"module":"grocery","id":"4665","item_id":"172","product_name":"Horse Gram (Ulavalu)","image":"http://pickany247.com/grocery/grocery/../../image/grocery/product_images/PIKGR40535/Horse-Gram-(Ulavalu).jpg","user_id":"23","request_code":"","category":"grocery","cart_type":"single","product_id":"172","quantity":"1","availability":"20","unit_price_incl_tax":"50.40","unit_price_exld_tax":"48.00","tax_rate":"5.00","tax_amt":"2.40","discount":"0.00","total_price":"50.40","storage":"","ram":"","color":"","eggless":"","eggless_amt":"","heart_shape":"","heart_shape_amt":"","flavour":"","message":"","screen":"","processor":"","camera":"","battery":"","features":""},{"module":"mobiles","id":"4729","item_id":"15","product_name":"LG G4 Dual","image":"http://pickany247.com/mobile/mobile/../../image/mobiles/product_images/PIKM128879/lg-g4dual-gray-back.jpg","user_id":"23","request_code":"","category":"mobiles","cart_type":"single","product_id":"15","quantity":"1","availability":"13","unit_price_incl_tax":"10080.00","unit_price_exld_tax":"9000.00","tax_rate":"12.00","tax_amt":"1080.00","discount":"0.00","total_price":"10080.00","storage":"3 GB","ram":"32 GB","color":"Grey","eggless":"","eggless_amt":"","heart_shape":"","heart_shape_amt":"","flavour":"","message":"","screen":"5.5 - 5.9 inch","processor":"Hexa Core","camera":"16 - 20.9 MP","battery":"3000-3999mAh","features":"<p>Sim Type: Dual Sim<\/p>\r\n\r\n<p>Touch Type: Yes<\/p>\r\n"}]
     * total_bill : [{"priceInclTax":"21439.40","GST":"2349.88","SubTotal":"21439.40","DeliveryCharges":"0","grandTotal":"21439.40"}]
     * user_address : [{"id":"58","user_id":"23","name":"Vizagkumar","city":"vizag","state":"andrapradesh","zip":"532457","phone":"8985018103","address":"vizag","set_as_default":"0","status":"1"},{"id":"52","user_id":"23","name":"mahesh","city":"vizag","state":"apup","zip":"532457","phone":"8790299482","address":"mvp","set_as_default":"1","status":"1"},{"id":"51","user_id":"23","name":"sureshkumar","city":"vizag","state":"andrapradesh","zip":"532457","phone":"8790299482","address":"ishukatota","set_as_default":"1","status":"1"},{"id":"56","user_id":"23","name":"Test123","city":"vizag","state":"andrapradesh","zip":"532457","phone":"8985018103","address":"vizag","set_as_default":"0","status":"1"},{"id":"57","user_id":"23","name":"santhosh","city":"vizag","state":"ap","zip":"432457","phone":"1234567890","address":"vizag","set_as_default":"0","status":"1"}]
     * message : List of items in cart
     */

    private String message;
    private List<CartItemsBean> cart_items;
    private List<TotalBillBean> total_bill;
    private List<UserAddressBean> user_address;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CartItemsBean> getCart_items() {
        return cart_items;
    }

    public void setCart_items(List<CartItemsBean> cart_items) {
        this.cart_items = cart_items;
    }

    public List<TotalBillBean> getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(List<TotalBillBean> total_bill) {
        this.total_bill = total_bill;
    }

    public List<UserAddressBean> getUser_address() {
        return user_address;
    }

    public void setUser_address(List<UserAddressBean> user_address) {
        this.user_address = user_address;
    }

    public static class CartItemsBean {
        /**
         * module : stationery
         * id : 4666
         * item_id : 79
         * product_name : Art Glue
         * image : http://pickany247.com/stationery/stationery/../images/product_images/PIK202IX3/1.jpg
         * user_id : 23
         * request_code :
         * category : stationery
         * cart_type : single
         * product_id : PIK202IX3
         * quantity : 1
         * availability : 70
         * unit_price_incl_tax : 789.00
         * unit_price_exld_tax : 668.64
         * tax_rate : 18.00
         * tax_amt : 120.36
         * discount : 0.00
         * total_price : 789.00
         * storage :
         * ram :
         * color : White
         * eggless :
         * eggless_amt :
         * heart_shape :
         * heart_shape_amt :
         * flavour :
         * message :
         * screen :
         * processor :
         * camera :
         * battery :
         * features :
         */

        private String module;
        private String id;
        private String item_id;
        private String product_name;
        private String image;
        private String user_id;
        private String request_code;
        private String category;
        private String cart_type;
        private String product_id;
        private String quantity;
        private String availability;
        private String unit_price_incl_tax;
        private String unit_price_exld_tax;
        private String tax_rate;
        private String tax_amt;
        private String discount;
        private String total_price;
        private String storage;
        private String ram;
        private String color;
        private String eggless;
        private String eggless_amt;
        private String heart_shape;
        private String heart_shape_amt;
        private String flavour;
        private String message;
        private String screen;
        private String processor;
        private String camera;
        private String battery;
        private String features;

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRequest_code() {
            return request_code;
        }

        public void setRequest_code(String request_code) {
            this.request_code = request_code;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCart_type() {
            return cart_type;
        }

        public void setCart_type(String cart_type) {
            this.cart_type = cart_type;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getUnit_price_incl_tax() {
            return unit_price_incl_tax;
        }

        public void setUnit_price_incl_tax(String unit_price_incl_tax) {
            this.unit_price_incl_tax = unit_price_incl_tax;
        }

        public String getUnit_price_exld_tax() {
            return unit_price_exld_tax;
        }

        public void setUnit_price_exld_tax(String unit_price_exld_tax) {
            this.unit_price_exld_tax = unit_price_exld_tax;
        }

        public String getTax_rate() {
            return tax_rate;
        }

        public void setTax_rate(String tax_rate) {
            this.tax_rate = tax_rate;
        }

        public String getTax_amt() {
            return tax_amt;
        }

        public void setTax_amt(String tax_amt) {
            this.tax_amt = tax_amt;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
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

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
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

        public String getHeart_shape() {
            return heart_shape;
        }

        public void setHeart_shape(String heart_shape) {
            this.heart_shape = heart_shape;
        }

        public String getHeart_shape_amt() {
            return heart_shape_amt;
        }

        public void setHeart_shape_amt(String heart_shape_amt) {
            this.heart_shape_amt = heart_shape_amt;
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

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }
    }

    public static class TotalBillBean {
        /**
         * priceInclTax : 21439.40
         * GST : 2349.88
         * SubTotal : 21439.40
         * DeliveryCharges : 0
         * grandTotal : 21439.40
         */

        private String priceInclTax;
        private String GST;
        private String SubTotal;
        private String DeliveryCharges;
        private String grandTotal;

        public String getPriceInclTax() {
            return priceInclTax;
        }

        public void setPriceInclTax(String priceInclTax) {
            this.priceInclTax = priceInclTax;
        }

        public String getGST() {
            return GST;
        }

        public void setGST(String GST) {
            this.GST = GST;
        }

        public String getSubTotal() {
            return SubTotal;
        }

        public void setSubTotal(String SubTotal) {
            this.SubTotal = SubTotal;
        }

        public String getDeliveryCharges() {
            return DeliveryCharges;
        }

        public void setDeliveryCharges(String DeliveryCharges) {
            this.DeliveryCharges = DeliveryCharges;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }
    }

    public static class UserAddressBean {
        /**
         * id : 58
         * user_id : 23
         * name : Vizagkumar
         * city : vizag
         * state : andrapradesh
         * zip : 532457
         * phone : 8985018103
         * address : vizag
         * set_as_default : 0
         * status : 1
         */

        private String id;
        private String user_id;
        private String name;
        private String city;
        private String state;
        private String zip;
        private String phone;
        private String address;
        private String set_as_default;
        private String status;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSet_as_default() {
            return set_as_default;
        }

        public void setSet_as_default(String set_as_default) {
            this.set_as_default = set_as_default;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
