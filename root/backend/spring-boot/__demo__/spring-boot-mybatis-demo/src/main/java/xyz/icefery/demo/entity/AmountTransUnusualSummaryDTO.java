package xyz.icefery.demo.entity;

import java.util.List;
import lombok.Data;

@Data
public class AmountTransUnusualSummaryDTO {

    private String periodName;

    private List<String> companyCodeList;

    private String companyName;

    private String dealTime;

    private String accountNo;

    private String accountType;

    private String accountName;

    private String accountCooperationBank;

    private String accountOpenBank;

    private String transferredAmount3;

    private String revenueAmount;

    private String colRate;

    private String reciprocalAcctName;

    private String reciprocalAcctCompany;
}
