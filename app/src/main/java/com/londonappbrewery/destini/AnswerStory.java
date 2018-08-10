package com.londonappbrewery.destini;


/* Class AnswerStory helps manage the answers to the stories */
public class AnswerStory {

    // Member variable
    private int answerStoryResourceId; // default value is 0

    /* One-arg constructor */
    public AnswerStory( int answerStoryResourceId ) {

         this.answerStoryResourceId = answerStoryResourceId ;

    }   // end of one-arg constructor

    /* Getter method getAnswerStoryResourceId() */
    public int getAnswerStoryResourceId() {

        return answerStoryResourceId;

    }   // end of getter method getAnswerStoryID()

    /* Setter method setAnswerStoryId() */
    public void setAnswerStoryResourceId( int answerStoryResourceId ) {

        this.answerStoryResourceId = answerStoryResourceId;

    }   // end of setter method setAnswerStoryResourceId()

}   // end of class AnswerStory
