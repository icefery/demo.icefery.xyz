package xyz.icefery.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.icefery.demo.entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {}
