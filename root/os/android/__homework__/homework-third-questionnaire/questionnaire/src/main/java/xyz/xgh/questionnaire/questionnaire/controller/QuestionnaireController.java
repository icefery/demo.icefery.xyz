package xyz.xgh.questionnaire.questionnaire.controller;

import java.util.List;
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
import xyz.xgh.questionnaire.questionnaire.entity.Questionnaire;
import xyz.xgh.questionnaire.questionnaire.service.QuestionnaireService;
import xyz.xgh.questionnaire.questionnaire.util.R;

@Validated
@RestController
@RequestMapping("/{tenantId}/questionnaire")
public class QuestionnaireController extends BaseController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @ModelAttribute
    public void preHandler(@PathVariable String tenantId) {
        ContextHolder.setCurrentTenantId(tenantId);
    }

    @GetMapping("/list")
    public R<List<Questionnaire>> list(@PathVariable String tenantId) {
        if (!validateCurrentTenant(tenantId)) {
            return R.failure(R.Code.PERMISSION_DENIED);
        }
        List<Questionnaire> data = questionnaireService.listQuestionnaire();
        return R.success(data);
    }

    @GetMapping("/find/id/{questionnaireId}")
    public R<Questionnaire> find(@PathVariable String questionnaireId) {
        Questionnaire data = questionnaireService.findQuestionnaire(questionnaireId);
        return R.success(data);
    }

    @PostMapping("/create")
    public R<String> create(@PathVariable String tenantId, @RequestBody Questionnaire questionnaire) {
        String id = questionnaireService.createQuestionnaire(questionnaire);
        return R.success(id);
    }
}
