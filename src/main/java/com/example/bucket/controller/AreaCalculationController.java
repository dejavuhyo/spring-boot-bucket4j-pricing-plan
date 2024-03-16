package com.example.bucket.controller;

import com.example.bucket.dto.AreaV1;
import com.example.bucket.dto.RectangleDimensionsV1;
import com.example.bucket.service.PricingPlanService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/area", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AreaCalculationController {

    private final PricingPlanService pricingPlanService;

    @PostMapping(value = "/rectangle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AreaV1> rectangle(@RequestHeader(value = "X-api-key") String apiKey,
                                            @RequestBody RectangleDimensionsV1 dimensions) {

        Bucket bucket = pricingPlanService.resolveBucket(apiKey);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return ResponseEntity.ok()
                    .header("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()))
                    .body(new AreaV1("rectangle", dimensions.getLength() * dimensions.getWidth()));
        }

        long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill))
                .build();
    }
}
