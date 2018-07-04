package com.prism.pickany247.Interface;

import com.prism.pickany247.Response.Product;

public interface CartDataSet {

    int size();

    Product get(int position);

    long getId(int position);

}
