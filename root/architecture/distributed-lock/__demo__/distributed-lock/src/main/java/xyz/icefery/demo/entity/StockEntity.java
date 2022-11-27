package xyz.icefery.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stock")
public class StockEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "warehouse_code")
    private String warehouseCode;

    @Column(name = "stock")
    private Long stock;
}

