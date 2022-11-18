package xyz.xgh.questionnaire.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xgh.questionnaire.questionnaire.context.ContextHolder;
import xyz.xgh.questionnaire.questionnaire.entity.QuestionnaireItem;
import xyz.xgh.questionnaire.questionnaire.service.QuestionnaireItemService;
import xyz.xgh.questionnaire.questionnaire.util.R;
import java.util.List;

@Validated
@RestController
@RequestMapping("/{tenantId}/questionnaire-item")
public class QuestionnaireItemController extends BaseController {
    @Autowired
    private QuestionnaireItemService questionnaireItemService;

    @ModelAttribute
    public void preHandler(@PathVariable String tenantId) {
        ContextHolder.setCurrentTenantId(tenantId);
    }

    @PostMapping("/create")
    public R<String> create(@RequestBody QuestionnaireItem questionnaireItem) {
        String id = questionnaireItemService.createQuestionnaireItem(questionnaireItem);
        return R.success(id);
    }

    @GetMapping("/list/questionnaire/{questionnaireId}")
    public R<List<QuestionnaireItem>> list(@PathVariable String tenantId, @PathVariable String questionnaireId) {
        if (!validateCurrentTenant(tenantId)) {
            return R.failure(R.Code.PERMISSION_DENIED);
        }
        List<QuestionnaireItem> data = questionnaireItemService.listQuestionnaireItem(questionnaireId);
        return R.success(data);
    }
}
