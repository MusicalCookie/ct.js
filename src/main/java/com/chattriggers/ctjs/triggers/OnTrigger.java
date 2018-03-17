package com.chattriggers.ctjs.triggers;

import com.chattriggers.ctjs.modules.Module;
import lombok.Getter;
import lombok.Setter;

public abstract class OnTrigger {
    @Getter
    protected String methodName;
    @Getter
    protected Object method;
    @Getter
    protected Priority priority;
    @Getter
    protected TriggerType type;
    @Getter @Setter
    protected Module owningModule;

    protected OnTrigger(String methodName, TriggerType type) {
        this.methodName = methodName;
        this.priority = Priority.NORMAL;
        this.type = type;

        if (TriggerRegister.currentModule != null) {
            setOwningModule(TriggerRegister.currentModule);
        }

        this.register();
    }

    public OnTrigger(Object method, TriggerType type) {
        this.method = method;
        this.priority = Priority.NORMAL;
        this.type = type;

        if (TriggerRegister.currentModule != null) {
            setOwningModule(TriggerRegister.currentModule);
        }

        this.register();
    }

    /**
     * Sets a triggers priority using {@link Priority}.
     * Highest runs first.
     * @param priority the priority of the trigger
     * @return the trigger for method chaining
     */
    public OnTrigger setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Registers a trigger based on its type.
     * This is done automatically with TriggerRegister.
     * @return the trigger for method chaining
     */
    public OnTrigger register() {
        this.type.addTrigger(this);
        return this;
    }

    /**
     * Unregisters a trigger.
     * @return the trigger for method chaining
     */
    public OnTrigger unregister() {
        this.type.removeTrigger(this);
        return this;
    }

    /**
     * @return boolean of if trigger is registered
     */
    public boolean isRegistered() {
        return this.type.containsTrigger(this);
    }

    public abstract void trigger(Object... args);

    public enum TriggerResult {
        CANCEL
    }

    public enum Priority {
        //LOWEST IS RAN LAST
        LOWEST, LOW, NORMAL, HIGH, HIGHEST
    }
}
