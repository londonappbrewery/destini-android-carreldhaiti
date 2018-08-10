package com.londonappbrewery.destini;

/* Class TextStory helps manage the text stories */
public class TextStory {

    // member variables
    private int textStoryResourceId;   // default is 0
    //private int mAnswerStoryResourceId; // default is 0


    // One-arg constructor
    public TextStory( int textStoryResourceId ) {

        this.textStoryResourceId = textStoryResourceId;

    }   // end of one-arg constructor

    /* Getter for text story resource id */
    public int getTextStoryResourceId() {

        return textStoryResourceId;

    }   // end of method getTextStoryResourceId()

    /* Setter for text story resource id */
    public void setTextStoryResourceId( int textStoryResourceId ) {

        this.textStoryResourceId = textStoryResourceId;

    }   // end of method setTextStoryResourceId()

}   //  end of class TextStory
