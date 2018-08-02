package com.prism.pickany247.Response;

import java.util.List;

public class AddressResponse {


    /**
     * Modules : [{"id":"49","user_id":"9","name":"swaroopku","city":"vizag","state":"andrapradesh","zip":"532457","phone":"8121821419","address":"88899559161646","set_as_default":"0","status":"1"},{"id":"33","user_id":"9","name":"suresh","city":"vizag","state":"andrapradesh","zip":"532457","phone":"8985018103","address":"ishukatota,ramalayam street, near nadi gudi temple","set_as_default":"0","status":"1"},{"id":"36","user_id":"9","name":"maheshkumar","city":"vizag","state":"andrapradesh","zip":"530003","phone":"8919480920","address":"ramalayam Street, mvp ","set_as_default":"0","status":"1"}]
     * message : Get All Address
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
         * id : 49
         * user_id : 9
         * name : swaroopku
         * city : vizag
         * state : andrapradesh
         * zip : 532457
         * phone : 8121821419
         * address : 88899559161646
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
