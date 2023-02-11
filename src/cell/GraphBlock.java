package cell;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;
import arc.util.*;
import cell.graph.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;

public class GraphBlock extends Block{
    public GraphBlock(String name){
        super(name);

        update = true;
        solid = true;
        size = 1;
        health = 60;
    }

    public class GraphBuild extends Building{
        public CellGraph cellGraph;

        @Override
        public void created(){
            super.created();
            cellGraph = new CellGraph();
            cellGraph.add(this);

            for(Point2 p : getEdges()){
                if(Vars.world.build(tile.x + p.x, tile.y + p.y) instanceof GraphBuild b){
                    if(b.cellGraph != null){
                        b.cellGraph.mergeWith(cellGraph);
                    }else{
                        cellGraph.add(b);
                    }
                }
            }
        }

        public void floodfill(Building source){
            for(Point2 p : getEdges()){
                if(Vars.world.build(tile.x + p.x, tile.y + p.y) instanceof GraphBuild b && b != source){
                    if(b.cellGraph != null){
                        b.cellGraph.mergeWith(cellGraph);
                    }else{
                        cellGraph.add(b);
                        b.floodfill(source);
                    }
                }
            }
        }

        @Override
        public void onRemoved(){
            if(cellGraph != null){
                cellGraph.delete();
                for(Point2 p : getEdges()){
                    if(Vars.world.build(tile.x + p.x, tile.y + p.y) instanceof GraphBuild b){
                        b.cellGraph = new CellGraph();
                        b.cellGraph.add(b);

                        b.floodfill(this);
                    }
                }
            }
            super.onRemoved();
        }

        @Override
        public void draw(){
            for(Point2 p : getEdges()){
                Lines.square((tile.x + p.x) * 8, (tile.y + p.y) * 8, 4);
            }

            boolean noGraph = cellGraph == null;
            Draw.color(Tmp.c1.set(noGraph ? Pal.darkishGray : Color.red).shiftHue(noGraph ? 0 : cellGraph.getID() * 15));
            Fill.square(x, y, size * 4);
            Draw.color();

            CellDrawf.text(x, y, Color.white, noGraph ? "-" : cellGraph.getID() + "");
        }
    }
}
