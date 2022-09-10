package com.practice.esmp_demo.controller.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Search {
    @NotEmpty
    @Length(min = 4, max = 4, message = "分公司代號不足四碼")
    private String branchNo;
    @NotEmpty
    @Length(min = 2, max = 2, message = "客戶代號不足兩碼")
    private String custSeq;

    private String stock;
}
