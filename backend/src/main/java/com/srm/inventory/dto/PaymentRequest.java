package com.srm.inventory.dto;

import com.srm.inventory.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(@NotNull PaymentStatus paymentStatus) { }
