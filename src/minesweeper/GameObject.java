package minesweeper;

public abstract class GameObject implements Resettable {
    protected boolean isActive;

    public GameObject() {
        this.isActive = true;
    }

    public abstract void performAction();

    public void displayStatus() {
        System.out.println("Object is active: " + isActive);
    }

    @Override
    public void reset() {
        isActive = true;
        System.out.println("Object has been reset.");
    }

    public void additionalReset() {
        System.out.println("Additional reset for GameObject.");
    }
}
