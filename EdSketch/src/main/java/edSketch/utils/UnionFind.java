package edSketch.utils;

import java.util.ArrayList;
import java.util.List;

public class UnionFind implements Cloneable{

    private List<Integer> tree;
    private List<Integer> weight;
    private int parts;
    
    public UnionFind(){
        tree = new ArrayList<>();
        weight = new ArrayList<>();
        parts = 0;
    }
    
    public UnionFind(int n) {
        this();
        for (int i = 0; i < n; i++) {
            addNode();
        }
    }
    
    public int addNode() {
        int id = tree.size();
        tree.add(id);
        weight.add(1);
        parts ++;
        return id;
    }
    
    private int root(int node){
        if(tree.get(node) == node) {
            return node;
        } else {
            tree.set(node, root(tree.get(node)));
            return tree.get(node);
        }
    }
    
    public boolean isConnected(int i, int j){
        return root(i) == root(j);
    }
    
    public void connect(int i, int j){
        i = root(i);
        j = root(j);
        if(i == j) return;
        if(weight.get(i) < weight.get(j)){
            tree.set(i, j);
            weight.set(j, weight.get(j) + weight.get(i));
        }else{
            tree.set(j, i);
            weight.set(i, weight.get(i) + weight.get(j));
        }
        parts--;
    }
    
    public int partsNum(){
        return parts;
    }
    
    @Override
    public UnionFind clone() {
        UnionFind res = new UnionFind();
        res.tree.addAll(this.tree);
        res.weight.addAll(this.weight);
        res.parts = this.parts;
        return res;
    }
    
}
