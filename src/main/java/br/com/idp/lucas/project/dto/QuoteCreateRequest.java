package br.com.idp.lucas.project.dto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
public class QuoteCreateRequest {

    @Length(min = 4, max = 5)
    @NotBlank
    private String stockId;

    @NotEmpty
    private Map<@Pattern(regexp = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])") String, @Size(max = 255) @Pattern(regexp = "^[1-9]+[0-9]+(\\.[0-9]{2})+$") String> quotes;
}