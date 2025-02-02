package kitchenpos.dto.order.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kitchenpos.domain.order.Order;
import kitchenpos.domain.order.OrderLineItem;
import kitchenpos.domain.order.OrderedMenu;
import kitchenpos.domain.order.OrderedMenuProduct;
import kitchenpos.domain.order.vo.OrderStatus;
import kitchenpos.dto.ordertable.response.OrderTableResponse;

public class OrderResponse {
    private final long id;
    private final OrderTableResponse orderTable;
    private final OrderStatus orderStatus;
    private final LocalDateTime orderedTime;
    private final List<OrderLineItemResponse> orderLineItems;

    public OrderResponse(
            final long id,
            final OrderTableResponse orderTable,
            final OrderStatus orderStatus,
            final LocalDateTime orderedTime,
            final List<OrderLineItemResponse> orderLineItems
    ) {
        this.id = id;
        this.orderTable = orderTable;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
        this.orderLineItems = orderLineItems;
    }

    public static OrderResponse of(
            final Order order,
            final List<OrderLineItem> orderLineItems,
            final List<OrderedMenu> orderedMenus,
            final List<List<OrderedMenuProduct>> orderedMenuProducts
    ) {
        return new OrderResponse(
                order.id(),
                OrderTableResponse.from(order.orderTable()),
                order.orderStatus(),
                order.orderedTime(),
                parseOrderLineItemResponses(orderLineItems, orderedMenus, orderedMenuProducts)
        );
    }

    private static List<OrderLineItemResponse> parseOrderLineItemResponses(
            final List<OrderLineItem> orderLineItems,
            final List<OrderedMenu> orderedMenus,
            final List<List<OrderedMenuProduct>> orderedMenuProducts
    ) {
        return IntStream.range(0, orderLineItems.size())
                .mapToObj(index -> OrderLineItemResponse.of(
                        orderLineItems.get(index),
                        orderedMenus.get(index),
                        orderedMenuProducts.get(index)
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public long getId() {
        return id;
    }

    public OrderTableResponse getOrderTable() {
        return orderTable;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }
}
