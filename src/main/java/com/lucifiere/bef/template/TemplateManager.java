package com.lucifiere.bef.template;

import com.lucifiere.bef.specific.IExtensionPointsFacade;
import com.lucifiere.bef.util.ClassPathScanHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Template manager.
 *
 * @author 沾清
 */
public class TemplateManager {

    /**
     * The constant ASTERISK.
     */
    public static final String ASTERISK = "*";

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateManager.class);

    /**
     * The constant instance.
     */
    private static volatile TemplateManager instance = null;

    /**
     * The constant registeredTemplateMap.
     */
    private static Map<String, AbstractTemplate> registeredTemplateMap = new HashMap<>();

    /**
     * The constant templateCodeMatchedCache.
     */
    private static Map<TwoStringKey, Boolean> templateCodeMatchedCache = new ConcurrentHashMap<>();

    /**
     * The constant registeredbefTemplates.
     */
    private static List<AbstractTemplate> registeredbefTemplates = new ArrayList<>();

    /**
     * Instantiates a new Template manager.
     */
    private TemplateManager() {
    }

    /**
     * The type Two string key.
     */
    private static class TwoStringKey {
        /**
         * The Key 1.
         */
        private String key1;
        /**
         * The Key 2.
         */
        private String key2;

        /**
         * Instantiates a new Two string key.
         *
         * @param key1 the key 1
         * @param key2 the key 2
         */
        public TwoStringKey(String key1, String key2) {
            this.key1 = key1;
            this.key2 = key2;
        }

        /**
         * Equals boolean.
         *
         * @param o the o
         * @return the boolean
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            TwoStringKey that = (TwoStringKey)o;

            if (!Objects.equals(key1, that.key1)) { return false; }
            return Objects.equals(key2, that.key2);
        }

        /**
         * Hash code int.
         *
         * @return the int
         */
        @Override
        public int hashCode() {
            int result = key1 != null ? key1.hashCode() : 0;
            result = 31 * result + (key2 != null ? key2.hashCode() : 0);
            return result;
        }
    }

    /**
     * Gets instance.
     *
     * @return get the instance.
     */
    public static TemplateManager getInstance() {
        if (instance == null) {
            synchronized (TemplateManager.class) {
                if (instance == null) {
                    instance = new TemplateManager();
                }
            }
        }
        return instance;
    }

    /**
     * get the template via CODE.
     *
     * @param <T>          the type parameter
     * @param templateCode the CODE of template.
     * @return the found template, if not found, return null.
     */
    public <T extends AbstractTemplate> T getRegisteredTemplate(String templateCode) {
        return (T)registeredTemplateMap.get(templateCode);
    }

    /**
     * register the template.
     *
     * @param template the template to be registered.
     */
    public void registerTemplate(AbstractTemplate template) {
        if (null == template) { return; }
        assert StringUtils.isNotEmpty(template.getCode());
        registeredTemplateMap.put(template.getCode(), template);
    }

    /**
     * unregister the template and clean all the resources related to
     *
     * @param template the template to unregister
     */
    public synchronized void unregisterTemplate(AbstractTemplate template) {
        registeredTemplateMap.remove(template.getCode());
    }

    /**
     * 根据指定的Class Package路径，扫描并添加模板的扩展点实现类
     *
     * @param template      the template
     * @param classPackages the class package name.
     * @return the registered Extension realizations
     */
    @SuppressWarnings("unused")
    public List<TemplateRealization> registerExtensionRealization(AbstractTemplate template, String... classPackages) {
        if (null == template) { return new ArrayList<>(); }

        List<TemplateRealization> realizations = new ArrayList<>();
        if (null == classPackages) { return realizations; }
        ClassPathScanHandler handler = new ClassPathScanHandler();
        for (String classPackage : classPackages) {
            Set<Class<?>> classSet = handler.getPackageAllClasses(classPackage, true)
                .stream().filter(p -> null != p.getDeclaredAnnotation(TemplateExt.class))
                .collect(Collectors.toSet());

            List<TemplateRealization> realizationList = classSet.stream()
                .flatMap(p -> TemplateRealization.of(p).stream())
                .filter(Objects::nonNull)
                .filter(p -> templateCodeMatched(p.getTemplateCode(), template.getCode()))
                .collect(Collectors.toList());

            if (CollectionUtils.isEmpty(realizationList)) {
                LOGGER.warn(
                    String.format("The template's realization is Empty, templateCode [%s]", template.getCode()));
            }
            realizations.addAll(realizationList);
        }
        template.addRealizations(realizations);
        return realizations;
    }

    /**
     * 根据指定的class set 扫描并添加模板的扩展点实现类
     *
     * @param template    the template
     * @param allClassSet the all class set
     * @return the list
     */
    public List<TemplateRealization> registerExtensionRealization(AbstractTemplate template, Set<Class<?>> allClassSet) {
        if (null == template) { return new ArrayList<>(); }

        ClassPathScanHandler handler = new ClassPathScanHandler();
        Set<Class<?>> classSet = allClassSet.stream().filter(p -> null != p.getDeclaredAnnotation(TemplateExt.class))
            .collect(Collectors.toSet());

        List<TemplateRealization> realizationList = classSet.stream()
            .flatMap(p -> TemplateRealization.of(p).stream())
            .filter(Objects::nonNull)
            .filter(p -> templateCodeMatched(p.getTemplateCode(), template.getCode()))
            .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(realizationList)) {
            LOGGER.warn(String.format("The template's realization is Empty, templateCode [%s]", template.getCode()));
        }
        List<TemplateRealization> realizations = new ArrayList<>(realizationList);
        template.addRealizations(realizations);
        return realizations;
    }

    /**
     * Find template ext realization in array template realization [ ].
     *
     * @param template the template
     * @return the template realization [ ]
     */
    public TemplateRealization[] findTemplateExtRealizationInArray(
        AbstractTemplate<? extends IExtensionPointsFacade> template) {
        if (null == template) { return null; }
        return template.getTemplateExtRealization();
    }

    /**
     * Template code matched boolean.
     *
     * @param code         the code
     * @param specificCode the specific code
     * @return the boolean
     */
    public static boolean templateCodeMatched(String code, String specificCode) {
        if (StringUtils.equals(code, specificCode)) { return true; }
        if (!StringUtils.contains(code, ASTERISK)) { return false; }
        TwoStringKey key = new TwoStringKey(code, specificCode);
        Boolean value = templateCodeMatchedCache.get(key);
        if (value != null) {
            return value;
        }
        //如果一直没有命中，就会做正则匹配，这里也是懒加载
        String patternStr = code2Pattern(code);
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(specificCode);
        boolean found = matcher.find();
        if (!found) {
            LOGGER.warn(String.format("The specific CODE [%s] not match the CODE pattern [%s]", specificCode, code));
        }
        int i = 10000;
        if (templateCodeMatchedCache.size() < i) {
            templateCodeMatchedCache.put(key, found);
        }
        return found;
    }

    /**
     * Code 2 pattern string.
     *
     * @param code the code
     * @return the string
     */
    private static String code2Pattern(String code) {
        String pattern = code.replace(".", "\\.");
        pattern = pattern.replace("*", "[a-zA-Z\\._]*");
        return "^" + pattern;
    }

    /**
     * Register bef template.
     *
     * @param template the template
     */
    public void registerbefTemplate(AbstractTemplate template) {
        registeredbefTemplates.add(template);
    }
}
