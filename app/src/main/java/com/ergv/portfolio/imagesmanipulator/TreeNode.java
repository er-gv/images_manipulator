package com.ergv.portfolio.imagesmanipulator;

import java.util.ArrayList;

public class TreeNode<type>{
    private type value;
    ArrayList<TreeNode> children;
    TreeNode parent;

    public TreeNode getChildAt(int index) throws IndexOutOfBoundsException{

        if(index <0 || index >= children.size())
            throw new  IndexOutOfBoundsException();
        return children.get(index);
    }

    public ArrayList<type> getAllChildren(){
        ArrayList<type> kids = new ArrayList<type>();
        for(TreeNode tn: children)
            kids.add((type)tn.value);
        return kids;
    }

    public type getValue(){
        return value;
    }


    public boolean isLeaf(){
        return (children.size()==0);
    }

    public void appendChild(type obj, TreeNode parent){
        TreeNode newNode = new TreeNode();
        newNode.value = obj;
        parent.children.add(newNode);
    }

    public void appendChildAt(type obj, TreeNode parent, int pos){
        TreeNode newNode = new TreeNode();
        newNode.value = obj;
        parent.children.add(pos, newNode);
    }



}