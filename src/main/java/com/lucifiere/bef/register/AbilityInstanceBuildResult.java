package com.lucifiere.bef.register;

import com.lucifiere.bef.exception.BefErrorMessage;
import com.lucifiere.bef.model.AbilityInstanceSpec;

/**
 * The type Ability instance build result.
 *
 * @author 沾清
 */
public class AbilityInstanceBuildResult {

    /**
     * The Success.
     */
    private boolean success;

    /**
     * The Registered.
     */
    private boolean registered;

    /**
     * The Error message.
     */
    private BefErrorMessage errorMessage;

    /**
     * The Instance spec.
     */
    private AbilityInstanceSpec instanceSpec;

    /**
     * Instantiates a new Ability instance build result.
     */
    private AbilityInstanceBuildResult() {

    }

    /**
     * Registered ability instance build result.
     *
     * @return the ability instance build result
     */
    public static AbilityInstanceBuildResult registered() {
        AbilityInstanceBuildResult result = new AbilityInstanceBuildResult();
        result.registered = true;
        return result;
    }

    /**
     * Success ability instance build result.
     *
     * @param instanceSpec the instance spec
     * @return the ability instance build result
     */
    public static AbilityInstanceBuildResult success(AbilityInstanceSpec instanceSpec) {
        AbilityInstanceBuildResult result = new AbilityInstanceBuildResult();
        result.success = true;
        result.instanceSpec = instanceSpec;
        return result;
    }

    /**
     * Failed ability instance build result.
     *
     * @param errorMessage the error message
     * @return the ability instance build result
     */
    public static AbilityInstanceBuildResult failed(BefErrorMessage errorMessage) {
        AbilityInstanceBuildResult result = new AbilityInstanceBuildResult();
        result.errorMessage = errorMessage;
        return result;
    }

    /**
     * Gets success
     *
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets success
     *
     * @param success the success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets registered
     *
     * @return the registered
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     * Sets registered
     *
     * @param registered the registered
     */
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    /**
     * Gets errorMessage
     *
     * @return the errorMessage
     */
    public BefErrorMessage getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets errorMessage
     *
     * @param errorMessage the errorMessage
     */
    public void setErrorMessage(BefErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets instanceSpec
     *
     * @return the instanceSpec
     */
    public AbilityInstanceSpec getInstanceSpec() {
        return instanceSpec;
    }

    /**
     * Sets instanceSpec
     *
     * @param instanceSpec the instanceSpec
     */
    public void setInstanceSpec(AbilityInstanceSpec instanceSpec) {
        this.instanceSpec = instanceSpec;
    }
}
