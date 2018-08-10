package com.londonappbrewery.destini;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements DestiniFragment.NoticeDialogListener {

    // TODO: Steps 4 & 8 - Declare member variables here:

    private TextView mTextStory;   // T1_Story, T2_Story, T3_Story
    private Button mTopButton;  // T1_Ans1, T3_Ans1, T3_Ans2
    private Button mBottomButton;    // T1_Ans2,  T2_Ans1, T2_Ans2, T3_Ans1, T3_Ans2

    private DestiniFragment mDestiniFragmentDialog;

    private int mTopButtonClickCount = 0;        // No click yet
    private int mBottomButtonClickCount = 0;     // No click yet

    private int mTextStoryIndex = 0;
    private int mAnswerStoryIndex = 0;
    private int mEndStoryIndex = 0;

    private int mTempTextStoryIndex = 0;

    private int mTextStoryResourceId;
    private int mAnswerStoryResourceId;
    private int mEndStoryResourceId;

    private boolean mEndOfStory = false;

    private boolean mFirstTimeButtonIsClicked = true;

    // Create the array to hold the text stories:
    //      TextStory[] mTextSToryBank = new TextStory[] { new TextStory( R.string.T1_Story ), ... };
    private TextStory[] mTextStoryBank = new TextStory[] {

            new TextStory( R.string.T1_Story ),
            new TextStory( R.string.T2_Story ),
            new TextStory( R.string.T3_Story )
    };

    // Create the array to hold the answer stories:
    //      AnswerStory[] mAnswerSToryBank = new AnswerStory[] { new AnswerStory( R.string.T1_Ans1 ), ... };
    private  AnswerStory[] mAnswerStoryBank = new AnswerStory[] {

            new AnswerStory( R.string.T1_Ans1 ),
            new AnswerStory( R.string.T1_Ans2 ),
            new AnswerStory( R.string.T2_Ans1 ),
            new AnswerStory( R.string.T2_Ans2 ),
            new AnswerStory( R.string.T3_Ans1 ),
            new AnswerStory( R.string.T3_Ans2 )

    };

    // Create the array to hold the end stories:
    //      EndStory[] mEndStoryBank = new EndStory[] { new EndStory( R.string.T4_End ), ... };
    private EndStory[] mEndStoryBank = new EndStory[] {

            new EndStory( R.string.T4_End ),
            new EndStory( R.string.T5_End ),
            new EndStory( R.string.T6_End )

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the Bundle state after the phone is rotated
        if ( savedInstanceState != null ) {

            mTextStoryIndex = savedInstanceState.getInt( "TextStoryIndexKey" );
            mAnswerStoryIndex = savedInstanceState.getInt( "AnswerStoryIndexKey" );
            mEndStoryIndex = savedInstanceState.getInt( "EndStoryIndexKey" );

            mTopButtonClickCount = savedInstanceState.getInt( "TopButtonClickCountKey" );
            mBottomButtonClickCount = savedInstanceState.getInt( "BottomButtonClickCountKey" );

            mTempTextStoryIndex = savedInstanceState.getInt( "TempTextStoryIndexKey" );

            mFirstTimeButtonIsClicked = savedInstanceState.getBoolean( "FirstTimeButtonIsClickedKey" );
            mEndOfStory = savedInstanceState.getBoolean( "EndOfStoryKey" );

        } else {

            mTextStoryIndex = 0;
            mAnswerStoryIndex = 0;
            mEndStoryIndex= 0;

            mTopButtonClickCount = 0;
            mBottomButtonClickCount = 0;

            mTempTextStoryIndex = 0;

            mFirstTimeButtonIsClicked = true;
            mEndOfStory = false;

        }


        // TODO: Step 5 - Wire up the 3 views from the layout to the member variables:
        mTextStory = ( TextView ) findViewById( R.id.storyTextView );
        mTopButton = ( Button ) findViewById( R.id.buttonTop );
        mBottomButton = ( Button ) findViewById( R.id.buttonBottom );

        // Invoke method updateStoryAndAnswer() after screen rotation or to initialize the buttons
        // and the text view before starting the game
        updateStoryAndAnswer();

        // TODO: Steps 6, 7, & 9 - Set a listener on the top button:
        mTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTopButtonClickCount++;  // Keep count of the top button click count

                mTextStoryIndex =  2;   // Reset the story index to 2


                if ( mBottomButtonClickCount == 0 && mTopButtonClickCount == 1 ) {  // T1_Ans1 is clicked
                    mAnswerStoryIndex = 4;
                }

                if ( mBottomButtonClickCount == 0 && mTopButtonClickCount == 2 ) {  // T3_Ans1 is clicked
                    mAnswerStoryIndex = 4;
                    mEndStoryIndex = 2;
                }

                if ( mBottomButtonClickCount == 1 && mTopButtonClickCount == 1 ) {  // T2_Ans1 is clicked
                    mAnswerStoryIndex = 4;
                }

                if ( mBottomButtonClickCount == 1 && mTopButtonClickCount == 2 ) {  // T3_Ans1 is clicked
                    mAnswerStoryIndex = 4;
                    mEndStoryIndex = 2;
                }


                if ( mFirstTimeButtonIsClicked ) {

                    mFirstTimeButtonIsClicked = false;   // Set it to false for any bottom button click

                }

                updateStoryAndAnswer();                 // Invoke method updateStoryAndAnswer()

            }   // end of method onClick()

        });     // end of listener topButton.setOnClickListener



        // TODO: Steps 6, 7, & 9 - Set a listener on the bottom button:
        mBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBottomButtonClickCount++;   // Keep count of the bottom button click

                mTextStoryIndex = 1;      // Reset the story index to 1 for a bottom button click

                if ( mBottomButtonClickCount == 1 && mTopButtonClickCount == 0 ) {  // T1_Ans2 is clicked
                    mAnswerStoryIndex = 2;
                }

                if ( mBottomButtonClickCount == 1 && mTopButtonClickCount == 1 ) {  // T3_Ans2 is clicked
                    mTempTextStoryIndex = 2;    // Reset the temp text story index to 2
                    mAnswerStoryIndex = 4;
                    mEndStoryIndex = 1;
                }

                if ( mBottomButtonClickCount == 2 && mTopButtonClickCount == 0 ) {  // T2_Ans2 is clicked
                    mAnswerStoryIndex = 2;
                    mEndStoryIndex = 0;
                }

                if ( mBottomButtonClickCount == 2 && mTopButtonClickCount == 1 ) {  // T3_Ans2 is clicked
                    mTempTextStoryIndex = 2;    // Reset the text story index to 2
                    mAnswerStoryIndex = 4;
                    mEndStoryIndex = 1;
                }


                if (mFirstTimeButtonIsClicked ) {

                    mFirstTimeButtonIsClicked = false;

                }

                updateStoryAndAnswer();         // Invoke method updateStoryAndAnswer()

            }   // end of method onClick()

        });     // end of listener bottomButton.setOnClickListener()

    }   // end of method onCreate()


    // Method to update the story and the answers
    private void updateStoryAndAnswer() {

        // Update the text view and the buttons with the stories and the answers, accordingly
        // Default: no button is clicked
        if ( (mTextStoryIndex == 0 && mAnswerStoryIndex == 0)
                && (mBottomButtonClickCount == 0 &&  mTopButtonClickCount == 0) ) {     // No clicks

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[ mTextStoryIndex ].getTextStoryResourceId();
            mAnswerStoryResourceId = mAnswerStoryBank[ mAnswerStoryIndex ].getAnswerStoryResourceId();

            mTextStory.setText( mTextStoryResourceId );     // T1_Story

            mTopButton.setText( mAnswerStoryResourceId );   // T1_Ans1

            mAnswerStoryResourceId = mAnswerStoryBank[ mAnswerStoryIndex + 1 ].getAnswerStoryResourceId();
            mBottomButton.setText( mAnswerStoryResourceId );    // T1_Ans2

            mEndOfStory = false;

        }

        /* Bottom button clicks */
        if ( (mTextStoryIndex == 1 && mAnswerStoryIndex == 2)
                && (mBottomButtonClickCount == 1 &&  mTopButtonClickCount == 0) ) {     // T1_Ans2 is clicked

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[mTextStoryIndex].getTextStoryResourceId();
            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex].getAnswerStoryResourceId();


            mTextStory.setText(mTextStoryResourceId);     // T2_Story

            mTopButton.setText(mAnswerStoryResourceId);       // T2_Ans1

            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex + 1].getAnswerStoryResourceId();
            mBottomButton.setText(mAnswerStoryResourceId);    // T2_Ans2

            mEndOfStory = false;
        }

        if ( (mTextStoryIndex == 1 &&  mAnswerStoryIndex == 2)
                && (mBottomButtonClickCount == 2 && mTopButtonClickCount == 0) ) {     // T2_Ans2 is clicked

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[mTextStoryIndex].getTextStoryResourceId();
            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex].getAnswerStoryResourceId();
            mEndStoryResourceId = mEndStoryBank[mEndStoryIndex].getEndStoryStoryResourceId();

            mTextStory.setText(mTextStoryResourceId);     // T2_Story

            mTopButton.setText(mAnswerStoryResourceId);       // T2_Ans1

            mBottomButton.setText(mEndStoryResourceId);    // T4_End

            mEndOfStory = true;

        }

        if ( (mTextStoryIndex == 1 &&  mAnswerStoryIndex == 4 )
                && (mBottomButtonClickCount == 1 && mTopButtonClickCount == 1) ) {  // T3_Ans2 is clicked

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[mTempTextStoryIndex].getTextStoryResourceId();
            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex].getAnswerStoryResourceId();
            mEndStoryResourceId = mEndStoryBank[mEndStoryIndex].getEndStoryStoryResourceId();

            mTextStory.setText(mTextStoryResourceId);     // T3_Story

            mTopButton.setText(mAnswerStoryResourceId);       // T3_Ans1

            mBottomButton.setText(mEndStoryResourceId);    // T5_End

            mEndOfStory = true;

        }

        if ( (mTextStoryIndex == 1 &&  mAnswerStoryIndex == 4 )
                && (mBottomButtonClickCount == 2 && mTopButtonClickCount == 1) ) {  // T3_Ans2 is clicked

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[mTempTextStoryIndex].getTextStoryResourceId();
            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex].getAnswerStoryResourceId();
            mEndStoryResourceId = mEndStoryBank[mEndStoryIndex].getEndStoryStoryResourceId();

            mTextStory.setText(mTextStoryResourceId);     // T3_Story

            mTopButton.setText(mAnswerStoryResourceId);       // T3_Ans1

            mBottomButton.setText(mEndStoryResourceId);    // T5_End

            mEndOfStory = true;

        }

        /* Top button is clicked */
        if ( (mTextStoryIndex == 2 &&  mAnswerStoryIndex == 4 )
                && (mBottomButtonClickCount == 0 && mTopButtonClickCount == 1) ) {  // T1_Ans1 is clicked

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[mTextStoryIndex].getTextStoryResourceId();
            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex].getAnswerStoryResourceId();

            mTextStory.setText(mTextStoryResourceId);     // T3_Story

            mTopButton.setText(mAnswerStoryResourceId);       // T3_Ans1

            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex + 1].getAnswerStoryResourceId();
            mBottomButton.setText(mAnswerStoryResourceId);    // T3_Ans2

            mEndOfStory = false;

        }

        if ( (mTextStoryIndex == 2 &&  mAnswerStoryIndex == 4 )
                && (mBottomButtonClickCount == 0 && mTopButtonClickCount == 2) ) {  // T3_Ans1 is clicked

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[mTextStoryIndex].getTextStoryResourceId();
            mEndStoryResourceId = mEndStoryBank[mEndStoryIndex].getEndStoryStoryResourceId();

            mTextStory.setText(mTextStoryResourceId);     // T3_Story

            mTopButton.setText(mEndStoryResourceId);    // T6_End

            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex + 1].getAnswerStoryResourceId();
            mBottomButton.setText(mAnswerStoryResourceId);       // T3_Ans2

            mEndOfStory = true;

        }


        if ( (mTextStoryIndex == 2 &&  mAnswerStoryIndex == 4 )
                && (mBottomButtonClickCount == 1 && mTopButtonClickCount == 1) ) {  // T1_Ans1 is clicked

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[mTextStoryIndex].getTextStoryResourceId();
            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex].getAnswerStoryResourceId();

            mTextStory.setText(mTextStoryResourceId);     // T3_Story

            mTopButton.setText(mAnswerStoryResourceId);       // T3_Ans1

            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex + 1].getAnswerStoryResourceId();
            mBottomButton.setText(mAnswerStoryResourceId);    // T3_Ans2

            mEndOfStory = false;

        }

        if ( (mTextStoryIndex == 2 &&  mAnswerStoryIndex == 4 )
                && (mBottomButtonClickCount == 1 && mTopButtonClickCount == 2) ) {  // T3_Ans1 is clicked

            // Get the resource id's
            mTextStoryResourceId = mTextStoryBank[mTextStoryIndex].getTextStoryResourceId();
            mEndStoryResourceId = mEndStoryBank[mEndStoryIndex].getEndStoryStoryResourceId();

            mTextStory.setText(mTextStoryResourceId);     // T3_Story

            mTopButton.setText(mEndStoryResourceId);    // T6_End

            mAnswerStoryResourceId = mAnswerStoryBank[mAnswerStoryIndex + 1].getAnswerStoryResourceId();
            mBottomButton.setText(mAnswerStoryResourceId);       // T3_Ans2

            mEndOfStory = true;

        }

        // Check to see if one end has been reached
        if ( isEndOfStory( mEndOfStory ) ) {

            mTextStory.setEnabled( false );             // Disable the text story
            mTopButton.setVisibility( View.GONE );      // Hide the top button
            mBottomButton.setVisibility( View.GONE );   // Hide the bottom button

            showNoticeDialog();                         // Invoke the method showNoticeDialog()

        }   // End of the if condition checking if it is the end of the story

    }   // end of method upDateStoryAndAnswer()

    /* Save the Bundle state so that, when the phone is rotated, the story and answers are
     * accurately tracked at any given time.
     */
    @Override
    public void onSaveInstanceState( Bundle outState ) {

        super.onSaveInstanceState( outState );

        outState.putInt( "TextStoryIndexKey", mTextStoryIndex );
        outState.putInt( "AnswerStoryIndexKey", mAnswerStoryIndex );
        outState.putInt( "EndStoryIndexKey", mEndStoryIndex );
        outState.putInt( "TopButtonClickCountKey", mTopButtonClickCount );
        outState.putInt( "BottomButtonClickCountKey", mBottomButtonClickCount );
        outState.putInt( "TempTextStoryIndexKey" , mTempTextStoryIndex );
        outState.putBoolean( "FirstTimeButtonIsClickedKey", mFirstTimeButtonIsClicked );
        outState.putBoolean( "EndOfStoryKey", mEndOfStory );

    }   // end of method onSaveInstanceState()


    /* Start of method isEndOfStory() */
    private  boolean isEndOfStory( boolean endOfStory ) {

        return endOfStory;

    }   // end method isGameOver()


    /* Start of method showNoticeDialog() */
    public void showNoticeDialog() {

        // Create an instance of the  dialog fragment
        mDestiniFragmentDialog = new DestiniFragment();

        // Display the alert dialog
        mDestiniFragmentDialog.show( getSupportFragmentManager(), "destiniDialogFragment" );

    }   // end of method showNoticeDialog()


    /* Implementation of the abstract method onDialogPositiveClick()
    *  This method is defined in the interface NoticeDialogListener, which is
    *  inside the class DestiniFragment.
    */
    @Override
    public void onDialogPositiveClick( DialogFragment dialog ) {

        // When the "Yes" button is clicked to continue the game, dismiss the alert dialog and invoke
        // the method startGameOver() to start the game from the beginning.
        dialog.dismiss();
        startGameOver();

    }   // end of method onDialogPositiveClick()


    /* Implementation of the abstract method onDialogNegativeClick()
     *  This method is defined in the interface NoticeDialogListener, which is
     *  inside the class DestiniFragment.
     */
    @Override
    public void onDialogNegativeClick( DialogFragment dialog ) {

        // When the "No" button is clicked, dismiss the alert dialog and invoke
        // the method endGame() to end the game.
        dialog.dismiss();
        endGame();

    }   // end of method onDialogNegativeClick()

    // Method startGameOver() is invoked to start the game from the beginning
    private void startGameOver() {

        mTextStory.setEnabled( true );             // Enable the text story
        mTopButton.setVisibility( View.VISIBLE );      // Make top button visible again
        mBottomButton.setVisibility( View.VISIBLE );   // Hide the bottom button again

        // Reset all the key variables
        mTextStoryIndex = 0;
        mAnswerStoryIndex = 0;
        mEndStoryIndex= 0;

        mTopButtonClickCount = 0;
        mBottomButtonClickCount = 0;

        mTempTextStoryIndex = 0;

        mFirstTimeButtonIsClicked = true;
        mEndOfStory = false;

        updateStoryAndAnswer();             // Invoke the method updateStoryAndAnswer()

    }   // end of method startGameOver()

    /* Method endGame() closes the application */
    private void endGame() {

        this.finish();      // Close the application

    }   // end of method endGame()

}   // end of class MainActivity
