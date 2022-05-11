package br.com.idp.lucas.project.repository;

import br.com.idp.lucas.project.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    public Stock findByName(String stock);
}
