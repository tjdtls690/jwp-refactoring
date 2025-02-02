package kitchenpos.domain.order;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kitchenpos.domain.order.vo.OrderStatus;
import kitchenpos.domain.ordertable.OrderTable;

@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "order_table_id", nullable = false, foreignKey = @ForeignKey(name = "fk_orders_order_table"))
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderTable orderTable;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderedTime;

    public Order() {
    }

    public Order(
            final OrderStatus orderStatus,
            final LocalDateTime orderedTime
    ) {
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
    }

    public void changeOrderStatus(final OrderStatus requestOrderStatus) {
        if (this.orderStatus == OrderStatus.COMPLETION) {
            throw new IllegalArgumentException("[ERROR] 이미 완료된 주문입니다.");
        }

        this.orderStatus = requestOrderStatus;
    }

    public void ungroupOrderTable() {
        if (this.orderStatus == OrderStatus.COOKING || this.orderStatus == OrderStatus.MEAL) {
            throw new IllegalArgumentException("[ERROR] 주문 상태가 요리중이거나 식사중일 경우 table group을 해제할 수 없습니다.");
        }

        this.orderTable.ungroup();
    }

    public void setOrderTable(final OrderTable orderTable) {
        validateOrderTable(orderTable);
        this.orderTable = orderTable;
    }

    private void validateOrderTable(final OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] order table이 비어있습니다.");
        }
    }

    public void validateHasCookingOrMealStatus() {
        if (isStatusCookingOrMeal()) {
            throw new IllegalArgumentException("[ERROR] 요리중이거나 식사중인 주문이 존재합니다.");
        }
    }

    private boolean isStatusCookingOrMeal() {
        return this.orderStatus == OrderStatus.COOKING || this.orderStatus == OrderStatus.MEAL;
    }

    public Long id() {
        return id;
    }

    public OrderTable orderTable() {
        return orderTable;
    }

    public OrderStatus orderStatus() {
        return orderStatus;
    }

    public LocalDateTime orderedTime() {
        return orderedTime;
    }
}
