package com.prism.pickany247.Response;

import java.util.List;

public class StationerySubCatResponse {


    /**
     * fSubCatList : [{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"66","sub_category_name":"Fabric Colors"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"57","sub_category_name":"Crayons"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"64","sub_category_name":"Stencils"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"55","sub_category_name":"Sketch Pens"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"62","sub_category_name":"Scissors"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"75","sub_category_name":"Scrap Books"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"60","sub_category_name":"Drawing books"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"67","sub_category_name":"Brown Tape"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"58","sub_category_name":"Paint Brushes"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"65","sub_category_name":"Art glues"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"56","sub_category_name":"Poster Paints"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"63","sub_category_name":"Glitter pens"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"54","sub_category_name":"Colors/ Paints"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"61","sub_category_name":"Color papers"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"68","sub_category_name":"Craft Papers"},{"main_category_id":"1","category_name":"Art & Craft","sub_category_id":"59","sub_category_name":"Palettes"},{"main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"2","sub_category_name":"Table Calenders"},{"main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"7","sub_category_name":"Business Card Holders"},{"main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"5","sub_category_name":"Paper Trays"},{"main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"3","sub_category_name":"Magazine Racks"},{"main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"1","sub_category_name":"Pen stands"},{"main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"6","sub_category_name":"Cutters & Knives"},{"main_category_id":"2","category_name":"Desk Organizers","sub_category_id":"4","sub_category_name":"Paper Weight"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"27","sub_category_name":"File Storages"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"46","sub_category_name":"Planner Refillers"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"34","sub_category_name":"Punching machines"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"25","sub_category_name":"Document Envelopes"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"44","sub_category_name":"Note Pads"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"32","sub_category_name":"Cheque Book Holders"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"42","sub_category_name":"Paper clips"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"30","sub_category_name":"Stickey notes"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"49","sub_category_name":"Clip Files"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"40","sub_category_name":"ID card pulleys"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"28","sub_category_name":"Paper Shredders"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"47","sub_category_name":"Calculators (Business & Financial)"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"35","sub_category_name":"CD Cases"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"26","sub_category_name":"Box Files"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"45","sub_category_name":"Conference files"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"33","sub_category_name":"Stapler,Stapler pins"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"24","sub_category_name":"File Folders"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"43","sub_category_name":"White Boards"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"31","sub_category_name":"Planners"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"101","sub_category_name":"Push Pins"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"41","sub_category_name":"Binder Clips"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"29","sub_category_name":"Memo note books"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"48","sub_category_name":"Document Sleeves"},{"main_category_id":"3","category_name":"Office Stationery","sub_category_id":"37","sub_category_name":"Paper  pins"}]
     * message : Get Filtered SubCategories
     */

    private String message;
    private List<FSubCatListBean> fSubCatList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FSubCatListBean> getFSubCatList() {
        return fSubCatList;
    }

    public void setFSubCatList(List<FSubCatListBean> fSubCatList) {
        this.fSubCatList = fSubCatList;
    }

    public static class FSubCatListBean {
        /**
         * main_category_id : 1
         * category_name : Art & Craft
         * sub_category_id : 66
         * sub_category_name : Fabric Colors
         */

        private String main_category_id;
        private String category_name;
        private String sub_category_id;
        private String sub_category_name;

        public String getMain_category_id() {
            return main_category_id;
        }

        public void setMain_category_id(String main_category_id) {
            this.main_category_id = main_category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

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
}
