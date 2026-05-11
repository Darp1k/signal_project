package com.alerts;

// priority alert for critical conditions
public class PriorityAlertDecorator extends AlertDecorator {
    private String priorityLevel;

    public PriorityAlertDecorator(Alert decoratedAlert, String priorityLevel) {
        super(decoratedAlert);
        this.priorityLevel = priorityLevel;
    }

    // We modify the condition to include the priority tag
    @Override
    public String getCondition() {
        // We use super.getCondition() to avoid needing protected access
        return super.getCondition() + " [Priority: " + priorityLevel + "]";
    }
    
    public String getPriorityLevel() {
        return priorityLevel;
    }
}