package cell;

import mindustry.mod.*;
import mindustry.world.meta.*;

public class CellStorage extends Mod{

    public CellStorage(){

    }

    @Override
    public void loadContent(){
        new GraphBlock("test1"){{
            buildVisibility = BuildVisibility.shown;
        }};
        new GraphBlock("test2"){{
            size = 2;
            buildVisibility = BuildVisibility.shown;
        }};
        new GraphBlock("test3"){{
            size = 3;
            buildVisibility = BuildVisibility.shown;
        }};
        new GraphBlock("test4"){{
            size = 4;
            buildVisibility = BuildVisibility.shown;
        }};
    }

}
