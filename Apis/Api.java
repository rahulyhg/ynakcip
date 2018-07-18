package com.prism.pickany247.Apis;

public class Api {

    public final static String BASE_URL ="https://pickany247.com/APIS/index.php/Api/";

    public final static String LOGIN_URL =BASE_URL+"user_login";

    public final static String REGISTRATION_URL =BASE_URL+"new_user";

    public final static String HOME_URL =BASE_URL+"h_banners_modules";



    // cart api
    public final static String ADD_TO_CART_URL =BASE_URL+"add_to_cart";

    public final static String CART_URL =BASE_URL+"cart_items?user_id=";

    public final static String REMOVE_CART_URL =BASE_URL+"cart_items/";

    public final static String QUANTITY_CART_URL =BASE_URL+"cart_items";

    // wishlist api
    public final static String ADD_WISHLIST_URL =BASE_URL+"wishList";

    public final static String DELETE_WISHLIST_URL =BASE_URL+"wishList_items/";

    // Address
    public final static String ADD_ADDRESS_URL =BASE_URL+"user_address";

    public final static String ADDRESS_LIST_URL =BASE_URL+"user_address?user_id=";

    // checkout
    public final static String CHECKOUT_URL =BASE_URL+"checkout";



    // Stationery url

    public final static String STATIONERY_HOME_URL =BASE_URL+"banners_categories?module=stationery";

    public final static String STATIONERY_URL =BASE_URL+"products?module=stationery&limit=4&maincatId=";

    public final static String PRODUCTS_URL =BASE_URL+"products?module=stationery&maincatId=";

    public final static String PRODUCT_DETAILS_URL =BASE_URL+"products?module=stationery&productId=";

    public final static String STATIONERY_SUB_CATEGORIES_URL =BASE_URL+"fsubcat?catId=";

    public final static String STATIONERY_FILTER_URL =BASE_URL+"filters";


    // mobile url

    public final static String MOBILE_HOME_URL =BASE_URL+"maincategories?module=mobile";

}
