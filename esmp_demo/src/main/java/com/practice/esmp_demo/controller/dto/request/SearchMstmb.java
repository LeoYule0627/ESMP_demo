package com.practice.esmp_demo.controller.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Data
public class SearchMstmb {
    @NotBlank
    @Length(min = 1, max = 6, message = "股票代號一~六碼")
    private String stock;
}
