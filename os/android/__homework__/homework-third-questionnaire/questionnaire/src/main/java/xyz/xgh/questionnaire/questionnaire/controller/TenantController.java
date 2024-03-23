package xyz.xgh.questionnaire.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xgh.questionnaire.questionnaire.entity.Tenant;
import xyz.xgh.questionnaire.questionnaire.service.TenantService;
import xyz.xgh.questionnaire.questionnaire.util.R;

@Validated
@RestController
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @PostMapping("/register")
    public R<String> register(@RequestBody Tenant tenant) {
        String id = tenantService.registerTenant(tenant);
        return R.success(id);
    }
}
