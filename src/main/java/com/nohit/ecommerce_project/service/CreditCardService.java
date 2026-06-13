package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu CreditCard.
 */
public interface CreditCardService {
    public List<CreditCard> getDsCreditCard();

    public CreditCard getCreditCard(int id);

    public CreditCard saveCreditCard(CreditCard creditCard);

    public void deleteCreditCard(int id);

    public CreditCard createCreditCard(KhachHang khachHang);
}
