package com.prism.pickany247.Interface;

import com.prism.pickany247.Model.Product;

public interface CartProductClickListener {

    void onMinusClick(Product product);

    void onPlusClick(Product product);

    void onSaveClick(Product product);

    void onRemoveClick(Product product);
}
