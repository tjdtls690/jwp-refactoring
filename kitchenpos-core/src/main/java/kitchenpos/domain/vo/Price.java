package kitchenpos.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {
    @Column(nullable = false)
    private BigDecimal price;

    public Price() {
    }

    public Price(final BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("[ERROR] 가격은 null이거나 0보다 작을 수 없습니다. input price : " + price);
        }
    }

    public int compareTo(final BigDecimal sum) {
        return price.compareTo(sum);
    }

    public BigDecimal multiply(final BigDecimal other) {
        return this.price.multiply(other);
    }

    public BigDecimal price() {
        return price;
    }
}
