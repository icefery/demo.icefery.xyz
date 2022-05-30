package xyz.xgh.questionnaire.questionnaire.context;

public class ContextHolder {
    private static final ThreadLocal<String> CURRENT_TENANT_ID = new ThreadLocal<>();

    public static String getCurrentTenantId() {
        return CURRENT_TENANT_ID.get();
    }

    public static void setCurrentTenantId(String tenantId) {
        CURRENT_TENANT_ID.set(tenantId);
    }
}
