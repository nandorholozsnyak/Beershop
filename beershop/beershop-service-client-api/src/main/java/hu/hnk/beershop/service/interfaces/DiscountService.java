package hu.hnk.beershop.service.interfaces;

import java.time.LocalDate;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.service.tools.DiscountType;

/**
 * @author Nandi
 *
 */
public interface DiscountService {
	public void validateDiscount(DiscountType discountType, Cargo cargo, LocalDate today);
}
