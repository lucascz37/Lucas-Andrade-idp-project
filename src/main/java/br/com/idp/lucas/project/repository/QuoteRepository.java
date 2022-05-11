package br.com.idp.lucas.project.repository;

import br.com.idp.lucas.project.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, UUID> {

    public List<Quote> findByStockName(String name);
}
