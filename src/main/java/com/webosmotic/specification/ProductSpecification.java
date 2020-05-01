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
					Predicate namePredicate = cb.like(cb.lower(root.get("name")),createPattern(searchCriteria.getAll()));
					Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), createPattern(searchCriteria.getAll()));
					Predicate criteriaPredicate = cb.like(cb.lower(root.get("productCategory").get("parentCategory")), createPattern(searchCriteria.getAll()));
					Predicate subCriteriaPredicate = cb.like(cb.lower(root.get("productCategory").get("subCategory")), createPattern(searchCriteria.getAll()));
					Predicate brandPredicate = cb.like(cb.lower(root.get("brand")), createPattern(searchCriteria.getAll()));
					Predicate allPredicate = cb.or(namePredicate , descriptionPredicate , criteriaPredicate, subCriteriaPredicate , brandPredicate);
					predicates.add(allPredicate);
				}
				
				if (!Strings.isNullOrEmpty(searchCriteria.getName())) {
					predicates.add(cb.like(cb.lower(root.get("name")), createPattern(searchCriteria.getName())));
				}
				if (!Strings.isNullOrEmpty(searchCriteria.getDescription())) {
					predicates.add(cb.like(cb.lower(root.get("description")), createPattern(searchCriteria.getDescription())));
				}

				if (!Strings.isNullOrEmpty(searchCriteria.getCategory())) {
					predicates.add(cb.like(cb.lower(root.get("productCategory").get("parentCategory")),
							createPattern(searchCriteria.getCategory())));
				}
				if (!Strings.isNullOrEmpty(searchCriteria.getSubCategory())) {
					predicates.add(cb.like(cb.lower(root.get("productCategory").get("subCategory")),
							createPattern(searchCriteria.getSubCategory())));
				}

				if (!Strings.isNullOrEmpty(searchCriteria.getBrand())) {
					predicates.add(cb.like(cb.lower(root.get("brand")),                                
							createPattern(searchCriteria.getBrand())));
				}
				if (searchCriteria.getDiscount() > 0) {
					predicates.add(cb.equal(root.get("discount"), searchCriteria.getDiscount()));
				}
				if (searchCriteria.getUnitPrice() > 0) {
					predicates.add(cb.equal(root.get("unitPrice"), searchCriteria.getUnitPrice()));
				}
				if (searchCriteria.getMinPrice() > 0 && searchCriteria.getMaxPrice() == 0) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("unitPrice"), searchCriteria.getMinPrice()));
				}

				if (searchCriteria.getMinPrice() == 0 && searchCriteria.getMaxPrice() > 0) {
					predicates.add(cb.lessThanOrEqualTo(root.get("unitPrice"), searchCriteria.getMaxPrice()));
				}
				
				if (searchCriteria.getMinDiscount() > 0 && searchCriteria.getMaxDiscount() == 0) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("discount"), searchCriteria.getMinDiscount()));
				}

				if (searchCriteria.getMinDiscount() == 0 && searchCriteria.getMaxDiscount() > 0) {
					predicates.add(cb.lessThanOrEqualTo(root.get("discount"), searchCriteria.getMaxDiscount()));
				}

				if (searchCriteria.getMinPrice() > 0 && searchCriteria.getMaxPrice() > 0) {
					predicates.add(
							cb.between(root.get("unitPrice"), searchCriteria.getMinPrice(), searchCriteria.getMaxPrice()));
				}
				
				if (searchCriteria.getMinDiscount() > 0 && searchCriteria.getMaxDiscount() > 0) {
					predicates.add(
							cb.between(root.get("discount"), searchCriteria.getMinDiscount(), searchCriteria.getMaxDiscount()));
				}
			}
			return cb.and(predicates.toArray(new Predicate[] {}));
		};

	}

	private static String createPattern(String s) {
		return "%" + s.toLowerCase() + "%";
	}
}
