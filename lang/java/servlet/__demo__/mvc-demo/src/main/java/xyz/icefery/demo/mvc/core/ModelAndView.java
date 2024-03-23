package xyz.icefery.demo.mvc.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Accessors(chain = true)
@NoArgsConstructor
public class ModelAndView {
    private final Map<String, Object> modelMap = new HashMap<>();

    @Getter
    @Setter
    private String viewName = "";

    @Getter
    @Setter
    private ResponseMethod responseMethod = ResponseMethod.FORWARD;

    public ModelAndView(String viewName, ResponseMethod responseMethod) {
        this.viewName = viewName;
        this.responseMethod = responseMethod;
    }

    public Set<Map.Entry<String, Object>> setOfModelMap() {
        return modelMap.entrySet();
    }

    public ModelAndView addObject(String key, Object object) {
        modelMap.put(key, object);
        return this;
    }

    public static enum ResponseMethod {
        FORWARD, REDIRECT, PLAIN
    }
}
