package co.edu.eafit.mrblock.Entidades;

/**
 * Created by Usuario on 17/11/2015.
 */
public class TransitionBlock {
    public String type;
    public int block;

    public TransitionBlock(String type,int block){
        this.type = type;
        this.block = block;
    }


    public int getBlock(){
        return block;
    }

    public String getType(){
        return type;
    }
}
