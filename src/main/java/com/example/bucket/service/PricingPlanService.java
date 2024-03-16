package com.example.bucket.service;

import com.example.bucket.dto.CapacityDto;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PricingPlanService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        PricingPlan pricingPlan = PricingPlan.resolvePlanFromApiKey(apiKey);
        return bucket(pricingPlan.getLimit());
    }

//    private Bucket newBucket(String apiKey) {
//        PricingPlan pricingPlan = PricingPlan.resolvePlanFromApiKey(apiKey);
//        CapacityDto capacityDto = new CapacityDto();
//        capacityDto.setFree(20);
//        capacityDto.setBasic(40);
//        capacityDto.setProfessional(100);
//        return bucket(pricingPlan.getLimit(capacityDto));
//    }

    private Bucket bucket(Bandwidth limit) {
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
