package com.example.bucket.service;

import com.example.bucket.dto.CapacityDto;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;

public enum PricingPlan {
    FREE(20),

    BASIC(40),

    PROFESSIONAL(100);

    private int bucketCapacity;

    private PricingPlan(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }

    Bandwidth getLimit() {
        return Bandwidth.classic(bucketCapacity, Refill.intervally(bucketCapacity, Duration.ofHours(1)));
    }

    public int bucketCapacity() {
        return bucketCapacity;
    }

    static PricingPlan resolvePlanFromApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return FREE;

        } else if (apiKey.startsWith("PX001-")) {
            return PROFESSIONAL;

        } else if (apiKey.startsWith("BX001-")) {
            return BASIC;
        }
        return FREE;
    }

//    FREE {
//        public Bandwidth getLimit(CapacityDto capacityDto) {
//            int capacity = capacityDto.getFree();
//            return Bandwidth.classic(capacity, Refill.intervally(capacity, Duration.ofHours(1)));
//        }
//    },
//    BASIC {
//        public Bandwidth getLimit(CapacityDto capacityDto) {
//            int capacity = capacityDto.getBasic();
//            return Bandwidth.classic(capacity, Refill.intervally(capacity, Duration.ofHours(1)));
//        }
//    },
//    PROFESSIONAL {
//        public Bandwidth getLimit(CapacityDto capacityDto) {
//            int capacity = capacityDto.getProfessional();
//            return Bandwidth.classic(capacity, Refill.intervally(capacity, Duration.ofHours(1)));
//        }
//    };
//
//    public abstract Bandwidth getLimit(CapacityDto capacityDto);
//
//    public static PricingPlan resolvePlanFromApiKey(String apiKey) {
//        if (apiKey == null || apiKey.isEmpty()) {
//            return FREE;
//        } else if (apiKey.startsWith("BA-")) {
//            return BASIC;
//        } else if (apiKey.startsWith("PX-")) {
//            return PROFESSIONAL;
//        }
//        return FREE;
//    }

}