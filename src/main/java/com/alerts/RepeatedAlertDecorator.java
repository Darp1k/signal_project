package com.alerts;

// decorator class to add repeated alert functionality 
public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;
    private long repeatInterval;

    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatCount, long repeatInterval) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
        this.repeatInterval = repeatInterval;
    }

    @Override
    public String getCondition() {
        return super.getCondition() + " (Repeats left: " + repeatCount + ")";
    }
    
    // Getters so the AlertManager knows what to do
    public int getRepeatCount() { 
        return repeatCount; 
    }
    
    public long getRepeatInterval() { 
        return repeatInterval; 
    }

    // Method to reduce the count after each dispatch
    public void decrementCount() {
        if (this.repeatCount > 0) {
            this.repeatCount--;
        }
    }
}