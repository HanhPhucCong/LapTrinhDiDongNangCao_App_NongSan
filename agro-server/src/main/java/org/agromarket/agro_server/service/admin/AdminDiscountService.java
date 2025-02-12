package org.agromarket.agro_server.service.admin;

import java.util.List;
import org.agromarket.agro_server.model.entity.Discount;
import org.agromarket.agro_server.repositories.admin.AdminDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminDiscountService {

    @Autowired
    private AdminDiscountRepository discountRepository;

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public Discount getDiscountById(Long id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Discount not found with ID: " + id));
    }

    @Transactional
    public Discount createDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    @Transactional
    public Discount updateDiscount(Long id, Discount discountDetails) {
        Discount discount = getDiscountById(id);
        discount.setCode(discountDetails.getCode());
        discount.setDiscountPercentage(discountDetails.getDiscountPercentage());
        discount.setStartDate(discountDetails.getStartDate());
        discount.setEndDate(discountDetails.getEndDate());
        return discountRepository.save(discount);
    }

    @Transactional
    public void deleteDiscount(Long id) {
        Discount discount = getDiscountById(id);
        discountRepository.delete(discount);
    }
}