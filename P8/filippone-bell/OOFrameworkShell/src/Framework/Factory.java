package Framework;


public abstract class Factory {
    public Factory() {
    }

    public abstract Calc NewCalc();
    public abstract Gui  NewGui();
}
