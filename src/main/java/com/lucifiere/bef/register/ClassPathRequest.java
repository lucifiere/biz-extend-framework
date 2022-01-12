package com.lucifiere.bef.register;

/**
 * The type Class path request.
 *
 * @author 沾清
 */
public class ClassPathRequest {

    /**
     * The Class packages.
     */
    private String[] classPackages;

    /**
     * Instantiates a new Class path request.
     *
     * @param classPackages the class packages
     */
    public ClassPathRequest(String[] classPackages) {
        this.classPackages = classPackages;
    }

    /**
     * Instantiates a new Class path request.
     *
     * @param classpath the classpath
     */
    public ClassPathRequest(String classpath) {
        this.classPackages = new String[] {classpath};
    }

    /**
     * Gets classPackages
     *
     * @return the classPackages
     */
    public String[] getClassPackages() {
        return classPackages;
    }

    /**
     * Sets classPackages
     *
     * @param classPackages the classPackages
     */
    public void setClassPackages(String[] classPackages) {
        this.classPackages = classPackages;
    }
}
