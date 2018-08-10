package com.londonappbrewery.destini;

/* Class EndStory helps manage the ends of the stories */
public class EndStory {

    private int endStoryStoryResourceId;    // Default value is 0

    /* One-arg constructor */
    public EndStory( int endStoryStoryResourceId ) {

        this.endStoryStoryResourceId = endStoryStoryResourceId;

    }   // end of one-arg constructor

    /* Getter method getEndStoryResourceId() */
    public int getEndStoryStoryResourceId() {

        return endStoryStoryResourceId;

    }   // end of getter method getEndStoryStoryResourceId()

    /* Setter method setEndStoryResourceId() */
    public void setEndStoryStoryResourceId( int endStoryStoryResourceId ) {

        this.endStoryStoryResourceId = endStoryStoryResourceId;

    }   // end of setter method setEndStoryStoryId()

}   // end of class EndStory
