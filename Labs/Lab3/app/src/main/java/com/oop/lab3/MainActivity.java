package com.oop.lab3;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.oop.lab3.dtw.DTW;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /* Empty Description. */
    private static final Description DESCRIPTION_NULL = new Description() { { this.setText(""); }};

    /* Chart Constants. */
    private static final int    LENGTH_CHART_HISTORY  = 1000;
    private static final double EPS                   = 1;
    private static final int    AVERAGE_WINDOW_LENGTH = 1;
    private static final int    DELAY_SENSOR          = SensorManager.SENSOR_DELAY_FASTEST;

    /* Member Variables. */
    private EMode         mMode;
    private boolean       mResponsive;
    private SensorManager mSensorManager;

    /* Feedback. */
    private RelativeLayout mFeedbackLayout;
    private ImageView      mFeedbackView;
    private RelativeLayout mObscureLayout;
    private TextView       mModeTitle;
    private TextView       mModeDescription;
    private TextView       mXResult;
    private TextView       mYResult;
    private TextView       mZResult;
    private TextView       mResult;
    private Switch         mModeSwitch;
    private Button         mStartButton;

    /* Graphs. */
    private LineChart mLineAcc;
    private LineChart mLineTrain;
    private LineChart mLineRecognition;

    /* Data. */
    private LineData  mAccData;
    private LineData  mTrainData;
    private LineData  mRecognitionData;

    /* Datasets. */
    private LineDataSet[] mAcceleration;
    private LineDataSet[] mTraining;
    private LineDataSet[] mRecognition;

    /* Chart Managers. */
    private LineChartManager mAccChartManager;
    private LineChartManager mTrainChartManager;
    private LineChartManager mRecognitionChartManager;

    /* History Lists. */
    private List<List<Float>> mTrainingHistory;
    private List<List<Float>> mRecognitionHistory;


    /** Mode Definition. */
    private enum EMode {
        /** Defines when the app is recording motion data. */
        TRAINING,
        /** Defines when the app is attempting to recognize motion data. */
        RECOGNITION;
    }

    /** Abstracts graph updates. */
    private static class LineChartManager {

        /* Member Variables. */
        private final LineChart     mLineChart;
        private final LineDataSet[] mDataSets;
        private final int           mWindow;
        private final float[]       mBuffer;
        private       int           mOffset;

        /** Constructor. */
        public LineChartManager(final LineChart pLineChart,
                                final int pWindow,
                                final LineDataSet ... pDataSets) {
            // Initialize Member Variables.
            this.mLineChart = pLineChart;
            this.mDataSets  = pDataSets;
            this.mWindow    = pWindow;
            this.mBuffer    = new float[pDataSets.length];
            this.mOffset    = 0;
        }

        /** Updates the Chart. */
        public final void onUpdateChart(final float ... pVertical) {
            // Increment the Offset.
            this.setOffset(this.getOffset() + 1);
            // Buffer the Averages.
            for(int i = 0; i < pVertical.length; i++) {
                // Accumulate.
                this.getBuffer()[i] += pVertical[i];
            }
            // Have we reached the window length?
            if(this.getOffset() % this.getWindow() == 0) {
                // Perform an aggregated update.
                this.onAggregateUpdate(this.getBuffer());
                // Clear the Buffer.
                Arrays.fill(this.getBuffer(), 0.0f);
            }
        }

        /** Called when the number of samples displayed on the graph have satisfied the window size. */
        public void onAggregateUpdate(final float[] pAggregate) {
            // Update the chart.
            for(int i = 0; i < this.getDataSets().length; i++) {
                // Calculate the Average.
                final float       lAverage      = this.getBuffer()[i] / this.getWindow();
                // Fetch the DataSet.
                final LineDataSet lLineDataSet  = this.getDataSets()[i];
                // Write this Value to the Aggregate for subclasses.
                pAggregate[i] = lAverage;
                // Remove the oldest element.
                lLineDataSet.removeFirst();
                // Buffer the Average.
                lLineDataSet.addEntry(new Entry(this.getOffset(), lAverage));
            }
            // Invalidate the Graph. (Ensure it is redrawn!)
            this.getLineChart().invalidate();
        }

        /* Getters. */
        private LineChart getLineChart() {
            return this.mLineChart;
        }

        private LineDataSet[] getDataSets() {
            return this.mDataSets;
        }

        private int getWindow() {
            return this.mWindow;
        }

        private float[] getBuffer() {
            return this.mBuffer;
        }

        public void setOffset(final int pOffset) {
            this.mOffset = pOffset;
        }

        private int getOffset() {
            return this.mOffset;
        }

    }


    /** Converts a List of Floats into a primitive equivalent. */
    private static float[] primitive(final List<Float> pList) {
        // Declare the Array.
        final float[] lT = new float[pList.size()];
        // Iterate the List.
        for(int i = 0; i < pList.size(); i++) {
            // Buffer the Element.
            lT[i] = pList.get(i);
        }
        // Return the Array.
        return lT;
    }

    /** Colors an Array of DataSets. */
    private static void color(final LineDataSet[] pLineDataSets, final int[] pColor) {
        // Iterate.
        for(int i = 0; i < pLineDataSets.length; i++) {
            // Update the color of the LineDataSet.
            MainActivity.color(pLineDataSets[i], pColor[i]);
        }
    }

    /** Colors a LineDataSet. */
    private static void color(final LineDataSet pLineDataSet, final int pColor) {
        // Update the Colors.
        pLineDataSet.setColor(pColor);
        pLineDataSet.setCircleHoleColor(pColor);
        pLineDataSet.setCircleColor(pColor);
    }


    /** Handle Creation of the Activity. */
    @SuppressLint("ClickableViewAccessibility")
    @Override protected final void onCreate(final Bundle pSavedInstanceState) {
        // Implement the Parent Definition
        super.onCreate(pSavedInstanceState);
        // Set the Content View
        this.setContentView(R.layout.activity_main);
        // Initialize Graphs
        this.mLineAcc         = $(R.id.lc_acc);
        this.mLineTrain       = $(R.id.lc_train);
        this.mLineRecognition = $(R.id.lc_recognize);
        // Initialize Feedback
        this.mFeedbackLayout  = $(R.id.rl_feedback);
        this.mFeedbackView    = $(R.id.iv_feedback);
        this.mObscureLayout   = $(R.id.rl_obscure);
        this.mStartButton     = $(R.id.start_button);
        // Initialize Result
        this.mXResult         = $(R.id.xDTW);
        this.mYResult         = $(R.id.yDTW);
        this.mZResult         = $(R.id.zDTW);
        this.mResult          = $(R.id.result);
        // Initialize UI
        this.mModeTitle       = $(R.id.tv_mode);
        this.mModeDescription = $(R.id.tv_mode_desc);
        this.mModeSwitch      = $(R.id.sw_mode);
        // Prepare data
        this.mAccData         = new LineData();
        this.mTrainData       = new LineData();
        this.mRecognitionData = new LineData();
        // Allocate the histories
        this.mTrainingHistory    = new ArrayList<>(Arrays.asList( new ArrayList<Float>(), new ArrayList<Float>(), new ArrayList<Float>() ));
        this.mRecognitionHistory = new ArrayList<>(Arrays.asList( new ArrayList<Float>(), new ArrayList<Float>(), new ArrayList<Float>() ));

        // Allocate the Acceleration
        this.mAcceleration = new LineDataSet[] { new LineDataSet(null, "X"), new LineDataSet(null, "Y"), new LineDataSet(null, "Z") };
        this.mTraining     = new LineDataSet[] { new LineDataSet(null, "X"), new LineDataSet(null, "Y"), new LineDataSet(null, "Z") };
        this.mRecognition  = new LineDataSet[] { new LineDataSet(null, "X"), new LineDataSet(null, "Y"), new LineDataSet(null, "Z") };

        // Register the Line Data Sources.
        this.getLineAcc().setData(this.getAccData());
        this.getLineTrain().setData(this.getTrainData());
        this.getLineRecognition().setData(this.getRecognitionData());

        // Enable AutoScaling.
        this.getLineAcc().setAutoScaleMinMaxEnabled(true);
        this.getLineTrain().setAutoScaleMinMaxEnabled(true);
        this.getLineRecognition().setAutoScaleMinMaxEnabled(true);
        this.getLineTrain().setScaleXEnabled(true);
        this.getLineRecognition().setScaleXEnabled(true);

        // Hide the right axis for training and recognition.
        this.getLineAcc().getXAxis().setDrawLabels(false);
        this.getLineAcc().getAxisRight().setDrawLabels(false);
        this.getLineTrain().getAxisRight().setDrawLabels(false);
        this.getLineRecognition().getAxisRight().setDrawLabels(false);

        this.getLineAcc().setDescription(MainActivity.DESCRIPTION_NULL);
        this.getLineTrain().setDescription(MainActivity.DESCRIPTION_NULL);
        this.getLineRecognition().setDescription(MainActivity.DESCRIPTION_NULL);


        // Initialize chart data.
        MainActivity.this.onInitializeData(this.getAcceleration());
        MainActivity.this.onInitializeData(this.getTraining());
        MainActivity.this.onInitializeData(this.getRecognition());

            // Register the LineDataSets.
        for(LineDataSet lLineDataSet : this.getAcceleration()) {         this.getAccData().addDataSet(lLineDataSet); }
        for(LineDataSet lLineDataSet : this.getTraining())     {       this.getTrainData().addDataSet(lLineDataSet); }
        for(LineDataSet lLineDataSet : this.getRecognition())  { this.getRecognitionData().addDataSet(lLineDataSet); }

        // Assert that the DataSet has changed, and that we'll be using threse three sources.
        this.getAccData().notifyDataChanged();
        this.getTrainData().notifyDataChanged();
        this.getRecognitionData().notifyDataChanged();

        // Color the DataSets.
        MainActivity.color(this.getAcceleration(), new int[]{ Color.RED, Color.GREEN, Color.BLUE });
        MainActivity.color(this.getTraining(),     new int[]{ Color.RED, Color.GREEN, Color.BLUE });
        MainActivity.color(this.getRecognition(),  new int[]{ Color.RED, Color.GREEN, Color.BLUE });

        // Declare the LineChartManager.
        this.mAccChartManager         = new LineChartManager(this.getLineAcc(),         MainActivity.AVERAGE_WINDOW_LENGTH, this.getAcceleration());
        // Declare the Training and Recognition update handling.
        this.mTrainChartManager       = new LineChartManager(this.getLineTrain(),       MainActivity.AVERAGE_WINDOW_LENGTH, this.getTraining()) { @Override public final void onAggregateUpdate(final float[] pAggregate) {
            // Update the graph. (This actually manipulates the buffer to compensate for averaging.)
            super.onAggregateUpdate(pAggregate);
            // Iterate the Averages.
            for(int i = 0; i < pAggregate.length; i++) {
                // Buffer the Value.
                MainActivity.this.getTrainingHistory().get(i).add(pAggregate[i]);
            }
        } };
        // Declare Recognition Handling.
        this.mRecognitionChartManager = new LineChartManager(this.getLineRecognition(), MainActivity.AVERAGE_WINDOW_LENGTH, this.getRecognition()) { @Override public final void onAggregateUpdate(final float[] pAggregate) {
            // Update the graph. (Compute the averages and store the result in the aggregate.)
            super.onAggregateUpdate(pAggregate);
            // Iterate the Averages.
            for(int i = 0; i < pAggregate.length; i++) {
                // Buffer the Value.
                MainActivity.this.getRecognitionHistory().get(i).add(pAggregate[i]);
            }
        } };

        // Define the startup mode.
        this.mMode          = EMode.TRAINING;
        // Define whether we're going to start processing motion data
        this.mResponsive    = false;
        // Fetch the SensorManager.
        this.mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        // Listen for clicks on the Mode switch.
        this.getModeSwitch().setOnCheckedChangeListener((pCompoundButton, pIsChecked) -> {
            // Update the training state.
            MainActivity.this.setMode(pIsChecked ? EMode.RECOGNITION : EMode.TRAINING);
            // Update the title and description.
            MainActivity.this.getModeTitle().setText(pIsChecked ? R.string.mode_recognition      : R.string.mode_training);
            MainActivity.this.getModeDescription().setText(pIsChecked ? R.string.mode_recognition_desc : R.string.mode_training_desc);
        });

        // Handle the ObscureLayout.
        this.getObscureLayout().setOnTouchListener((pView, pMotionEvent) -> {
            // Whilst the ObscureLayout is visible, obscure all touch data.
            return MainActivity.this.getObscureLayout().getVisibility() == View.VISIBLE;
        });

        // Listen for Touch Events on the FeedbackLayout.
        this.getStartButton().setOnTouchListener((pView, pMotionEvent) -> {
            // Handle the MotionEvent.
            switch(pMotionEvent.getActionMasked()) {
                /* When the user touches down on the graphs... */
                case MotionEvent.ACTION_DOWN : {
                    // Disable the Switch.
                    MainActivity.this.getModeSwitch().setEnabled(false);
                    // Handle the Mode.
                    switch(MainActivity.this.getMode()) {
                        case TRAINING     : {
                            // Reset the Training History.
                            for(final List<Float> lTraining : MainActivity.this.getTrainingHistory()) {
                                // Clear the Training List.
                                lTraining.clear();
                            }
                            // Reset the Training Chart.
                            MainActivity.this.getTrainChartManager().setOffset(0);
                            // Re-initialize the Training Data.
                            MainActivity.this.onInitializeData(MainActivity.this.getTraining());
                            // Assert that we're recording.
                            MainActivity.this.onFeedbackRecording();
                        } break;
                        case RECOGNITION  : {
                            // Reset the Recognition History.
                            for(final List<Float> lRecognition : MainActivity.this.getRecognitionHistory()) {
                                // Clear the Recognition List.
                                lRecognition.clear();
                            }
                            // Reset the Recognition Chart.
                            MainActivity.this.getRecognitionChartManager().setOffset(0);
                            // Re-initialize the Recognition Data.
                            MainActivity.this.onInitializeData(MainActivity.this.getRecognition());
                            // Assert that we're listening.
                            MainActivity.this.onFeedbackRecognition();
                        } break;
                    }
                    // Assert that we're now responsive.
                    MainActivity.this.setResponsive(true);
                } break;
                /* Once the user has stopped touching the graphs... */
                case MotionEvent.ACTION_UP   : {
                    // We're no longer responsive.
                    MainActivity.this.setResponsive(false);
                    // Hide the FeedbackLayout.
                    MainActivity.this.onHideFeedback();
                    // Handle the Mode.
                    switch(MainActivity.this.getMode()) {
                        case TRAINING     : {

                        } break;
                        case RECOGNITION  : {
                            // Ensure the ObscureLayout is visible.
                            MainActivity.this.getObscureLayout().setVisibility(View.VISIBLE);
                            // Launch an AsyncTask.
                            @SuppressLint("SetTextI18n") Runnable lAsyncTask = () -> {
                                // Declare the Averages.
                                final double[] lAverages = new double[3];
                                // Declare the Dynamic Time Warping Algorithm.
                                final DTW      lDTW      = new DTW();
                                // Iterate the Histories.
                                for(int i = 0; i < 3; i++) {
                                    // Fetch the Primitive Histories for this Axis.
                                    final float[] lTraining    = MainActivity.primitive(MainActivity.this.getTrainingHistory().get(i));
                                    final float[] lRecognition = MainActivity.primitive(MainActivity.this.getRecognitionHistory().get(i));
                                    // Calculate the distance using Dynamic Time Warping.
                                    lAverages[i] = lDTW.compute(lRecognition, lTraining).getDistance();
                                }
                                // Linearize execution on the UI Thread.
                                MainActivity.this.runOnUiThread(() -> {
                                    // Allow the layout to be interacted with again.
                                    MainActivity.this.getObscureLayout().setVisibility(View.GONE);
                                    // Print the Result.
                                    boolean isNice = true;
                                    for (int i=0;i<3;i++){
                                        if (Double.isNaN(lAverages[i]) || lAverages[i] > EPS) {
                                            isNice = false;
                                            break;
                                        }
                                    }
                                    if (isNice) { getResult().setText("Recognized");}
                                    else {getResult().setText("Not recognized");}
                                    getXResult().setText(String.valueOf(lAverages[0]));
                                    getYResult().setText(String.valueOf(lAverages[1]));
                                    getZResult().setText(String.valueOf(lAverages[2]));
                                });
                                // Satisfy the compiler.
                            };
                            // Execute the AsyncTask.
                            ExecutorService executor = Executors.newFixedThreadPool(4);
                            executor.execute(lAsyncTask);
                        } break;
                    }
                    // Re-enable the Switch.
                    MainActivity.this.getModeSwitch().setEnabled(true);
                } break;
            }
            // Consume all touch data.
            return true;
        });
        // Hide the Feedback Layout.
        this.onHideFeedback();
    }

    /* Resets a Chart. */
    private void onInitializeData(final LineDataSet[] pDataSet) {
        // Ensure the DataSets are empty.
        for(final LineDataSet lLineDataSet : pDataSet) {
            // Clear the DataSet.
            lLineDataSet.clear();
        }
        // Initialize the Acceleration Charts.
        for(int i = 0; i < MainActivity.LENGTH_CHART_HISTORY; i++) {
            // Allocate a the default Entry.
            final Entry lEntry = new Entry(i, 0);
            // Iterate the DataSets.
            for(final LineDataSet lLineDataSet : pDataSet) {
                // Buffer the Entry.
                lLineDataSet.addEntry(lEntry);
            }
        }
    }

    /* Hides the Feedback View. */
    private void onHideFeedback() {
        this.getFeedbackView().setVisibility(View.GONE);
        this.getFeedbackLayout().setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent));
    }

    /* Asserts that we're recording. */
    private void onFeedbackRecording() {
        this.getFeedbackView().setVisibility(View.VISIBLE);
        this.getFeedbackView().setImageResource(R.drawable.ic_train_circle);
        this.getFeedbackLayout().setBackgroundColor(ContextCompat.getColor(this, R.color.colorMature));
    }

    /* Asserts that we're recognizing. */
    private void onFeedbackRecognition() {
        this.getFeedbackView().setVisibility(View.VISIBLE);
        this.getFeedbackView().setImageResource(R.drawable.ic_record_circle);
        this.getFeedbackLayout().setBackgroundColor(ContextCompat.getColor(this, R.color.colorMature));
    }

    /* Simple return for a resource reference. */
    private <T extends View> T $(int pId) {
        // Return the type-casted View.
        return (T)this.findViewById(pId);
    }

    /* Handle a change to sensor data. */
    @Override public final void onSensorChanged(final SensorEvent pSensorEvent) {
        if(pSensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Update the LineChartManager.
            this.getAccChartManager().onUpdateChart(pSensorEvent.values);
            if(MainActivity.this.isResponsive()) {
                switch(MainActivity.this.getMode()) {
                    case TRAINING    : {
                        // Update the Training Chart.
                        this.getTrainChartManager().onUpdateChart(pSensorEvent.values);
                    } break;
                    case RECOGNITION : {
                        // Update the Training Chart.
                        this.getRecognitionChartManager().onUpdateChart(pSensorEvent.values);
                    } break;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    /* When the Activity is resumed. */
    @Override protected final void onResume() {
        super.onResume();
        // Register for updates on the SensorManager
        this.getSensorManager().registerListener(this, this.getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER), MainActivity.DELAY_SENSOR);
    }

    /* When the Activity is paused. */
    @Override protected final void onPause() {
        super.onPause();
        // Stop listening for accelerometer data
        this.getSensorManager().unregisterListener(this);
    }

    /* Getters and Setters */
    private void setMode(final EMode pMode) {
        this.mMode = pMode;
    }

    private EMode getMode() {
        return this.mMode;
    }

    private void setResponsive(final boolean pIsResponsive) {
        this.mResponsive = pIsResponsive;
    }

    private boolean isResponsive() {
        return this.mResponsive;
    }

    private SensorManager getSensorManager() {
        return this.mSensorManager;
    }

    private LineChart getLineAcc() {
        return this.mLineAcc;
    }

    private LineChart getLineTrain() {
        return this.mLineTrain;
    }

    private LineChart getLineRecognition() {
        return this.mLineRecognition;
    }

    private RelativeLayout getFeedbackLayout() {
        return this.mFeedbackLayout;
    }

    private ImageView getFeedbackView() {
        return this.mFeedbackView;
    }

    private RelativeLayout getObscureLayout() {
        return this.mObscureLayout;
    }

    private TextView getModeTitle() {
        return this.mModeTitle;
    }

    private TextView getXResult() {
        return this.mXResult;
    }

    private TextView getYResult() {
        return this.mYResult;
    }

    private TextView getZResult() {
        return this.mZResult;
    }

    private TextView getResult() {
        return this.mResult;
    }

    private TextView getModeDescription() {
        return this.mModeDescription;
    }

    private Button getStartButton() {
        return this.mStartButton;
    }

    private Switch getModeSwitch() {
        return this.mModeSwitch;
    }

    private LineData getAccData() {
        return this.mAccData;
    }

    private LineData getTrainData() {
        return this.mTrainData;
    }

    private LineData getRecognitionData() {
        return this.mRecognitionData;
    }

    private LineDataSet[] getAcceleration() {
        return this.mAcceleration;
    }

    private LineDataSet[] getTraining() {
        return this.mTraining;
    }

    private LineDataSet[] getRecognition() {
        return this.mRecognition;
    }

    private LineChartManager getAccChartManager() {
        return this.mAccChartManager;
    }

    private LineChartManager getTrainChartManager() {
        return this.mTrainChartManager;
    }

    private LineChartManager getRecognitionChartManager() {
        return this.mRecognitionChartManager;
    }

    private List<List<Float>> getTrainingHistory() {
        return this.mTrainingHistory;
    }

    private List<List<Float>> getRecognitionHistory() {
        return this.mRecognitionHistory;
    }

}
