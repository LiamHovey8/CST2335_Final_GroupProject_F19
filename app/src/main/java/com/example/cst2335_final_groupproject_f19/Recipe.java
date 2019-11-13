package com.example.cst2335_final_groupproject_f19;
//the recipe class is here to store the data about recipes in one

/**
 *
 */
class Recipe {
    private String publisher;
    private String f2f_url;
    private String title;
    private String source_url;
    private String recipe_id;
    private String image_url;
    private double social_rank;
    private String publisher_url;



    Recipe(){

    }
    Recipe(String publisher,String f2f_url,String title,String source_url,String recipe_id,String image_url,double social_rank,String publisher_url){
        setPublisher(publisher);
        setF2f_url(f2f_url);
        setTitle(title);
        setSource_url(source_url);
        setRecipe_id(recipe_id);
        setImage_url(image_url);
        setSocial_rank(social_rank);
        setPublisher_url(publisher_url);
    }

    public void setF2f_url(String f2f_url){this.f2f_url=f2f_url;}
    public void setPublisher(String publisher) {this.publisher = publisher; }
    public void setTitle(String title){this.title=title;}
    public void setSource_url(String source_url){this.source_url=source_url;}
    public void setRecipe_id(String recipe_id){this.recipe_id=recipe_id;}
    public void setImage_url(String image_url){this.image_url=image_url;}
    public void setSocial_rank(double social_rank){this.social_rank=social_rank;}
    public void setPublisher_url(String publisher_url){this.publisher_url=publisher_url;}

    public String getPublisher() { return publisher; }
    public String getF2f_url(){ return f2f_url;}
    public String getTitle(){return title;}
    public String getSource_url(){return source_url;}
    public String getRecipe_id(){return recipe_id;}
    public String getImage_url(){return image_url;}
    public double getSocial_rank(){return social_rank;}
    public String getPublisher_url(){return publisher_url;}
}

