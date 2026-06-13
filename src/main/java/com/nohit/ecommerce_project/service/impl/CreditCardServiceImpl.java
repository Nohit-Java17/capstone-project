package com.nohit.ecommerce_project.service.impl;

import lombok.*;

import java.util.*;

import javax.transaction.*;

import org.springframework.stereotype.*;

import com.nohit.ecommerce_project.model.*;
import com.nohit.ecommerce_project.repository.*;
import com.nohit.ecommerce_project.service.*;
import com.nohit.ecommerce_project.util.*;

import lombok.extern.slf4j.*;

/**
 * Cài đặt nghiệp vụ và chuẩn hóa dữ liệu cho service CreditCard.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final StringUtil stringUtil;

    @Override
    public List<CreditCard> getDsCreditCard() {
        log.info("Fetching all credit_card");
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard getCreditCard(int id) {
        log.info("Fetching credit_card with id {}", id);
        return creditCardRepository.findById(id).orElse(null);
    }

    @Override
    public CreditCard saveCreditCard(CreditCard creditCard) {
        creditCard.setNameOnCard(stringUtil.parseNameOnCard(creditCard.getNameOnCard()));
        log.info("Saving credit_card with name: {}", creditCard.getNameOnCard());
        return creditCardRepository.save(creditCard);
    }

    @Override
    public void deleteCreditCard(int id) {
        log.info("Deleting credit_card with id: {}", id);
        creditCardRepository.deleteById(id);
    }

    @Override
    public CreditCard createCreditCard(KhachHang khachHang) {
        var creditCard = new CreditCard();
        creditCard.setId(khachHang.getId());
        log.info("Create credit_card with email: {}", khachHang.getEmail());
        return creditCardRepository.save(creditCard);
    }
}
