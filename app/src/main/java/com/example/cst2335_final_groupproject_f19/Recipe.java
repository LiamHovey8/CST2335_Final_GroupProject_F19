package com.example.cst2335_final_groupproject_f19;

class Recipe {
    private String name;
    private int id;

    Recipe(){

    }
    Recipe(String name, int id){
        setName(name);
        setId(0);
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

