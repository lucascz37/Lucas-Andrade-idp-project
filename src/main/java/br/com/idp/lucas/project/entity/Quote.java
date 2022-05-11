package br.com.idp.lucas.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    private UUID id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stock stock;

}
