package SKAZA.ai;
public class ElapsedTimer {
    long oldTime;

    public ElapsedTimer() {
        oldTime = System.currentTimeMillis();
    }

    public long elapsed() {
        return System.currentTimeMillis() - oldTime;
    }

    public void reset() {
        oldTime = System.currentTimeMillis();
    }

    public String toString() {
        String ret = elapsed() + " ms elapsed";
        reset();
        return ret;
    }
}


