package DPlugIn;


import Framework.Calc;
import Framework.Gui;

public class Factory extends Framework.Factory {

    @Override
    public Calc NewCalc() {
        return new DPlugIn.Calc();
    }

    @Override
    public Gui NewGui() {
        return new DPlugIn.Gui();
    }

}
