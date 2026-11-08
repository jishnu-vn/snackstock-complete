package com.srm.inventory.dto;

import com.srm.inventory.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(@NotNull OrderStatus status) { }
