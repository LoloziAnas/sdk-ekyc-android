package com.onblock.sdk_ekyc.mrz;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognizer;
import com.onblock.sdk_ekyc.graphic.FrameMetadata;
import com.onblock.sdk_ekyc.graphic.GraphicOverlay;
import com.onblock.sdk_ekyc.graphic.TextGraphic;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TextRecognitionProcessor {

    private static final String TAG = TextRecognitionProcessor.class.getName();
    private final TextRecognizer textRecognizer;
    private final AtomicBoolean shouldThrottle = new AtomicBoolean(false);

    private ResultListener resultListener;
    private String scannedTextBuffer;

    public TextRecognitionProcessor(TextRecognizer textRecognizer, ResultListener resultListener) {
        this.textRecognizer = textRecognizer;
        this.resultListener = resultListener;
    }


    public void process(ByteBuffer data, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay) throws MlKitException {

        if (shouldThrottle.get()) {
            return;
        }

        InputImage inputImage = InputImage.fromByteBuffer(data,
                frameMetadata.getWidth(),
                frameMetadata.getHeight(),
                frameMetadata.getRotation(),
                InputImage.IMAGE_FORMAT_NV21);

        detectInVisionImage(inputImage, frameMetadata, graphicOverlay);
    }
    protected Task<Text> detectInImage(InputImage image) {
        return textRecognizer.process(image);
    }


    protected void onSuccess(@NonNull Text results, @NonNull FrameMetadata frameMetadata, @NonNull GraphicOverlay graphicOverlay) {

        graphicOverlay.clear();

        scannedTextBuffer = "";

        List<Text.TextBlock> blocks = results.getTextBlocks();

        for (int i = 0; i < blocks.size(); i++) {
            List<Text.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<Text.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    filterScannedText(graphicOverlay, elements.get(k));
                }
            }
        }
    }

    private void filterScannedText(GraphicOverlay graphicOverlay, Text.Element element) {
        GraphicOverlay.Graphic textGraphic = new TextGraphic(graphicOverlay, element, Color.GREEN);
        scannedTextBuffer += element.getText();

    }

    protected void onFailure(@NonNull Exception e) {
        Log.w(TAG, "Text detection failed." + e);
        resultListener.onError(e);
    }
    private void detectInVisionImage(InputImage image, final FrameMetadata metadata, final GraphicOverlay graphicOverlay) {

        detectInImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text results) {
                                shouldThrottle.set(false);
                                TextRecognitionProcessor.this.onSuccess(results, metadata, graphicOverlay);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                shouldThrottle.set(false);
                                TextRecognitionProcessor.this.onFailure(e);
                            }
                        });
        // Begin throttling until this frame of input has been processed, either in onSuccess or
        // onFailure.
        shouldThrottle.set(true);
    }
    public void stop() {
        textRecognizer.close();
    }

    public interface  ResultListener{
        void onSuccess();
        void onError(Exception exception);
    }
}
