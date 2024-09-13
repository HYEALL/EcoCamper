package com.example.EcoCamper.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.EcoCamper.entity.Map;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component // 스프링이 이 클래스를 빈으로 관리하도록 설정
public class PlacefilterSpecification implements Specification<Map> {

	public static Specification<Map> filterByRegionCategoryFacilityEnvironmentSeasonKeyword(List<String> regions,
			List<String> categories, List<String> facilities, List<String> environments, List<String> seasons,
			String keyword) {

		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// 지역 필터 (place_address와 비교, 포함 여부를 기준으로)
			if (regions != null && !regions.isEmpty()) {
				List<Predicate> regionPredicates = new ArrayList<>();
				for (String region : regions) {
					regionPredicates.add(criteriaBuilder.like(root.get("place_address"), "%" + region + "%"));
				}
				predicates.add(criteriaBuilder.or(regionPredicates.toArray(new Predicate[0])));
			}

			// 키워드 필터 (place_name과 비교)
			if (keyword != null && !keyword.isEmpty()) {
				predicates.add(criteriaBuilder.like(root.get("place_name"), "%" + keyword + "%"));
			}

			// 카테고리 필터 (place_category와 비교)
			if (categories != null && !categories.isEmpty()) {
				predicates.add(root.get("place_category").in(categories));
			}

			// 편의시설 필터 (place_facility와 비교)
			if (facilities != null && !facilities.isEmpty()) {
				for (String facility : facilities) {
					predicates.add(criteriaBuilder.like(root.get("place_facility"), "%" + facility + "%"));
				}
			}

			// 주변환경 필터 (place_environment와 비교)
			if (environments != null && !environments.isEmpty()) {
				for (String environment : environments) {
					predicates.add(criteriaBuilder.like(root.get("place_environment"), "%" + environment + "%"));
				}
			}

			// 계절 필터 (place_season과 비교)
			if (seasons != null && !seasons.isEmpty()) {
				for (String season : seasons) {
					predicates.add(criteriaBuilder.like(root.get("place_season"), "%" + season + "%"));
				}
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public Predicate toPredicate(Root<Map> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		return null;
	}
}
