package co.edu.eafit.mrblock.Entidades;

/**
 * Created by Usuario on 17/11/2015.
 */
public class TransitionBlock {
    public int Block;
    public String Type = "MapsBlocked";

    public TransitionBlock(int block){
        this.Block = block;
    }

    public TransitionBlock(){}

    public int getBlock(){
        return Block;
    }

    public String getType(){
        return Type;
    }
}
