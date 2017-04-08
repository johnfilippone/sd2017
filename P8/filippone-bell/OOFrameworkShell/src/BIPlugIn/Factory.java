package BIPlugIn;


import Framework.Calc;
import Framework.Gui;

public class Factory extends Framework.Factory {

    @Override
    public Calc NewCalc() {
        return new BIPlugIn.Calc();
    }

    @Override
    public Gui NewGui() {
        return new BIPlugIn.Gui();
    }

}
