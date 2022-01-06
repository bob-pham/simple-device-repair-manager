package devices;

public interface MobileDevice {

    int getScreenCondition();

    boolean hasBattery();

    void setBattery(boolean battery);

    void setScreenCondition(int condition);

}
