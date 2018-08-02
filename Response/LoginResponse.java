package com.prism.pickany247.Response;

import java.util.List;

public class LoginResponse {


    /**
     * userDetails : [{"id":"23","username":"sudha","email":"sudha@gmail.com","mobile":"8790299482","password":"8790299482"}]
     * message : Login Successfull
     */

    private String message;
    private List<UserDetailsBean> userDetails;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserDetailsBean> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<UserDetailsBean> userDetails) {
        this.userDetails = userDetails;
    }

    public static class UserDetailsBean {
        /**
         * id : 23
         * username : sudha
         * email : sudha@gmail.com
         * mobile : 8790299482
         * password : 8790299482
         */

        private String id;
        private String username;
        private String email;
        private String mobile;
        private String password;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
