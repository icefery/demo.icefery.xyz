package xyz.xgh.questionnaire.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import xyz.xgh.questionnaire.questionnaire.entity.Tenant;
import xyz.xgh.questionnaire.questionnaire.service.TenantService;

@RestController
public class BaseController {

    @Autowired
    private TenantService tenantService;

    protected Boolean validateCurrentTenant(String tenantId) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tenant find = tenantService.findTenantByUsername(username);
        return tenantId.equals(find.getId());
    }
}
