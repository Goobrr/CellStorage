package cell.graph;

import arc.struct.*;
import cell.GraphBlock.*;
import mindustry.gen.*;

public class CellGraph{
    protected Seq<Building> cores = new Seq<>(false, 16, Building.class);
    protected Seq<Building> devices = new Seq<>(false, 16, Building.class);
    protected Seq<GraphBuild> all = new Seq<>(false, 16, GraphBuild.class);

    private final int graphID;
    private static int lastGraphID;

    public CellGraph(){
        graphID = lastGraphID++;
    }

    public int getID(){
        return graphID;
    }

    public void add(GraphBuild b){
        b.cellGraph = this;
        all.add(b);
    }

    public void remove(GraphBuild b){
        b.cellGraph = null;
        all.remove(b);
    }

    public CellGraph mergeWith(CellGraph graph){
        if(graph.graphID == graphID) return this;

        CellGraph merged = graph.all.size <= this.all.size ? graph : this;
        CellGraph merger = this.all.size >= graph.all.size ? this : graph;
        for(GraphBuild b : merged.all){
            b.cellGraph = merger;
            merger.add(b);
        }
        return merger;
    }

    public void delete(){
        for(GraphBuild b : all){
            b.cellGraph = null;
        }
        all.clear();
    }
}
