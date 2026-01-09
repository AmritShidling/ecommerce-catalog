package com.zenith.order.order_service.DTO;

import java.util.List;

public record OrderRequest (Long orderId, List<OrderLineItemDto> orderLineItems){
}
