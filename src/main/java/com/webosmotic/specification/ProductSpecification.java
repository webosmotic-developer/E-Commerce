package com.webosmotic.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.google.common.base.Strings;
import com.webosmotic.entity.Product;
import com.webosmotic.pojo.ProductSearchCriteria;

public class ProductSpecification {

	public static Specification<Product> findBySearchCriteria(ProductSearchCriteria searchCriteria) {

		return (Specification<Product>) (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (searchCriteria != null) {
				
				if(!Strings.isNullOrEmpty(searchCriteria.getAll())) {
					Predicate namePredicate = cb.like(root.get("name"),createPattern(searchCriteria.getAll()));
					Predicate descriptionPredicate = cb.like(root.get("description"), createPattern(searchCriteria.getAll()));
					Predicate criteriaPredicate = cb.like(root.get("productCategory").get("name"), createPattern(searchCriteria.getAll()));
					Predicate subCriteriaPredicate = cb.like(root.get("productCategory").get("subCategory"), createPattern(searchCriteria.getAll()));
					Predicate brandPredicate = cb.like(root.get("brand"), createPattern(searchCriteria.getAll()));
					Predicate allPredicate = cb.or(namePredicate , descriptionPredicate , criteriaPredicate, subCriteriaPredicate , brandPredicate);
					predicates.add(allPredicate);
				}
				
				if (!Strings.isNullOrEmpty(searchCriteria.getName())) {
					predicates.add(cb.like(root.get("name"), createPattern(searchCriteria.getName())));
				}
				if (!Strings.isNullOrEmpty(searchCriteria.getDescription())) {
					predicates.add(cb.like(root.get("description"), createPattern(searchCriteria.getDescription())));
				}
				if (!Strings.isNullOrEmpty(searchCriteria.getCategory())) {
					predicates.add(cb.like(root.get("productCategory").get("name"),
							createPattern(searchCriteria.getCategory())));
				}
				if (!Strings.isNullOrEmpty(searchCriteria.getSubCategory())) {
					predicates.add(cb.like(root.get("productCategory").get("subCategory"),
							createPattern(searchCriteria.getSubCategory())));
				}
				if (!Strings.isNullOrEmpty(searchCriteria.getBrand())) {
					predicates.add(cb.like(root.get("brand").get("subCategory"),
							createPattern(searchCriteria.getBrand())));
				}
				if (searchCriteria.getDiscount() > 0) {
					predicates.add(cb.equal(root.get("discount"), searchCriteria.getDiscount()));
				}
				if (searchCriteria.getPrice() > 0) {
					predicates.add(cb.equal(root.get("price"), searchCriteria.getPrice()));
				}
				if (searchCriteria.getMinPrice() > 0 && searchCriteria.getMaxPrice() == 0) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("price"), searchCriteria.getMinPrice()));
				}

				if (searchCriteria.getMinPrice() == 0 && searchCriteria.getMaxPrice() > 0) {
					predicates.add(cb.lessThanOrEqualTo(root.get("price"), searchCriteria.getMinPrice()));
				}

				if (searchCriteria.getMinPrice() > 0 && searchCriteria.getMaxPrice() > 0) {
					predicates.add(
							cb.between(root.get("price"), searchCriteria.getMinPrice(), searchCriteria.getMaxPrice()));
				}
			}
			return cb.and(predicates.toArray(new Predicate[] {}));
		};

	}

	private static String createPattern(String s) {
		return "%" + s + "%";
	}
}
