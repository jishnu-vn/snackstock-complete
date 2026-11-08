package com.srm.inventory.dto;

import com.srm.inventory.entity.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record OrderRequest(
    @NotBlank String customerName,
    @Email String customerEmail,
    @NotBlank @Pattern(regexp = "[0-9]{10}") String phoneNumber,
    @NotBlank String roomNumber,
    @Size(max = 50) String hostelBlock,
    @Size(max = 250) String deliveryNote,
    @NotNull PaymentMethod paymentMethod,
    @Size(max = 80) String transactionReference,
    @NotEmpty List<@Valid ItemRequest> items
) {
  public record ItemRequest(@NotNull Long productId, @NotNull @Min(1) Integer quantity) { }
}
